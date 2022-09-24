package io.github.fourlastor.game.level.definitions;

import com.badlogic.gdx.math.Vector2;
import io.github.fourlastor.game.level.platform.PlatformSpec;
import java.util.List;

public class MovingPlatform extends Platform {

    public final PlatformSpec.Speed speed;
    public final List<Vector2> path;

    public MovingPlatform(
            Vector2 position,
            PlatformSpec.Type type,
            PlatformSpec.Width width,
            PlatformSpec.Speed speed,
            List<Vector2> path) {
        super(position, type, width);
        this.speed = speed;
        this.path = path;
    }
}
