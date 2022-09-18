package io.github.fourlastor.game.gameplay;

import dagger.Lazy;
import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;

@ScreenScoped
@Subcomponent(modules = LevelModule.class)
public interface LevelComponent {

    @ScreenScoped
    Lazy<LevelScreen> levelScreen();

    @Subcomponent.Builder
    interface Builder {
        Builder levelModule(LevelModule module);

        LevelComponent build();
    }
}
