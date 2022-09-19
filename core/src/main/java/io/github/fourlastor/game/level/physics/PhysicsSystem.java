package io.github.fourlastor.game.level.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class PhysicsSystem extends IntervalSystem {

    private static final Family FAMILY_BUILDER =
            Family.all(BodyBuilderComponent.class).get();
    private static final Family FAMILY_BODY = Family.all(BodyComponent.class).get();
    private static final float STEP = 1f / 16f;

    private final World world;
    private final ComponentMapper<BodyBuilderComponent> bodyBuilders;
    private final ComponentMapper<BodyComponent> bodies;
    private final Factory factory;
    private final Cleaner cleaner;

    @Inject
    public PhysicsSystem(World world, ComponentMappers componentMappers) {
        super(STEP);
        this.world = world;
        bodyBuilders = componentMappers.get(BodyBuilderComponent.class);
        bodies = componentMappers.get(BodyComponent.class);
        factory = new Factory();
        cleaner = new Cleaner();
    }

    @Override
    protected void updateInterval() {
        world.step(STEP, 6, 2);
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(FAMILY_BUILDER, factory);
        engine.addEntityListener(FAMILY_BODY, cleaner);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(factory);
        engine.removeEntityListener(cleaner);
    }

    /** Creates a body in the world every time a body builder is added. */
    public class Factory implements EntityListener {

        @Override
        public void entityAdded(Entity entity) {
            BodyBuilderComponent component = bodyBuilders.get(entity);
            Body body = component.factory.build(world);
            entity.add(new BodyComponent(body));
            entity.remove(BodyBuilderComponent.class);
        }

        @Override
        public void entityRemoved(Entity entity) {
            // nothing
        }
    }

    /** Cleans up a body which has been removed from the engine. */
    public class Cleaner implements EntityListener {

        @Override
        public void entityAdded(Entity entity) {
            // nothing
        }

        @Override
        public void entityRemoved(Entity entity) {
            BodyComponent component = bodies.get(entity);
            if (component.body != null) {
                world.destroyBody(component.body);
                component.body = null;
            }
        }
    }
}
