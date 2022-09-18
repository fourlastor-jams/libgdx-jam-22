package io.github.fourlastor.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

/**
 * Coordinates the movement between each pair of scene2d actor and box2d body.
 * Actors follow the bodies.
 */
public class ActorFollowBodySystem extends IteratingSystem {

    private static final Family FAMILY =
            Family.all(BodyComponent.class, ActorComponent.class).get();
    private final ComponentMapper<BodyComponent> bodies;
    private final ComponentMapper<ActorComponent> actors;

    @Inject
    public ActorFollowBodySystem(ComponentMappers componentMappers) {
        super(FAMILY);
        bodies = componentMappers.get(BodyComponent.class);
        actors = componentMappers.get(ActorComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 center = bodies.get(entity).body.getPosition();
        Actor actor = actors.get(entity).actor;
        actor.setPosition(center.x - actor.getWidth() / 2, center.y - actor.getHeight() / 2);
    }
}
