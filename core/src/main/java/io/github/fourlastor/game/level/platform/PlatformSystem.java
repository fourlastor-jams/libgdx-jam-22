package io.github.fourlastor.game.level.platform;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.MovingPlatformComponent;
import javax.inject.Inject;

public class PlatformSystem extends IteratingSystem {
    private static final Family FAMILY =
            Family.all(MovingPlatformComponent.class, BodyComponent.class).get();

    private final ComponentMapper<BodyComponent> bodies;
    private final ComponentMapper<MovingPlatformComponent> movingPlatforms;

    @Inject
    public PlatformSystem(
            ComponentMapper<BodyComponent> bodies, ComponentMapper<MovingPlatformComponent> movingPlatforms) {
        super(FAMILY);
        this.bodies = bodies;
        this.movingPlatforms = movingPlatforms;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Body body = bodies.get(entity).body;
        Vector2 position = body.getPosition();
        MovingPlatformComponent movingPlatform = movingPlatforms.get(entity);
        if (movingPlatform.initialPosition.dst(position) >= 4) {
            movingPlatform.goingLeft = !movingPlatform.goingLeft;
        }
        float speed = movingPlatform.goingLeft ? -movingPlatform.speed : movingPlatform.speed;
        body.setLinearVelocity(speed, 0f);
    }
}