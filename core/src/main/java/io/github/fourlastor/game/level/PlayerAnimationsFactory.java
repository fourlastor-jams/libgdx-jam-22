package io.github.fourlastor.game.level;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Named;

@Module
public class PlayerAnimationsFactory {

    public static final String ANIMATION_STANDING = "player/OnGround/OnGround";
    public static final String ANIMATION_FALLING = "player/Falling/Falling";
    public static final String ANIMATION_JUMPING = "player/Jumping/Jumping";
    public static final String ANIMATION_CHARGE_JUMP = "player/ChargeJump/ChargeJump";
    private static final float FRAME_DURATION = 0.1f;

    @Provides
    @ScreenScoped
    @Named(ANIMATION_STANDING)
    public Animation<TextureRegion> standing(TextureAtlas textureAtlas) {
        return new Animation<>(FRAME_DURATION, textureAtlas.findRegions(ANIMATION_STANDING), Animation.PlayMode.LOOP);
    }

    @Provides
    @ScreenScoped
    @Named(ANIMATION_FALLING)
    public Animation<TextureRegion> falling(TextureAtlas textureAtlas) {
        return new Animation<>(FRAME_DURATION, textureAtlas.findRegions(ANIMATION_FALLING), Animation.PlayMode.NORMAL);
    }

    @Provides
    @ScreenScoped
    @Named(ANIMATION_JUMPING)
    public Animation<TextureRegion> jumping(TextureAtlas textureAtlas) {
        return new Animation<>(FRAME_DURATION, textureAtlas.findRegions(ANIMATION_JUMPING), Animation.PlayMode.NORMAL);
    }

    @Provides
    @ScreenScoped
    @Named(ANIMATION_CHARGE_JUMP)
    public Animation<TextureRegion> chargeJump(TextureAtlas textureAtlas) {
        return new Animation<>(
                FRAME_DURATION, textureAtlas.findRegions(ANIMATION_CHARGE_JUMP), Animation.PlayMode.NORMAL);
    }
}
