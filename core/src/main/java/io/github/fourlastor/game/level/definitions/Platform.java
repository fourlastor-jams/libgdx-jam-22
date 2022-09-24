package io.github.fourlastor.game.level.definitions;

import com.badlogic.gdx.math.Vector2;
import io.github.fourlastor.game.level.platform.PlatformSpec;

public class Platform {
    public final Vector2 position;
    public final PlatformSpec.Type type;
    public final PlatformSpec.Width width;

    public Platform(Vector2 position, PlatformSpec.Type type, PlatformSpec.Width width) {
        this.position = position;
        this.type = type;
        this.width = width;
    }
}
