package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import io.github.fourlastor.game.input.InputState;
import io.github.fourlastor.game.input.InputStateMachine;

public class PlayerComponent implements Component {

    public InputStateMachine stateMachine;

    public InputState.OnGround onGround;
}
