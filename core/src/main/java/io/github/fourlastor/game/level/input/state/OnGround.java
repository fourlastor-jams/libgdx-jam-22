package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import javax.inject.Inject;

public class OnGround extends InputState {

    private final TextureAtlas textureAtlas;

    @Inject
    public OnGround(
            ComponentMapper<PlayerComponent> players,
            ComponentMapper<BodyComponent> bodies,
            TextureAtlas textureAtlas,
            ComponentMapper<AnimatedImageComponent> images) {
        super(players, bodies, images);
        this.textureAtlas = textureAtlas;
    }

    @Override
    public void enter(Entity entity) {
        super.enter(entity);
        images.get(entity)
                .image
                .setAnimation(new Animation<>(0.2f, textureAtlas.findRegions("player/OnGround/OnGround")));
    }

    @Override
    public void update(Entity entity) {
        if (bodies.get(entity).body.getLinearVelocity().y < 0) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.falling);
        }
    }

    @Override
    public boolean keyDown(Entity entity, int keycode) {
        if (keycode == Input.Keys.SPACE) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.jumping);
            return true;
        }
        return super.keyDown(entity, keycode);
    }
}
