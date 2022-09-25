package io.github.fourlastor.game.level.blueprint.definitions;

import com.badlogic.gdx.math.Vector2;

public class Platform {
    public final Vector2 position;
    public final Type type;
    public final Width width;

    public Platform(Vector2 position, Type type, Width width) {
        this.position = position;
        this.type = type;
        this.width = width;
    }

    public enum Type {
        LARGE_GRID("0"),
        SMALL_GRID("1");

        public final String tileName;

        Type(String tileName) {
            this.tileName = tileName;
        }
    }

    public enum Width {
        ONE(1),
        FOUR(4),
        NINE(9);

        public final int width;

        Width(int width) {
            this.width = width;
        }
    }
}
