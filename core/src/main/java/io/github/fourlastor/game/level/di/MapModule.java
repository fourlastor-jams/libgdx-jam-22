package io.github.fourlastor.game.level.di;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.blueprint.definitions.Chunk;
import io.github.fourlastor.game.level.blueprint.definitions.LevelDefinitions;
import io.github.fourlastor.game.level.blueprint.definitions.MovementSpeed;
import io.github.fourlastor.game.level.blueprint.definitions.MovingPlatform;
import io.github.fourlastor.game.level.blueprint.definitions.Platform;
import io.github.fourlastor.game.level.blueprint.definitions.SawBlade;
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
        return new Chunk(size, staticPlatforms(level, size), sawBlades(level, size));
    }

    private List<SawBlade> sawBlades(JsonValue level, GridPoint2 size) {
        JsonValue entities = entities(level);
        List<SawBlade> sawBlades = new ArrayList<>();
        for (int i = 0; i < entities.size; i++) {
            JsonValue entity = entities.get(i);
            if (!identifier(entity).equals("Sawblade")) {
                continue;
            }
            float[] grid = getGrid(entity);
            float[] pivot = pivot(entity);
            SawBlade.Speed speed = sawBladeSpeed(entity);
            List<Vector2> path = path(entity, size, pivot);
            sawBlades.add(new SawBlade(getPosition(size, grid, pivot), speed, path));
        }
        return sawBlades;
    }

    private GridPoint2 size(JsonValue level) {
        return new GridPoint2(level.getInt("pxWid") / 32, level.getInt("pxHei") / 32);
    }

    private List<Platform> staticPlatforms(JsonValue level, GridPoint2 size) {
        JsonValue entities = entities(level);
        ArrayList<Platform> platforms = new ArrayList<>();
        for (int i = 0; i < entities.size; i++) {
            JsonValue entity = entities.get(i);
            String identifier = identifier(entity);
            if (!identifier.startsWith("Platform")) {
                continue;
            }
            Platform.Width width = width(identifier);

            float[] grid = getGrid(entity);
            float[] pivot = pivot(entity);
            MovingPlatform.Speed speed = platformSpeed(entity);
            List<Vector2> path = path(entity, size, pivot);
            if (speed == null || path.isEmpty()) {
                platforms.add(new Platform(getPosition(size, grid, pivot), Platform.Type.SMALL_GRID, width));
            } else {
                platforms.add(new MovingPlatform(
                        getPosition(size, grid, pivot), Platform.Type.SMALL_GRID, width, speed, path));
            }
        }
        return platforms;
    }

    private String identifier(JsonValue entity) {
        return entity.getString("__identifier");
    }

    private JsonValue entities(JsonValue level) {
        return level.get("layerInstances").get(0).get("entityInstances");
    }

    private List<Vector2> path(JsonValue entity, GridPoint2 size, float[] pivot) {
        JsonValue fieldInstances = entity.get("fieldInstances");
        for (int j = 0; j < fieldInstances.size; j++) {
            JsonValue field = fieldInstances.get(j);
            if (identifier(field).equals("Path")) {
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

    private MovingPlatform.Speed platformSpeed(JsonValue entity) {
        return speed(entity, MovingPlatform.Speed.SLOW, MovingPlatform.Speed.MEDIUM, MovingPlatform.Speed.FAST);
    }

    private SawBlade.Speed sawBladeSpeed(JsonValue entity) {
        return speed(entity, SawBlade.Speed.SLOW, SawBlade.Speed.MEDIUM, SawBlade.Speed.FAST);
    }

    private <T extends MovementSpeed> T speed(JsonValue entity, T slow, T medium, T fast) {
        JsonValue fieldInstances = entity.get("fieldInstances");
        for (int j = 0; j < fieldInstances.size; j++) {
            JsonValue field = fieldInstances.get(j);
            if (identifier(field).equals("Speed")) {
                String value = field.getString("__value");
                if ("Slow".equals(value)) {
                    return slow;
                } else if ("Medium".equals(value)) {
                    return medium;
                } else if ("Fast".equals(value)) {
                    return fast;
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

    private Platform.Width width(String identifier) {
        Platform.Width width;
        if (identifier.endsWith("9")) {
            width = Platform.Width.NINE;
        } else if (identifier.endsWith("4")) {
            width = Platform.Width.FOUR;
        } else {
            width = Platform.Width.ONE;
        }
        return width;
    }
}
