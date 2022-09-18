package io.github.fourlastor.game.gameplay;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Provider;

@ScreenScoped
@Subcomponent(modules = LevelModule.class)
public interface LevelComponent {

    @ScreenScoped
    Provider<LevelScreen> levelScreen();

    @Subcomponent.Builder
    interface Builder {
        Builder levelModule(LevelModule module);

        LevelComponent build();
    }
}
