package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.PlayerAnimationsFactory;
import io.github.fourlastor.game.level.di.Gravity;
import javax.inject.Inject;
import javax.inject.Named;

public class Jumping extends InputState {

    private static final float MAX_JUMP_HEIGHT = 7f;
    public static final float MIN_JUMP_HEIGHT = 2.5f;
    private final Animation<TextureRegion> animation;
    private final float gravity;
    private AssetManager assetManager;

    @Inject
    public Jumping(
            ComponentMapper<PlayerComponent> players,
            ComponentMapper<BodyComponent> bodies,
            ComponentMapper<AnimatedImageComponent> images,
            @Named(PlayerAnimationsFactory.ANIMATION_JUMPING) Animation<TextureRegion> animation,
            @Gravity Vector2 gravity,
            AssetManager assetManager) {
        super(players, bodies, images);
        this.animation = animation;
        this.gravity = Math.abs(gravity.y);
        this.assetManager = assetManager;
    }

    @Override
    protected Animation<TextureRegion> animation() {
        return animation;
    }

    @Override
    public void enter(Entity entity) {
        super.enter(entity);
        Body body = bodies.get(entity).body;
        float charge = players.get(entity).charge;
        float targetHeight = Math.max(MIN_JUMP_HEIGHT, MAX_JUMP_HEIGHT * charge);
        float velocity = (float) Math.sqrt(2f * targetHeight * gravity);
        body.setLinearVelocity(body.getLinearVelocity().x / 3f, velocity);

        playRandomSound(assetManager);
    }

    @Override
    public void update(Entity entity) {
        if (bodies.get(entity).body.getLinearVelocity().y <= 0) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.falling);
        }
    }

    private void playRandomSound(AssetManager assetManager) {
        int random = MathUtils.random(0, 4);
        Sound sound = assetManager.get("audio/sounds/jumping/jump_" + random + ".wav", Sound.class);
        sound.play();
    }
}
