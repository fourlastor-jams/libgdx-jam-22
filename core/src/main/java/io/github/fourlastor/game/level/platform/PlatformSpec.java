package io.github.fourlastor.game.level.platform;

public class PlatformSpec {

    public final PlatformWidth width;
    public final PlatformType type;
    public final PlatformSpeed speed;
    public final float x;
    public final float y;

    public PlatformSpec(PlatformWidth width, PlatformType type, PlatformSpeed speed, float x, float y) {
        this.width = width;
        this.type = type;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }
}
