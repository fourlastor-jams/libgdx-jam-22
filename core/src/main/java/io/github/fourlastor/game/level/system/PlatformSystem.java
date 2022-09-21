package io.github.fourlastor.game.level.system;

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
import javax.inject.Inject;

public class PlatformSystem extends IteratingSystem {
    private static final Family FAMILY =
            Family.all(MovingPlatformComponent.class, BodyComponent.class).get();

    private final Camera camera;
    private final ComponentMapper<BodyComponent> bodies;
    private final ComponentMapper<MovingPlatformComponent> movingPlatforms;
    private Engine engine;

    @Inject
    public PlatformSystem(
            Camera camera,
            ComponentMapper<BodyComponent> bodies,
            ComponentMapper<MovingPlatformComponent> movingPlatforms) {
        super(FAMILY);
        this.camera = camera;
        this.bodies = bodies;
        this.movingPlatforms = movingPlatforms;
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
        if (camera.position.y > position.y && !camera.frustum.pointInFrustum(position.x, position.y, 0f)) {
            engine.removeEntity(entity);
            return;
        }
        MovingPlatformComponent movingPlatform = movingPlatforms.get(entity);
        if (movingPlatform.initialPosition.dst(position) >= 4) {
            movingPlatform.goingLeft = !movingPlatform.goingLeft;
        }
        if (movingPlatform.goingLeft) {
            body.setLinearVelocity(-1f, 0f);
        } else {
            body.setLinearVelocity(1f, 0f);
        }
    }
}
