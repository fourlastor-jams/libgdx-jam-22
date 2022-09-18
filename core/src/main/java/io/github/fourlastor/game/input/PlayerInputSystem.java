package io.github.fourlastor.game.input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.component.PlayerRequestComponent;
import io.github.fourlastor.game.input.state.Falling;
import io.github.fourlastor.game.input.state.Jumping;
import io.github.fourlastor.game.input.state.OnGround;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;
import javax.inject.Provider;

public class PlayerInputSystem extends IteratingSystem {

    private static final Family FAMILY_REQUEST =
            Family.all(PlayerRequestComponent.class, BodyComponent.class).get();
    private static final Family FAMILY =
            Family.all(PlayerComponent.class, BodyComponent.class).get();

    private final InputMultiplexer inputMultiplexer;
    private final PlayerSetup playerSetup;
    private final ComponentMapper<PlayerComponent> players;
    private final World world;
    private final MessageManager messageManager;

    @Inject
    public PlayerInputSystem(
            InputMultiplexer inputMultiplexer,
            PlayerSetup playerSetup,
            ComponentMappers componentMappers,
            World world,
            MessageManager messageManager) {
        super(FAMILY);
        this.inputMultiplexer = inputMultiplexer;
        this.playerSetup = playerSetup;
        players = componentMappers.get(PlayerComponent.class);
        this.world = world;
        this.messageManager = messageManager;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        players.get(entity).stateMachine.update();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        inputMultiplexer.addProcessor(inputProcessor);
        engine.addEntityListener(FAMILY_REQUEST, playerSetup);
        world.setContactListener(contactListener);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        world.setContactListener(null);
        engine.removeEntityListener(playerSetup);
        inputMultiplexer.removeProcessor(inputProcessor);
        super.removedFromEngine(engine);
    }

    public static class PlayerSetup implements EntityListener {

        private final Provider<OnGround> onGroundProvider;
        private final Provider<Falling> fallingProvider;
        private final Provider<Jumping> jumpingProvider;
        private final InputStateMachine.Factory stateMachineFactory;
        private final MessageManager messageManager;

        @Inject
        public PlayerSetup(
                Provider<OnGround> onGroundProvider,
                Provider<Falling> fallingProvider,
                Provider<Jumping> jumpingProvider,
                InputStateMachine.Factory stateMachineFactory,
                MessageManager messageManager) {
            this.onGroundProvider = onGroundProvider;
            this.fallingProvider = fallingProvider;
            this.jumpingProvider = jumpingProvider;
            this.stateMachineFactory = stateMachineFactory;
            this.messageManager = messageManager;
        }

        @Override
        public void entityAdded(Entity entity) {
            entity.remove(PlayerRequestComponent.class);
            Falling falling = fallingProvider.get();
            InputStateMachine stateMachine = stateMachineFactory.create(entity, falling);
            OnGround onGround = onGroundProvider.get();
            Jumping jumping = jumpingProvider.get();
            entity.add(new PlayerComponent(stateMachine, onGround, falling, jumping));
            stateMachine.getCurrentState().enter(entity);
            for (Message value : Message.values()) {
                messageManager.addListener(stateMachine, value.ordinal());
            }
        }

        @Override
        public void entityRemoved(Entity entity) {}
    }

    private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            for (Entity entity : getEntities()) {
                if (players.get(entity).stateMachine.keyDown(entity, keycode)) {
                    return true;
                }
            }
            return false;
        }
    };

    private final ContactListener contactListener = new ContactListener() {
        @Override
        public void beginContact(Contact contact) {
            if ("foot".equals(contact.getFixtureA().getUserData())
                    || "foot".equals(contact.getFixtureB().getUserData())) {
                messageManager.dispatchMessage(Message.PLAYER_ON_GROUND.ordinal());
            }
        }

        @Override
        public void endContact(Contact contact) {
            if ("foot".equals(contact.getFixtureA().getUserData())
                    || "foot".equals(contact.getFixtureB().getUserData())) {
                messageManager.dispatchMessage(Message.PLAYER_OFF_GROUND.ordinal());
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {}

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {}
    };
}
