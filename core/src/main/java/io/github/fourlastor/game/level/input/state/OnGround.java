package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class OnGround extends InputState {

    @Inject
    public OnGround(ComponentMappers componentMappers) {
        super(componentMappers);
    }

    @Override
    public void update(Entity entity) {
        if (bodies.get(entity).body.getLinearVelocity().y < 0) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.falling);
        }
    }

    @Override
    public boolean keyDown(Entity entity, int keycode) {
        if (keycode == Input.Keys.SPACE) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.jumping);
            return true;
        }
        return super.keyDown(entity, keycode);
    }
}
