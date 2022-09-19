package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import javax.inject.Inject;

/**
 * Moves platforms down when the player goes up
 */
public class MovePlatformsDownSystem extends EntitySystem {

    private final Camera camera;

    @Inject
    public MovePlatformsDownSystem(Camera camera) {
        this.camera = camera;
    }

    float timePassed = 0;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timePassed += deltaTime;
        if (timePassed < 5) {
            return;
        }

        camera.position.add(0f, deltaTime, 0f);
    }
}
