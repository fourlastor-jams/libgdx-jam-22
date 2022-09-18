package io.github.fourlastor.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.utils.ComponentMappers;

public class PhysicsSystem extends IntervalSystem implements EntityListener {

    private static final Family FAMILY = Family.all(BodyComponent.class).get();
    private static final float STEP = 1f / 16f;

    private final World world;
    private final ComponentMapper<BodyComponent> bodies;

    public PhysicsSystem(World world, ComponentMappers componentMappers) {
        super(STEP);
        this.world = world;
        bodies = componentMappers.get(BodyComponent.class);
    }

    @Override
    protected void updateInterval() {
        world.step(STEP, 6, 2);
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(FAMILY, this);
        // TODO move this to entity
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(5.5f, 0f));
        Body body = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(6, 1);
        body.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        BodyComponent component = bodies.get(entity);
        component.body = world.createBody(component.def);
    }

    @Override
    public void entityRemoved(Entity entity) {
        BodyComponent component = bodies.get(entity);
        if (component.body != null) {
            world.destroyBody(component.body);
            component.body = null;
        }
    }
}
