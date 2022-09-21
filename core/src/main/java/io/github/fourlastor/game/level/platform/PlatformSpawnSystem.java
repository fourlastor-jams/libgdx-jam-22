package io.github.fourlastor.game.level.platform;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.MovingPlatformComponent;
import io.github.fourlastor.game.level.EntitiesFactory;
import javax.inject.Inject;

public class PlatformSpawnSystem extends IteratingSystem {
    private static final Family FAMILY =
            Family.all(MovingPlatformComponent.class, BodyComponent.class).get();

    private final Camera camera;
    private final ComponentMapper<BodyComponent> bodies;
    private Engine engine;
    private final EntitiesFactory factory;

    @Inject
    public PlatformSpawnSystem(Camera camera, ComponentMapper<BodyComponent> bodies, EntitiesFactory factory) {
        super(FAMILY);
        this.camera = camera;
        this.bodies = bodies;
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
        Body body = bodies.get(entity).body;
        Vector2 position = body.getPosition();
        // Remove platforms that are under the camera and out of view
        if (position.y < camera.position.y - camera.viewportHeight / 2) {
            engine.removeEntity(entity);
            engine.addEntity(factory.ground());
        }
    }
}
