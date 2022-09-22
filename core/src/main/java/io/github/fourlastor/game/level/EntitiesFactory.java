package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.PlayerRequestComponent;
import io.github.fourlastor.game.ui.AnimatedImage;
import io.github.fourlastor.game.ui.ParallaxImage;
import javax.inject.Inject;
import javax.inject.Named;

/** Factory to create various entities: player, buildings, enemies.. */
public class EntitiesFactory {

    private static final float CHARACTER_SCALE_XY = 1f / 40f;
    private static final float SCALE_XY = 1f / 32f;
    private final Animation<TextureRegion> fallingAnimation;
    private final TextureAtlas textureAtlas;

    @Inject
    public EntitiesFactory(
            @Named(PlayerAnimationsFactory.ANIMATION_FALLING) Animation<TextureRegion> fallingAnimation,
            TextureAtlas textureAtlas) {
        this.fallingAnimation = fallingAnimation;
        this.textureAtlas = textureAtlas;
    }

    public Entity player() {
        Entity entity = new Entity();
        AnimatedImage image = new AnimatedImage(fallingAnimation);
        image.setScale(CHARACTER_SCALE_XY);

        entity.add(new AnimatedImageComponent(image));
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(new Vector2(4.5f, 1f));
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.25f, 0.5f);
            body.createFixture(shape, 0.0f).setUserData(UserData.PLAYER);
            shape.dispose();
            return body;
        }));
        image.setY(-0.75f);
        Group group = new Group();
        group.addActor(image);
        entity.add(new ActorComponent(group, ActorComponent.Layer.CHARACTER));
        entity.add(new PlayerRequestComponent());
        return entity;
    }

    public Entity ground(float x, float y, PlatformType platformType, PlatformWidth platformWidth) {
        Entity entity = new Entity();
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(new Vector2(x, y));
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(platformWidth.width / 2f, 0.25f);
            body.createFixture(shape, 0.0f).setUserData(UserData.PLATFORM);
            shape.dispose();
            return body;
        }));
        Image image = new Image(
                textureAtlas.findRegion("platforms/platform_" + platformType.tileName + "_w" + platformWidth.width));
        image.setScale(SCALE_XY);
        entity.add(new ActorComponent(image, ActorComponent.Layer.PLATFORM));

        return entity;
    }

    public Entity parallaxBackground(Texture texture, float factor, ActorComponent.Layer layer) {
        Entity entity = new Entity();
        ParallaxImage image = new ParallaxImage(factor, texture);
        image.setScale(SCALE_XY);
        entity.add(new ActorComponent(image, layer));
        return entity;
    }
}
