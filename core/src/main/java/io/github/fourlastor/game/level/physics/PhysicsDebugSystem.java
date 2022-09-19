package io.github.fourlastor.game.level.physics;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import javax.inject.Inject;

public class PhysicsDebugSystem extends EntitySystem {

    private final Camera camera;
    private final World world;

    private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();

    @Inject
    public PhysicsDebugSystem(Camera camera, World world) {
        this.camera = camera;
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        renderer.render(world, camera.combined);
    }
}
