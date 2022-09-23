package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import javax.inject.Inject;

/**
 * Moves platforms down when the player goes up
 */
public class CameraMovementSystem extends IteratingSystem {

    private static final Family FAMILY_PLAYER =
            Family.all(PlayerComponent.class, BodyComponent.class).get();
    private final Camera camera;
    private final ComponentMapper<BodyComponent> bodies;

    @Inject
    public CameraMovementSystem(Camera camera, ComponentMapper<BodyComponent> bodies) {
        super(FAMILY_PLAYER);
        this.camera = camera;
        this.bodies = bodies;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 bodyPosition = bodies.get(entity).body.getPosition();
        camera.position.y = Math.max(camera.position.y, bodyPosition.y);
        //        uncomment for debug
        //        camera.position.y += deltaTime * 2f;
        //        camera.position.y %= 105;
    }
}
