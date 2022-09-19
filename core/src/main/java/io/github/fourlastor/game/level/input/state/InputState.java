package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;

public abstract class InputState implements State<Entity> {

    protected final ComponentMapper<PlayerComponent> players;
    protected final ComponentMapper<BodyComponent> bodies;

    public InputState(ComponentMapper<PlayerComponent> players, ComponentMapper<BodyComponent> bodies) {
        this.players = players;
        this.bodies = bodies;
    }

    @Override
    public void enter(Entity entity) {}

    @Override
    public void update(Entity entity) {}

    @Override
    public void exit(Entity entity) {}

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        return false;
    }

    public boolean keyDown(Entity entity, int keycode) {
        return false;
    }
}