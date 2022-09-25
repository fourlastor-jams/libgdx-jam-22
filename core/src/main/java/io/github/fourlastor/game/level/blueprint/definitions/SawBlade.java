package io.github.fourlastor.game.level.blueprint.definitions;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class SawBlade {

    public final Vector2 position;
    public final MovementSpeed speed;
    public final List<Vector2> path;

    public SawBlade(Vector2 position, MovementSpeed speed, List<Vector2> path) {
        this.position = position;
        this.speed = speed;
        this.path = path;
    }
}
