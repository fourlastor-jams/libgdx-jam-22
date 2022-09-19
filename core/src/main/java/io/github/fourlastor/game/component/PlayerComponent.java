package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import io.github.fourlastor.game.input.InputStateMachine;
import io.github.fourlastor.game.input.state.Falling;
import io.github.fourlastor.game.input.state.Jumping;
import io.github.fourlastor.game.input.state.OnGround;

/** Bag containing the player state machine, and the possible states it can get into. */
public class PlayerComponent implements Component {
    public final InputStateMachine stateMachine;
    public final OnGround onGround;
    public final Falling falling;
    public final Jumping jumping;

    public PlayerComponent(InputStateMachine stateMachine, OnGround onGround, Falling falling, Jumping jumping) {
        this.stateMachine = stateMachine;
        this.onGround = onGround;
        this.falling = falling;
        this.jumping = jumping;
    }
}
