package io.github.fourlastor.game.level.blueprint;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import io.github.fourlastor.game.component.ChunkComponent;
import io.github.fourlastor.game.component.ChunkRemovalComponent;
import java.util.List;
import javax.inject.Inject;

public class ChunkSpawnSystem extends IteratingSystem {
    private static final Family FAMILY_REMOVAL =
            Family.all(ChunkRemovalComponent.class).get();
    private static final Family FAMILY_CHUNKS = Family.all(ChunkComponent.class).get();

    private final Camera camera;
    private final ComponentMapper<ChunkRemovalComponent> removals;
    private final ComponentMapper<ChunkComponent> chunks;
    private final ChunkFactory factory;
    private Engine engine;

    @Inject
    public ChunkSpawnSystem(
            Camera camera,
            ComponentMapper<ChunkRemovalComponent> removals,
            ComponentMapper<ChunkComponent> chunks,
            ChunkFactory factory) {
        super(FAMILY_REMOVAL);
        this.camera = camera;
        this.removals = removals;
        this.chunks = chunks;
        this.factory = factory;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.engine = null;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Remove platforms that are under the camera and out of view
        ChunkRemovalComponent candidate = removals.get(entity);
        if (candidate.y < camera.position.y - camera.viewportHeight / 2) {
            engine.removeEntity(entity);
            for (Entity toRemove : engine.getEntitiesFor(FAMILY_CHUNKS)) {
                if (chunks.get(toRemove).y <= candidate.y) {
                    engine.removeEntity(toRemove);
                }
            }
            List<Entity> generated = factory.generate();
            for (int i = 0; i < generated.size(); i++) {
                Entity toAdd = generated.get(i);
                engine.addEntity(toAdd);
            }
        }
    }
}
