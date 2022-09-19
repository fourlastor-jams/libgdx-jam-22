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

    @Provides
    @ScreenScoped
    @Named(ANIMATION_STANDING)
    public Animation<TextureRegion> provideStanding(TextureAtlas textureAtlas) {
        return new Animation<>(0.2f, textureAtlas.findRegions(ANIMATION_STANDING), Animation.PlayMode.LOOP);
    }

    @Provides
    @ScreenScoped
    @Named(ANIMATION_FALLING)
    public Animation<TextureRegion> provideFalling(TextureAtlas textureAtlas) {
        return new Animation<>(0.1f, textureAtlas.findRegions(ANIMATION_FALLING), Animation.PlayMode.NORMAL);
    }
}
