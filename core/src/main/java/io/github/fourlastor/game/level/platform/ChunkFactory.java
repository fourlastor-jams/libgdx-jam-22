package io.github.fourlastor.game.level.platform;

import com.badlogic.ashley.core.Entity;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.EntitiesFactory;
import io.github.fourlastor.game.level.platform.definitions.Chunk;
import io.github.fourlastor.game.level.platform.definitions.LevelDefinitions;
import io.github.fourlastor.game.level.platform.definitions.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;

@ScreenScoped
public class ChunkFactory {

    private final LevelDefinitions definitions;
    private final EntitiesFactory factory;
    private final Random random;

    private float dY = 0f;
    private int bottomIndex = 0;

    @Inject
    public ChunkFactory(LevelDefinitions definitions, EntitiesFactory factory, Random random) {
        this.definitions = definitions;
        this.factory = factory;
        this.random = random;
    }

    public List<Entity> generate() {
        List<Entity> entities = chunk(bottomIndex);
        bottomIndex += 1;
        return entities;
    }

    public List<Entity> chunk(int index) {
        if (index == 0) {
            return addInitial();
        } else {
            return addNext(random.nextInt(definitions.chunks.size()));
        }
    }

    private List<Entity> addInitial() {
        return addPlatforms(definitions.initial);
    }

    private List<Entity> addNext(int index) {
        return addPlatforms(definitions.chunks.get(index));
    }

    private List<Entity> addPlatforms(Chunk chunk) {
        List<Entity> entities = new ArrayList<>(chunk.platforms.size() + 1);
        float newTop = dY + chunk.size.y;
        for (Platform platform : chunk.platforms) {
            entities.add(factory.platform(platform, dY, newTop));
        }
        entities.add(factory.chunkRemoval(newTop));
        dY = newTop;
        return entities;
    }
}
