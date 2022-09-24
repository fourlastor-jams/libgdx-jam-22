package io.github.fourlastor.game.level.di;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.definitions.Chunk;
import io.github.fourlastor.game.level.definitions.LevelDefinitions;
import io.github.fourlastor.game.level.definitions.MovingPlatform;
import io.github.fourlastor.game.level.definitions.Platform;
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

        return new LevelDefinitions(initialLevel(levels), otherLevels(levels));
    }

    private Chunk initialLevel(JsonValue levels) {
        return chunk(levels.get(0));
    }

    private List<Chunk> otherLevels(JsonValue levels) {
        List<Chunk> otherLevels = new ArrayList<>(levels.size - 1);
        for (int i = 1; i < levels.size; i++) {
            otherLevels.add(chunk(levels.get(i)));
        }
        return otherLevels;
    }

    private Chunk chunk(JsonValue level) {
        GridPoint2 size = size(level);
        return new Chunk(size, staticPlatforms(level, size));
    }

    private GridPoint2 size(JsonValue level) {
        return new GridPoint2(level.getInt("pxWid") / 32, level.getInt("pxWid") / 32);
    }

    private List<Platform> staticPlatforms(JsonValue level, GridPoint2 size) {
        JsonValue entities = level.get("layerInstances").get(0).get("entityInstances");
        ArrayList<Platform> platforms = new ArrayList<>();
        for (int i = 0; i < entities.size; i++) {
            JsonValue entity = entities.get(i);
            String identifier = entity.getString("__identifier");
            if (!identifier.startsWith("Platform")) {
                continue;
            }
            PlatformSpec.Width width = width(identifier);

            float[] grid = getGrid(entity);
            float[] pivot = pivot(entity);
            PlatformSpec.Speed speed = speed(entity);
            List<Vector2> path = path(entity, size, pivot);
            if (speed == null || path.isEmpty()) {
                platforms.add(new Platform(getPosition(size, grid, pivot), PlatformSpec.Type.SMALL_GRID, width));
            } else {
                platforms.add(new MovingPlatform(
                        getPosition(size, grid, pivot), PlatformSpec.Type.SMALL_GRID, width, speed, path));
            }
        }
        return platforms;
    }

    private List<Vector2> path(JsonValue entity, GridPoint2 size, float[] pivot) {
        JsonValue fieldInstances = entity.get("fieldInstances");
        for (int j = 0; j < fieldInstances.size; j++) {
            JsonValue field = fieldInstances.get(j);
            if (field.getString("__identifier").equals("Path")) {
                JsonValue value = field.get("__value");
                List<Vector2> path = new ArrayList<>(value.size);
                for (int i = 0; i < value.size; i++) {
                    JsonValue point = value.get(i);
                    path.add(new Vector2(
                            size.x - point.getFloat("cx") - pivot[0], size.y - point.getFloat("cy") - pivot[1]));
                }
                return path;
            }
        }

        return Collections.emptyList();
    }

    private PlatformSpec.Speed speed(JsonValue entity) {
        JsonValue fieldInstances = entity.get("fieldInstances");
        for (int j = 0; j < fieldInstances.size; j++) {
            JsonValue field = fieldInstances.get(j);
            if (field.getString("__identifier").equals("Speed")) {
                String value = field.getString("__value");
                if ("Slow".equals(value)) {
                    return PlatformSpec.Speed.SLOW;
                } else if ("Normal".equals(value)) {
                    return PlatformSpec.Speed.MEDIUM;
                } else if ("Fast".equals(value)) {
                    return PlatformSpec.Speed.FAST;
                } else {
                    return null;
                }
            }
        }

        return null;
    }

    private Vector2 getPosition(GridPoint2 size, float[] grid, float[] pivot) {
        return new Vector2(size.x - grid[0] - pivot[0], size.y - grid[1] - pivot[1]);
    }

    private float[] pivot(JsonValue entity) {
        return entity.get("__pivot").asFloatArray();
    }

    private float[] getGrid(JsonValue entity) {
        return entity.get("__grid").asFloatArray();
    }

    private PlatformSpec.Width width(String identifier) {
        PlatformSpec.Width width;
        if (identifier.endsWith("9")) {
            width = PlatformSpec.Width.NINE;
        } else if (identifier.endsWith("4")) {
            width = PlatformSpec.Width.FOUR;
        } else {
            width = PlatformSpec.Width.ONE;
        }
        return width;
    }
}
