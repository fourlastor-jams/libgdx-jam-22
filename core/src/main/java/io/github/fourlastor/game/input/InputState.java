package io.github.fourlastor.game.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public abstract class InputState implements State<Entity> {

    public static class OnGround extends InputState {

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
