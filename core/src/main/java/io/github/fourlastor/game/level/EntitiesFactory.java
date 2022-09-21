package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
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
import io.github.fourlastor.game.level.platform.PlatformSpeed;
import io.github.fourlastor.game.level.platform.PlatformType;
import io.github.fourlastor.game.level.platform.PlatformWidth;
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
            return makeGround(PlatformWidth.NINE, PlatformType.SMALL_GRID, PlatformSpeed.IMMOBILE, 4.5f, 0f);
        }
        PlatformWidth platformWidth = platformWidth();
        PlatformType platformType;
        if (platformWidth == PlatformWidth.ONE) {
            platformType = PlatformType.SMALL_GRID;
        } else {
            platformType = platformType();
        }
        PlatformSpeed platformSpeed = platformSpeed();
        float x = random.nextFloat(2f, 6f);
        float y = 4f * count;
        return makeGround(platformWidth, platformType, platformSpeed, x, y);
    }

    private Entity makeGround(
            PlatformWidth platformWidth, PlatformType platformType, PlatformSpeed platformSpeed, float x, float y) {
        Entity entity = new Entity();
        Vector2 initialPosition = new Vector2(x, y);
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(initialPosition);
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
        entity.add(new MovingPlatformComponent(initialPosition.cpy(), random.nextBoolean(), platformSpeed.speed));

        return entity;
    }

    private PlatformType platformType() {
        PlatformType[] values = PlatformType.values();
        return values[random.nextInt(values.length)];
    }

    private PlatformWidth platformWidth() {
        PlatformWidth[] values = PlatformWidth.values();
        return values[random.nextInt(values.length)];
    }

    private PlatformSpeed platformSpeed() {
        PlatformSpeed[] values = PlatformSpeed.values();
        return values[random.nextInt(values.length)];
    }

    public Entity parallaxBackground(Texture texture, float factor, ActorComponent.Layer layer) {
        Entity entity = new Entity();
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        ParallaxImage image = new ParallaxImage(factor, texture);
        image.setScale(SCALE_XY);
        entity.add(new ActorComponent(image, layer));
        return entity;
    }
}
