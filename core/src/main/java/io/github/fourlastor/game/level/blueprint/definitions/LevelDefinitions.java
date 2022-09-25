package io.github.fourlastor.game.level.blueprint.definitions;

import java.util.List;

public class LevelDefinitions {

    public final Chunk initial;
    public final List<Chunk> chunks;

    public LevelDefinitions(Chunk initial, List<Chunk> chunks) {
        this.initial = initial;
        this.chunks = chunks;
    }
}
