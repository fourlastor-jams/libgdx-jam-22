package io.github.fourlastor.game.level.di;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.platform.PlatformSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;

@Module
public class MapModule {

    @Provides
    @ScreenScoped
    public JsonReader jsonReader() {
        return new JsonReader();
    }

    @Provides
    @Named("map")
    @ScreenScoped
    public JsonValue map(JsonReader reader) {
        return reader.parse(Gdx.files.internal("map/map.ldtk"));
    }

    @Provides
    @ScreenScoped
    public LevelDefinitions levelDefinitions(@Named("map") JsonValue json) {
        JsonValue levels = json.get("levels");

        return new LevelDefinitions(initialLevel(levels), Collections.emptyList());
    }

    private Chunk initialLevel(JsonValue levels) {
        return chunk(levels.get(0));
    }

    private Chunk chunk(JsonValue level) {
        GridPoint2 size = size(level);
        return new Chunk(size, staticPlatforms(level, size));
    }

    private GridPoint2 size(JsonValue level) {
        return new GridPoint2(level.getInt("pxWid") / 32, level.getInt("pxWid") / 32);
    }

    private List<StaticPlatform> staticPlatforms(JsonValue level, GridPoint2 size) {
        JsonValue entities = level.get("layerInstances").get(0).get("entityInstances");
        ArrayList<StaticPlatform> platforms = new ArrayList<>();
        for (int i = 0; i < entities.size; i++) {
            JsonValue entity = entities.get(i);
            String identifier = entity.getString("__identifier");
            if (!identifier.startsWith("Platform")) {
                continue;
            }
            PlatformSpec.Width width;
            if (identifier.endsWith("9")) {
                width = PlatformSpec.Width.NINE;
            } else if (identifier.endsWith("4")) {
                width = PlatformSpec.Width.FOUR;
            } else {
                width = PlatformSpec.Width.ONE;
            }

            float[] grid = entity.get("__grid").asFloatArray();
            float[] pivot = entity.get("__pivot").asFloatArray();

            platforms.add(new StaticPlatform(
                    new Vector2(size.x - (grid[0] + pivot[0]), size.y - (grid[1] + pivot[1])),
                    PlatformSpec.Type.SMALL_GRID,
                    width));
        }
        return platforms;
    }

    public static class LevelDefinitions {

        public final Chunk initial;
        public final List<Chunk> chunks;

        public LevelDefinitions(Chunk initial, List<Chunk> chunks) {
            this.initial = initial;
            this.chunks = chunks;
        }

        @Override
        public String toString() {
            return "LevelDefinitions{" + "initial=" + initial + ", chunks=" + chunks + '}';
        }
    }

    public static class Chunk {

        public final GridPoint2 size;
        public final List<StaticPlatform> staticPlatforms;

        public Chunk(GridPoint2 size, List<StaticPlatform> staticPlatforms) {
            this.size = size;
            this.staticPlatforms = staticPlatforms;
        }

        @Override
        public String toString() {
            return "Chunk{" + "size=" + size + ", staticPlatforms=" + staticPlatforms + '}';
        }
    }

    public static class StaticPlatform {
        public final Vector2 position;
        public final PlatformSpec.Type type;
        public final PlatformSpec.Width width;

        public StaticPlatform(Vector2 position, PlatformSpec.Type type, PlatformSpec.Width width) {
            this.position = position;
            this.type = type;
            this.width = width;
        }

        @Override
        public String toString() {
            return "StaticPlatform{" + "position=" + position + ", type=" + type + ", width=" + width + '}';
        }
    }
}
