package io.github.fourlastor.game.level.blueprint.definitions;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class MovingPlatform extends Platform {

    public final Speed speed;
    public final List<Vector2> path;

    public MovingPlatform(Vector2 position, Type type, Width width, Speed speed, List<Vector2> path) {
        super(position, type, width);
        this.speed = speed;
        this.path = path;
    }
}
