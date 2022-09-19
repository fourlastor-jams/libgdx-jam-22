package io.github.fourlastor.game.input.state;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.input.Message;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class Falling extends InputState {

    @Inject
    public Falling(ComponentMappers componentMappers) {
        super(componentMappers);
    }

    @Override
    public void enter(Entity entity) {
        System.out.println("Falling");
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
