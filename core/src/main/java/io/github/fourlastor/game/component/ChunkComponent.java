package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;

public class ChunkComponent implements Component {
    public final float y;

    public ChunkComponent(float y) {
        this.y = y;
    }
}
