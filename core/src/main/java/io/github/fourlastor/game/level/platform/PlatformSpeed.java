package io.github.fourlastor.game.level.platform;

public enum PlatformSpeed {
    IMMOBILE(0f),
    SLOW(0.5f),
    MEDIUM(1f),
    FAST(1.5f);

    public final float speed;

    PlatformSpeed(float speed) {
        this.speed = speed;
    }
}
