package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.ImageComponent;
import io.github.fourlastor.game.component.PlayerRequestComponent;
import io.github.fourlastor.game.ui.AnimatedImage;
import io.github.fourlastor.game.ui.ParallaxImage;
import javax.inject.Inject;
import javax.inject.Named;

/** Factory to create various entities: player, buildings, enemies.. */
public class EntitiesFactory {

    private static final float SCALE_XY = 1f / 40f;
    private final Animation<TextureRegion> fallingAnimation;

    @Inject
    public EntitiesFactory(
            @Named(PlayerAnimationsFactory.ANIMATION_FALLING) Animation<TextureRegion> fallingAnimation) {
        this.fallingAnimation = fallingAnimation;
    }

    public Entity player() {
        Entity entity = new Entity();
        AnimatedImage image = new AnimatedImage(fallingAnimation);
        image.setScale(SCALE_XY);
        entity.add(new AnimatedImageComponent(image));
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(new Vector2(4.5f, 1f));
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.5f, 0.5f);
            body.createFixture(shape, 0.0f).setUserData(UserData.PLAYER);
            shape.dispose();
            return body;
        }));
        entity.add(new PlayerRequestComponent());
        return entity;
    }

    public Entity ground(float x, float y) {
        Entity entity = new Entity();
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(new Vector2(x, y));
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(6, 0.2f);
            body.createFixture(shape, 0.0f).setUserData(UserData.PLATFORM);
            shape.dispose();
            return body;
        }));

        return entity;
    }

    public Entity parallaxBackground(Texture texture, float factor) {
        Entity entity = new Entity();
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        ParallaxImage image = new ParallaxImage(factor, texture);
        image.setScale(9f / 288f);
        entity.add(new ImageComponent(image));
        return entity;
    }
}
