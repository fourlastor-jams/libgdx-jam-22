package io.github.fourlastor.game.level.platform;

import com.badlogic.ashley.core.Entity;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.EntitiesFactory;
import io.github.fourlastor.game.level.definitions.Chunk;
import io.github.fourlastor.game.level.definitions.LevelDefinitions;
import io.github.fourlastor.game.level.definitions.Platform;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@ScreenScoped
public class ChunkFactory {

    private final LevelDefinitions definitions;
    private final EntitiesFactory factory;

    private float dY = 0f;

    @Inject
    public ChunkFactory(LevelDefinitions definitions, EntitiesFactory factory) {
        this.definitions = definitions;
        this.factory = factory;
    }

    public List<Entity> chunk(int index) {
        if (index == 0) {
            return addInitial();
        } else {
            return addNext(index - 1);
        }
    }

    private List<Entity> addInitial() {
        return addPlatform(definitions.initial);
    }

    private List<Entity> addNext(int index) {
        return addPlatform(definitions.chunks.get(index));
    }

    private List<Entity> addPlatform(Chunk chunk) {
        List<Entity> entities = new ArrayList<>(chunk.platforms.size());
        for (Platform platform : chunk.platforms) {
            entities.add(factory.platform(platform, dY));
        }
        dY += chunk.size.y;
        return entities;
    }
}
