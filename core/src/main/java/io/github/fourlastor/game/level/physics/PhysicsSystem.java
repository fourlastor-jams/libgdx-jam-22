package io.github.fourlastor.game.level.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.level.Message;
import io.github.fourlastor.game.level.UserData;
import javax.inject.Inject;

public class PhysicsSystem extends IntervalSystem {

    private static final Family FAMILY_BUILDER =
            Family.all(BodyBuilderComponent.class).get();
    private static final Family FAMILY_BODY = Family.all(BodyComponent.class).get();
    private static final float STEP = 1f / 16f;

    private final World world;
    private final ComponentMapper<BodyBuilderComponent> bodyBuilders;
    private final ComponentMapper<BodyComponent> bodies;
    private final MessageManager messageManager;
    private final Factory factory;
    private final Cleaner cleaner;

    @Inject
    public PhysicsSystem(
            World world,
            ComponentMapper<BodyBuilderComponent> bodyBuilders,
            ComponentMapper<BodyComponent> bodies,
            MessageManager messageManager) {
        super(STEP);
        this.world = world;
        this.bodyBuilders = bodyBuilders;
        this.bodies = bodies;
        this.messageManager = messageManager;
        factory = new Factory();
        cleaner = new Cleaner();
    }

    @Override
    protected void updateInterval() {
        world.step(STEP, 6, 2);
    }

    @Override
    public void addedToEngine(Engine engine) {
        world.setContactListener(contactListener);
        engine.addEntityListener(FAMILY_BUILDER, factory);
        engine.addEntityListener(FAMILY_BODY, cleaner);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(factory);
        engine.removeEntityListener(cleaner);
        world.setContactListener(null);
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

    private final ContactListener contactListener = new ContactListener() {

        @Override
        public void beginContact(Contact contact) {}

        private void checkCollision(Contact contact, Fixture playerFixture, Fixture platformFixture) {
            Body playerBody = playerFixture.getBody();
            Body platformBody = platformFixture.getBody();
            float playerBottom = playerBody.getPosition().y - 0.5f;
            double platformTop = platformBody.getPosition().y + 0.2;
            boolean shouldNotCollide = playerBottom < platformTop;
            if (shouldNotCollide) {
                contact.setEnabled(false);
            } else {
                messageManager.dispatchMessage(Message.PLAYER_ON_GROUND.ordinal());
            }
        }

        @Override
        public void endContact(Contact contact) {
            contact.setEnabled(true);
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            if (UserData.PLAYER == fixtureA.getUserData() && UserData.PLATFORM == fixtureB.getUserData()) {
                checkCollision(contact, fixtureA, fixtureB);
            } else if (UserData.PLATFORM == fixtureA.getUserData() && UserData.PLAYER == fixtureB.getUserData()) {
                checkCollision(contact, fixtureB, fixtureA);
            }
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {}
    };
}
