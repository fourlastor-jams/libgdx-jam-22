package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.ChunkRemovalComponent;
import io.github.fourlastor.game.component.MovingComponent;
import io.github.fourlastor.game.component.PlayerRequestComponent;
import io.github.fourlastor.game.component.SoundComponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.blueprint.definitions.MovingPlatform;
import io.github.fourlastor.game.level.blueprint.definitions.Platform;
import io.github.fourlastor.game.level.blueprint.definitions.SawBlade;
import io.github.fourlastor.game.ui.AnimatedImage;
import io.github.fourlastor.game.ui.ParallaxImage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/** Factory to create various entities: player, buildings, enemies.. */
@ScreenScoped
public class EntitiesFactory {

    private static final float CHARACTER_SCALE_XY = 1f / 40f;
    private static final float SCALE_XY = 1f / 32f;
    private final Animation<TextureRegion> fallingAnimation;
    private final Animation<TextureRegion> fishAnimation;
    private final TextureAtlas textureAtlas;
    private final Sound sawBladeSound;
    private final Sound fishSound;

    @Inject
    public EntitiesFactory(
            @Named(PlayerAnimationsFactory.ANIMATION_FALLING) Animation<TextureRegion> fallingAnimation,
            TextureAtlas textureAtlas,
            AssetManager assetManager) {
        this.fallingAnimation = fallingAnimation;
        this.textureAtlas = textureAtlas;
        sawBladeSound = assetManager.get("audio/sounds/sawblade.ogg", Sound.class);
        fishSound = assetManager.get("audio/sounds/fish.mp3", Sound.class);
        fishAnimation = new Animation<>(
                0.1f, textureAtlas.findRegions("enemies/wigglingFish/wigglingFish"), Animation.PlayMode.LOOP);
    }

    public Entity player() {
        Entity entity = new Entity();
        AnimatedImage image = new AnimatedImage(fallingAnimation);
        image.setScale(CHARACTER_SCALE_XY);

        entity.add(new AnimatedImageComponent(image));
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(new Vector2(4.5f, 1.5f));
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.25f, 0.25f);
            Fixture fixture = body.createFixture(shape, 0.0f);
            fixture.setFriction(100f);
            fixture.setRestitution(0.15f);
            fixture.setUserData(UserData.PLAYER);
            shape.dispose();
            return body;
        }));
        image.setPosition(-0.5f, -0.5f);
        Group group = new Group();
        group.addActor(image);
        entity.add(new ActorComponent(group, ActorComponent.Layer.CHARACTER));
        entity.add(new PlayerRequestComponent());
        return entity;
    }

    private void movingPlatform(Entity entity, MovingPlatform platform, float dY, Vector2 initialPosition) {
        List<Vector2> path = new ArrayList<>(platform.path.size() + 1);
        path.add(initialPosition);
        for (Vector2 point : platform.path) {
            path.add(point.cpy().add(0, dY));
        }
        entity.add(new MovingComponent(path, platform.speed.speed()));
    }

    public Entity platform(Platform platform, float dY, float top) {
        Entity entity = new Entity();
        Vector2 initialPosition = platform.position.cpy().add(0f, dY);
        entity.add(platformBuilder(initialPosition, platform.width));
        entity.add(platformActor(platform.type, platform.width));
        if (platform instanceof MovingPlatform) {
            movingPlatform(entity, (MovingPlatform) platform, dY, initialPosition);
        }
        return entity;
    }

    private ActorComponent platformActor(Platform.Type type, Platform.Width width) {
        Image image = new Image(textureAtlas.findRegion("platforms/platform_" + type.tileName + "_w" + width.width));
        image.setScale(SCALE_XY);
        return new ActorComponent(image, ActorComponent.Layer.PLATFORM);
    }

    private BodyBuilderComponent platformBuilder(Vector2 position, Platform.Width width) {
        return new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(position);
            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width.width / 2f, 0.25f);
            body.createFixture(shape, 0.0f).setUserData(UserData.PLATFORM);
            shape.dispose();
            return body;
        });
    }

    public Entity parallaxBackground(float factor, ActorComponent.Layer layer, int backgroundIndex) {
        Entity entity = new Entity();
        TextureRegion region = textureAtlas.findRegion("background/background_layer", backgroundIndex);
        ParallaxImage image = new ParallaxImage(factor, region);
        image.setScale(SCALE_XY);
        entity.add(new ActorComponent(image, layer));
        return entity;
    }

    public Entity chunkRemoval(float newTop) {
        Entity entity = new Entity();
        entity.add(new ChunkRemovalComponent(newTop));
        return entity;
    }

    public Entity sawBlade(SawBlade sawBlade, float dY, float top) {
        Entity entity = new Entity();
        Vector2 initialPosition = sawBlade.position.cpy().add(0f, dY);
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(initialPosition);
            Body body = world.createBody(bodyDef);
            CircleShape shape = new CircleShape();
            shape.setRadius(0.5f);
            body.createFixture(shape, 0.0f).setUserData(UserData.SAWBLADE);
            shape.dispose();
            return body;
        }));
        Image image = new Image(textureAtlas.findRegion("enemies/sawblade"));
        image.setScale(SCALE_XY);
        image.setOrigin(0.5f, 0.5f);
        image.setPosition(-1f, -1f);
        RotateByAction rotate = new RotateByAction();
        rotate.setAmount(360);
        rotate.setDuration(1f);
        Group group = new Group();
        group.addActor(image);
        group.addAction(Actions.forever(rotate));
        entity.add(new ActorComponent(group, ActorComponent.Layer.ENEMIES));
        entity.add(new SoundComponent(sawBladeSound));
        List<Vector2> path = new ArrayList<>(sawBlade.path.size() + 1);
        path.add(initialPosition);
        for (Vector2 point : sawBlade.path) {
            path.add(point.cpy().add(0, dY));
        }
        entity.add(new MovingComponent(path, sawBlade.speed.speed));
        return entity;
    }

    public Entity fish(Vector2 initialPosition) {
        Entity entity = new Entity();
        entity.add(new SoundComponent(fishSound));
        AnimatedImage image = new AnimatedImage(fishAnimation);
        image.setScale(CHARACTER_SCALE_XY);
        entity.add(new AnimatedImageComponent(image));
        entity.add(new ActorComponent(image, ActorComponent.Layer.ENEMIES));
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(initialPosition);
            Body body = world.createBody(bodyDef);
            CircleShape shape = new CircleShape();
            shape.setRadius(0.1f);
            Fixture fixture = body.createFixture(shape, 0.0f);
            fixture.setFriction(0f);
            fixture.setRestitution(0.4f);
            shape.dispose();
            return body;
        }));
        return entity;
    }
}
