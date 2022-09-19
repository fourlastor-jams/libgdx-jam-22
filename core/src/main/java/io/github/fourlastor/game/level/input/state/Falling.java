package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.input.Message;
import javax.inject.Inject;

public class Falling extends InputState {

    private final TextureAtlas textureAtlas;

    @Inject
    public Falling(
            ComponentMapper<PlayerComponent> players,
            ComponentMapper<BodyComponent> bodies,
            ComponentMapper<AnimatedImageComponent> images,
            TextureAtlas textureAtlas) {
        super(players, bodies, images);
        this.textureAtlas = textureAtlas;
    }

    @Override
    public void enter(Entity entity) {
        super.enter(entity);
        images.get(entity)
                .image
                .setAnimation(new Animation<>(0.2f, textureAtlas.findRegions("player/Falling/Falling")));
    }

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        if (telegram.message == Message.PLAYER_ON_GROUND.ordinal()) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.onGround);
            return true;
        }
        return false;
    }
}
