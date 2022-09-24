package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;

public class ChunkRemovalComponent implements Component {
    public final float y;

    public ChunkRemovalComponent(float y) {
        this.y = y;
    }
}
