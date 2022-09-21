package io.github.fourlastor.game.level.platform;

public enum PlatformType {
    LARGE_GRID("0"),
    SMALL_GRID("1");

    public final String tileName;

    PlatformType(String tileName) {
        this.tileName = tileName;
    }
}
