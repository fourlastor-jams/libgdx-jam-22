package io.github.fourlastor.game.level.blueprint.definitions;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class SawBlade {

    public final Vector2 position;
    public final Speed speed;
    public final List<Vector2> path;

    public SawBlade(Vector2 position, Speed speed, List<Vector2> path) {
        this.position = position;
        this.speed = speed;
        this.path = path;
    }

    public enum Speed implements MovementSpeed {
        SLOW(0.5f),
        MEDIUM(1f),
        FAST(1.5f);

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
