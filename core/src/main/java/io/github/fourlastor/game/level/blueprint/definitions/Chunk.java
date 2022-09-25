package io.github.fourlastor.game.level.blueprint.definitions;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public class Chunk {

    public final GridPoint2 size;
    public final List<Platform> platforms;

    public Chunk(GridPoint2 size, List<Platform> platforms) {
        this.size = size;
        this.platforms = platforms;
    }
}
