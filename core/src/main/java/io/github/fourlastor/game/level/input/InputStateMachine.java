package io.github.fourlastor.game.level.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import io.github.fourlastor.game.level.input.state.InputState;

public class InputStateMachine extends DefaultStateMachine<Entity, InputState> {

    @AssistedInject
    public InputStateMachine(@Assisted Entity entity, @Assisted InputState initialState) {
        super(entity, initialState);
    }

    public boolean keyDown(Entity entity, int keycode) {
        boolean result = false;
        if (currentState != null) {
            result = currentState.keyDown(entity, keycode);
        }
        if (globalState != null && !result) {
            result = globalState.keyDown(entity, keycode);
        }
        return result;
    }

    public boolean keyUp(Entity entity, int keycode) {
        boolean result = false;
        if (currentState != null) {
            result = currentState.keyUp(entity, keycode);
        }
        if (globalState != null && !result) {
            result = globalState.keyUp(entity, keycode);
        }
        return result;
    }

    public boolean touchDown(Entity entity, int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        if (currentState != null) {
            result = currentState.touchDown(entity, screenX, screenY, pointer, button);
        }
        if (globalState != null && !result) {
            result = globalState.touchDown(entity, screenX, screenY, pointer, button);
        }
        return result;
    }

    public boolean touchUp(Entity entity, int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        if (currentState != null) {
            result = currentState.touchUp(entity, screenX, screenY, pointer, button);
        }
        if (globalState != null && !result) {
            result = globalState.touchUp(entity, screenX, screenY, pointer, button);
        }
        return result;
    }

    @AssistedFactory
    public interface Factory {
        InputStateMachine create(Entity entity, InputState initialState);
    }
}
