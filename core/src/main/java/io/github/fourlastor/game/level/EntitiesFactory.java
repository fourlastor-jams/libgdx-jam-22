package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.MovingPlatformComponent;
import io.github.fourlastor.game.component.PlayerRequestComponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.platform.PlatformSpec;
import io.github.fourlastor.game.ui.AnimatedImage;
import io.github.fourlastor.game.ui.ParallaxImage;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Named;

/** Factory to create various entities: player, buildings, enemies.. */
@ScreenScoped
public class EntitiesFactory {

    private static final float CHARACTER_SCALE_XY = 1f / 40f;
    private static final float SCALE_XY = 1f / 32f;
    private final Animation<TextureRegion> fallingAnimation;
    private final TextureAtlas textureAtlas;
    private final Random random;

    @Inject
    public EntitiesFactory(
            @Named(PlayerAnimationsFactory.ANIMATION_FALLING) Animation<TextureRegion> fallingAnimation,
            TextureAtlas textureAtlas,
            Random random) {
        this.fallingAnimation = fallingAnimation;
        this.textureAtlas = textureAtlas;
        this.random = random;
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
            Fixture fixture = body.createFixture(shape, 0.0f);
            fixture.setFriction(100f);
            fixture.setUserData(UserData.PLAYER);
            shape.dispose();
            return body;
        }));
        image.setPosition(-0.5f, -0.75f);
        Group group = new Group();
        group.addActor(image);
        entity.add(new ActorComponent(group, ActorComponent.Layer.CHARACTER));
        entity.add(new PlayerRequestComponent());
        return entity;
    }

    private int platformsCount = 0;

    public Entity ground() {
        int count = platformsCount;
        platformsCount += 1;
        if (count == 0) {
            return makeGround(new PlatformSpec(
                    PlatformSpec.Width.NINE, PlatformSpec.Type.SMALL_GRID, PlatformSpec.Speed.IMMOBILE, 4.5f, 0f));
        }
        PlatformSpec.Width width = platformWidth();
        PlatformSpec.Type type;
        if (width == PlatformSpec.Width.ONE) {
            type = PlatformSpec.Type.SMALL_GRID;
        } else {
            type = platformType();
        }
        PlatformSpec.Speed speed = platformSpeed();
        float x = random.nextFloat() * 4f + 2f;
        float y = 4f * count;
        return makeGround(new PlatformSpec(width, type, speed, x, y));
    }

    private Entity makeGround(PlatformSpec spec) {

        Entity entity = new Entity();
        Vector2 initialPosition = new Vector2(spec.x, spec.y);
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(initialPosition);
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(spec.width.width / 2f, 0.25f);
            body.createFixture(shape, 0.0f).setUserData(UserData.PLATFORM);
            shape.dispose();
            return body;
        }));
        Image image = new Image(
                textureAtlas.findRegion("platforms/platform_" + spec.type.tileName + "_w" + spec.width.width));
        image.setScale(SCALE_XY);
        entity.add(new ActorComponent(image, ActorComponent.Layer.PLATFORM));
        entity.add(new MovingPlatformComponent(initialPosition.cpy(), random.nextBoolean(), spec.speed.speed));

        return entity;
    }

    private PlatformSpec.Type platformType() {
        PlatformSpec.Type[] values = PlatformSpec.Type.values();
        return values[random.nextInt(values.length)];
    }

    private PlatformSpec.Width platformWidth() {
        PlatformSpec.Width[] values = PlatformSpec.Width.values();
        return values[random.nextInt(values.length)];
    }

    private PlatformSpec.Speed platformSpeed() {
        PlatformSpec.Speed[] values = PlatformSpec.Speed.values();
        return values[random.nextInt(values.length)];
    }

    public Entity parallaxBackground(float factor, ActorComponent.Layer layer, int backgroundIndex) {
        Entity entity = new Entity();
        TextureRegion region = textureAtlas.findRegion("background/background_layer", backgroundIndex);
        ParallaxImage image = new ParallaxImage(factor, region);
        image.setScale(SCALE_XY);
        entity.add(new ActorComponent(image, layer));
        return entity;
    }
}
