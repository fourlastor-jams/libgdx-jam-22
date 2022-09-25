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

    public enum Speed implements MovementSpeed {
        SLOW(1f),
        MEDIUM(2f),
        FAST(2.5f);

        public final float speed;

        Speed(float speed) {
            this.speed = speed;
        }

        @Override
        public float speed() {
            return speed;
        }
    }
}
