package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.input.Message;
import javax.inject.Inject;

public class Falling extends InputState {

    @Inject
    public Falling(ComponentMapper<PlayerComponent> players, ComponentMapper<BodyComponent> bodies) {
        super(players, bodies);
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
