package io.github.fourlastor.game.level.blueprint;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import io.github.fourlastor.game.component.ChunkRemovalComponent;
import java.util.List;
import javax.inject.Inject;

public class ChunkSpawnSystem extends IteratingSystem {
    private static final Family FAMILY_REMOVAL =
            Family.all(ChunkRemovalComponent.class).get();

    private final Camera camera;
    private final ComponentMapper<ChunkRemovalComponent> removals;
    private final ChunkFactory factory;
    private Engine engine;

    @Inject
    public ChunkSpawnSystem(Camera camera, ComponentMapper<ChunkRemovalComponent> removals, ChunkFactory factory) {
        super(FAMILY_REMOVAL);
        this.camera = camera;
        this.removals = removals;
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
            List<Entity> generated = factory.generate();
            for (int i = 0; i < generated.size(); i++) {
                Entity toAdd = generated.get(i);
                engine.addEntity(toAdd);
            }
        }
    }
}
