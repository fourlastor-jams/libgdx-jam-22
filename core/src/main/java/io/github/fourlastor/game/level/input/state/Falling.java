package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.Message;
import io.github.fourlastor.game.level.PlayerAnimationsFactory;
import javax.inject.Inject;
import javax.inject.Named;

public class Falling extends InputState {

    private static final float GRACE_TIME = 0.3f;
    private final Animation<TextureRegion> animation;
    private float attemptedTime;
    private float fallingTime;

    @Inject
    public Falling(
            ComponentMapper<PlayerComponent> players,
            ComponentMapper<BodyComponent> bodies,
            ComponentMapper<AnimatedImageComponent> images,
            @Named(PlayerAnimationsFactory.ANIMATION_FALLING) Animation<TextureRegion> animation) {
        super(players, bodies, images);
        this.animation = animation;
    }

    @Override
    public void enter(Entity entity) {
        super.enter(entity);
        fallingTime = 0f;
        attemptedTime = -1f;
    }

    @Override
    public void update(Entity entity) {
        super.update(entity);
        fallingTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    protected Animation<TextureRegion> animation() {
        return animation;
    }

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        if (telegram.message == Message.PLAYER_ON_GROUND.ordinal()) {
            PlayerComponent player = players.get(entity);
            if (attemptedTime > 0 && fallingTime - attemptedTime < GRACE_TIME) {
                player.stateMachine.changeState(player.chargeJump);
            } else {
                player.stateMachine.changeState(player.onGround);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(Entity entity, int keycode) {
        if (keycode == Input.Keys.SPACE) {
            attemptedTime = fallingTime;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(Entity entity, int screenX, int screenY, int pointer, int button) {
        attemptedTime = fallingTime;
        return true;
    }
}
