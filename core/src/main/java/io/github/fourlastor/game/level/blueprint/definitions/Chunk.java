package io.github.fourlastor.game.level.blueprint.definitions;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public class Chunk {

    public final GridPoint2 size;
    public final List<Platform> platforms;
    public final List<SawBlade> sawBlades;

    public Chunk(GridPoint2 size, List<Platform> platforms, List<SawBlade> sawBlades) {
        this.size = size;
        this.platforms = platforms;
        this.sawBlades = sawBlades;
    }
}
