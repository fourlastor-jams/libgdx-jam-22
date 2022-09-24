package io.github.fourlastor.game.level.input.state;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.PlayerAnimationsFactory;
import javax.inject.Inject;
import javax.inject.Named;

public class ChargeJump extends InputState {

    private final Animation<TextureRegion> animation;

    @Inject
    public ChargeJump(
            ComponentMapper<PlayerComponent> players,
            ComponentMapper<BodyComponent> bodies,
            ComponentMapper<AnimatedImageComponent> images,
            @Named(PlayerAnimationsFactory.ANIMATION_CHARGE_JUMP) Animation<TextureRegion> animation) {
        super(players, bodies, images);
        this.animation = animation;
    }

    @Override
    protected Animation<TextureRegion> animation() {
        return animation;
    }

    @Override
    public void enter(Entity entity) {
        super.enter(entity);
        players.get(entity).charge = 0f;
    }

    @Override
    public void update(Entity entity) {
        super.update(entity);
        players.get(entity).charge += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void exit(Entity entity) {
        super.exit(entity);
        PlayerComponent player = players.get(entity);
        player.charge = Math.min(1f, player.charge);
    }

    @Override
    public boolean keyUp(Entity entity, int keycode) {
        if (keycode == Input.Keys.SPACE) {
            PlayerComponent player = players.get(entity);
            player.stateMachine.changeState(player.jumping);
            return true;
        }

        return false;
    }
}
