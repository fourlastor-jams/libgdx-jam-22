package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import io.github.fourlastor.game.input.InputStateMachine;
import io.github.fourlastor.game.input.state.Falling;
import io.github.fourlastor.game.input.state.OnGround;

public class PlayerComponent implements Component {

    public InputStateMachine stateMachine;

    public OnGround onGround;
    public Falling falling;
}
