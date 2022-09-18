package io.github.fourlastor.game.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;

public class InputStateMachine extends DefaultStateMachine<Entity, InputState> {

    public InputStateMachine(Entity entity, InputState initialState) {
        super(entity, initialState);
    }
}
