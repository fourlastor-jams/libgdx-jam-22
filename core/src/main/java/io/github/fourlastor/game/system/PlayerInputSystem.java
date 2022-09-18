package io.github.fourlastor.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.input.InputState;
import io.github.fourlastor.game.input.InputStateMachine;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class PlayerInputSystem extends IteratingSystem implements EntityListener {

    private static final Family FAMILY = Family.all(PlayerComponent.class).get();
    private final ComponentMapper<PlayerComponent> players;

    @Inject
    public PlayerInputSystem(ComponentMappers componentMappers) {
        super(FAMILY);
        players = componentMappers.get(PlayerComponent.class);
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
        player.onGround = new InputState.OnGround();
        player.stateMachine = new InputStateMachine(entity, player.onGround);
        player.stateMachine.getCurrentState().enter(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {}
}
