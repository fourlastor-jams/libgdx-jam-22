package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import javax.inject.Inject;

public class GarbageCollectionSystem extends IntervalIteratingSystem {

    private static final Family FAMILY =
            Family.all(BodyComponent.class).exclude(PlayerComponent.class).get();
    private final Camera camera;
    private Engine engine;

    private final ComponentMapper<BodyComponent> removals;

    @Inject
    public GarbageCollectionSystem(Camera camera, ComponentMapper<BodyComponent> removals) {
        super(FAMILY, 1f);
        this.camera = camera;
        this.removals = removals;
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
    protected void processEntity(Entity entity) {
        Body candidate = removals.get(entity).body;
        if (candidate.getPosition().y < camera.position.y - camera.viewportHeight / 2) {
            engine.removeEntity(entity);
        }
    }
}
