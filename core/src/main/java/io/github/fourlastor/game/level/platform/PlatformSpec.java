package io.github.fourlastor.game.level.platform;

public class PlatformSpec {

    public final Width width;
    public final Type type;
    public final Speed speed;
    public final float x;
    public final float y;

    public PlatformSpec(Width width, Type type, Speed speed, float x, float y) {
        this.width = width;
        this.type = type;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    public enum Speed {
        IMMOBILE(0f),
        SLOW(0.5f),
        MEDIUM(1f),
        FAST(1.5f);

        public final float speed;

        Speed(float speed) {
            this.speed = speed;
        }
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
