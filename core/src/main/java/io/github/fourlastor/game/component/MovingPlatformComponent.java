package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovingPlatformComponent implements Component {
    public final Vector2 initialPosition;
    public boolean goingLeft;
    public final float speed;

    public MovingPlatformComponent(Vector2 initialPosition, boolean goingLeft, float speed) {
        this.initialPosition = initialPosition;
        this.goingLeft = goingLeft;
        this.speed = speed;
    }
}
