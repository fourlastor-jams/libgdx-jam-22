package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.input.Message;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class Jumping extends InputState {

    @Inject
    public Jumping(ComponentMappers componentMappers) {
        super(componentMappers);
    }

    @Override
    public void enter(Entity entity) {
        System.out.println("Jumping");
        Body body = bodies.get(entity).body;
        body.setLinearVelocity(body.getLinearVelocity().x, 10f);
    }

    @Override
    public void update(Entity entity) {
        if (bodies.get(entity).body.getLinearVelocity().y <= 0) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.falling);
        }
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
