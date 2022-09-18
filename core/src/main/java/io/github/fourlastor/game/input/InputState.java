package io.github.fourlastor.game.input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public abstract class InputState implements State<Entity> {

    public static class OnGround extends InputState {

        private final ComponentMapper<PlayerComponent> players;

        @Inject
        public OnGround(ComponentMappers componentMappers) {
            players = componentMappers.get(PlayerComponent.class);
        }

        @Override
        public void enter(Entity entity) {}

        @Override
        public void update(Entity entity) {}

        @Override
        public void exit(Entity entity) {}

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    }
}
