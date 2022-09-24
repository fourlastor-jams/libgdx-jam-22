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
import io.github.fourlastor.game.component.ChunkComponent;
import io.github.fourlastor.game.component.ChunkRemovalComponent;
import io.github.fourlastor.game.component.MovingComponent;
import io.github.fourlastor.game.component.PlayerRequestComponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.platform.definitions.MovingPlatform;
import io.github.fourlastor.game.level.platform.definitions.Platform;
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

    private void movingPlatform(Entity entity, MovingPlatform platform, float dY) {
        List<Vector2> path = new ArrayList<>(platform.path.size());
        for (Vector2 point : platform.path) {
            path.add(point.cpy().add(0, dY));
        }
        entity.add(new MovingComponent(path, platform.speed.speed));
    }

    public Entity platform(Platform platform, float dY, float top) {
        Entity entity = new Entity();
        entity.add(platformBuilder(platform.position.cpy().add(0f, dY), platform.width));
        entity.add(platformActor(platform.type, platform.width));
        entity.add(new ChunkComponent(top));
        if (platform instanceof MovingPlatform) {
            movingPlatform(entity, (MovingPlatform) platform, dY);
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
}
