package no.sandramoen.commanderqueen.actors.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

import no.sandramoen.commanderqueen.utils.BaseGame;
import no.sandramoen.commanderqueen.utils.Stage3D;

public class BaseActor3D {
    public GameObject modelData;

    protected final Vector3 position;
    protected final Quaternion rotation;
    protected final Vector3 scale;
    protected Polygon boundingPolygon;
    protected Stage3D stage;

    public BaseActor3D(float x, float y, float z, Stage3D s) {
        modelData = null;
        position = new Vector3(x, y, z);
        rotation = new Quaternion();
        scale = new Vector3(1, 1, 1);
        boundingPolygon = null;
        stage = s;
        s.addActor(this);
    }

    public void setModelInstance(GameObject m) {
        modelData = m;
    }

    public Matrix4 calculateTransform() {
        return new Matrix4(position, rotation, scale);
    }

    public void act(float dt) {
        modelData.transform.set(calculateTransform());
    }

    public void draw(ModelBatch batch, Environment env) {
        batch.render(modelData, env);
    }

    public void setColor(Color c) {
        for (Material m : modelData.materials)
            m.set(ColorAttribute.createDiffuse(c));
    }

    public void loadImage(String name) {
        TextureRegion region = BaseGame.textureAtlas.findRegion(name);
        if (region == null)
            Gdx.app.error(getClass().getSimpleName(), "Error: region is null. Are you sure the image '" + name + "' exists?");
        for (Material material : modelData.materials)
            material.set(TextureAttribute.createDiffuse(region));
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 v) {
        position.set(v);
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    public void moveBy(Vector3 v) {
        position.add(v);
    }

    public void moveBy(float x, float y, float z) {
        moveBy(new Vector3(x, y, z));
    }

    public void moveForward(float dist) {
        moveBy(rotation.transform(new Vector3(0, 0, 1)).scl(dist));
    }

    public void moveUp(float dist) {
        moveBy(rotation.transform(new Vector3(1, 0, 0)).scl(dist));
    }

    public void moveRight(float dist) {
        moveBy(rotation.transform(new Vector3(0, 1, 0)).scl(dist));
    }

    public float getTurnAngle() {
        return rotation.getAngleAround(0, -1, 0);
    }

    public void setTurnAngle(float degrees) {
        rotation.set(new Quaternion(Vector3.X, degrees));
    }

    public void turnBy(float degrees) {
        rotation.mul(new Quaternion(Vector3.X, -degrees));
    }

    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
    }

    // 2D collision detection
    public void setBaseRectangle() {
        BoundingBox modelBounds = modelData.calculateBoundingBox(new BoundingBox());
        Vector3 max = modelBounds.max;
        Vector3 min = modelBounds.min;

        float[] vertices =
                {max.z, max.y, min.z, max.y, min.z, min.y, max.z, min.y};

        boundingPolygon = new Polygon(vertices);
        boundingPolygon.setOrigin(0, 0);
    }

    public Polygon getBoundaryPolygon() {
        boundingPolygon.setPosition(position.y, position.z);
        boundingPolygon.setRotation(getTurnAngle());
        boundingPolygon.setScale(scale.y, scale.z);
        return boundingPolygon;
    }

    public boolean overlaps(BaseActor3D other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return false;

        MinimumTranslationVector mtv = new MinimumTranslationVector();

        return Intersector.overlapConvexPolygons(poly1, poly2, mtv);
    }

    public void preventOverlap(BaseActor3D other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // initial test to improve performance
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return;

        MinimumTranslationVector mtv = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

        if (polygonOverlap)
            this.moveBy(0, mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
    }

    public void remove() {
        stage.removeActor(this);
    }

    public static class GameObject extends ModelInstance {
        public final Vector3 center = new Vector3();
        public final Vector3 dimensions = new Vector3();
        public final float radius;

        private final static BoundingBox bounds = new BoundingBox();

        public GameObject(Model boxModel, Vector3 position) {
            super(boxModel, position);
            calculateBoundingBox(bounds);
            bounds.getCenter(center);
            bounds.getDimensions(dimensions);
            radius = dimensions.len() / 2f;
        }
    }
}