package io.github.fourlastor.game.level;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;

@ScreenScoped
@Subcomponent(modules = {LevelModule.class, PlayerAnimationsFactory.class})
public interface LevelComponent {

    @ScreenScoped
    LevelScreen.Factory screen();

    @Subcomponent.Builder
    interface Builder {
        LevelComponent build();
    }
}
