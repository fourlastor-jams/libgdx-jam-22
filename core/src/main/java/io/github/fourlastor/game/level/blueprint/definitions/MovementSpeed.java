package io.github.fourlastor.game.level.blueprint.definitions;

public enum MovementSpeed {
    SLOW(0.5f),
    MEDIUM(1f),
    FAST(1.5f);

    public final float speed;

    MovementSpeed(float speed) {
        this.speed = speed;
    }
}
