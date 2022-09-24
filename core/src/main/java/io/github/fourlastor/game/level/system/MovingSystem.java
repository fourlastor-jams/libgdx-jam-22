package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.MovingComponent;
import java.util.List;
import javax.inject.Inject;

public class MovingSystem extends IteratingSystem {
    private static final Family FAMILY =
            Family.all(MovingComponent.class, BodyComponent.class).get();

    private final ComponentMapper<BodyComponent> bodies;
    private final ComponentMapper<MovingComponent> movables;
    private final Vector2 velocity = new Vector2();

    @Inject
    public MovingSystem(ComponentMapper<BodyComponent> bodies, ComponentMapper<MovingComponent> movables) {
        super(FAMILY);
        this.bodies = bodies;
        this.movables = movables;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovingComponent movingComponent = movables.get(entity);
        Body body = bodies.get(entity).body;
        Vector2 position = body.getPosition();
        List<Vector2> path = movingComponent.path;
        int current = movingComponent.position;
        int next = (current + 1) % path.size();
        Vector2 destination = path.get(next);
        float dst = position.dst(destination);
        if (dst > 0.1f) {
            velocity.set(destination).sub(position).nor().scl(movingComponent.speed);
            body.setLinearVelocity(velocity);
        } else {
            movingComponent.position = next;
        }
    }
}
