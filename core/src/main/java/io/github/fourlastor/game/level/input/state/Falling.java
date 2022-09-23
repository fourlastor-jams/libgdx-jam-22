package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.fourlastor.game.Message;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.PlayerAnimationsFactory;
import javax.inject.Inject;
import javax.inject.Named;

public class Falling extends InputState {

    private final Animation<TextureRegion> animation;

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
    protected Animation<TextureRegion> animation() {
        return animation;
    }

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        if (telegram.message == Message.PLAYER_ON_GROUND.ordinal()) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.onGround);
            return true;
        }
        return false;
    }
}
