package io.github.fourlastor.game.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class InputStateMachine extends DefaultStateMachine<Entity, InputState> {

    @AssistedInject
    public InputStateMachine(@Assisted Entity entity, @Assisted InputState initialState) {
        super(entity, initialState);
    }

    @AssistedFactory
    public interface Factory {
        InputStateMachine create(Entity entity, InputState initialState);
    }
}
