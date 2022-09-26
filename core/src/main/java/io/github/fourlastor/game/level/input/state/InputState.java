package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;

public abstract class InputState implements State<Entity> {

    protected final ComponentMapper<PlayerComponent> players;
    protected final ComponentMapper<BodyComponent> bodies;
    protected final ComponentMapper<AnimatedImageComponent> images;

    public InputState(
            ComponentMapper<PlayerComponent> players,
            ComponentMapper<BodyComponent> bodies,
            ComponentMapper<AnimatedImageComponent> images) {
        this.players = players;
        this.bodies = bodies;
        this.images = images;
    }

    protected abstract Animation<TextureRegion> animation();

    @Override
    public void enter(Entity entity) {
        images.get(entity).image.setAnimation(animation());
    }

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

    public boolean keyUp(Entity entity, int keycode) {
        return false;
    }

    public boolean touchDown(Entity entity, int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Entity entity, int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
