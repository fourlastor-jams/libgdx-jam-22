package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import io.github.fourlastor.game.input.InputStateMachine;
import io.github.fourlastor.game.input.state.Falling;
import io.github.fourlastor.game.input.state.OnGround;

public class PlayerComponent implements Component {
    public final InputStateMachine stateMachine;
    public final OnGround onGround;
    public final Falling falling;

    public PlayerComponent(InputStateMachine stateMachine, OnGround onGround, Falling falling) {
        this.stateMachine = stateMachine;
        this.onGround = onGround;
        this.falling = falling;
    }
}
