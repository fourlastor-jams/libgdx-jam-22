package io.github.fourlastor.game.input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;
import javax.inject.Provider;

public class PlayerInputSystem extends IteratingSystem implements EntityListener {

    private static final Family FAMILY = Family.all(PlayerComponent.class).get();
    private final ComponentMapper<PlayerComponent> players;
    private final Provider<InputState.OnGround> onGroundProvider;
    private final InputStateMachine.Factory stateMachineFactory;

    @Inject
    public PlayerInputSystem(
            ComponentMappers componentMappers,
            Provider<InputState.OnGround> onGroundProvider,
            InputStateMachine.Factory stateMachineFactory) {
        super(FAMILY);
        players = componentMappers.get(PlayerComponent.class);
        this.onGroundProvider = onGroundProvider;
        this.stateMachineFactory = stateMachineFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        players.get(entity).stateMachine.update();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        PlayerComponent player = players.get(entity);
        player.onGround = onGroundProvider.get();
        player.stateMachine = stateMachineFactory.create(entity, player.onGround);
        player.stateMachine.getCurrentState().enter(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {}
}
