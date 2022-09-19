package io.github.fourlastor.game.level;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Provider;

@ScreenScoped
@Subcomponent(modules = {LevelModule.class, PlayerAnimationsFactory.class})
public interface LevelComponent {

    @ScreenScoped
    Provider<LevelScreen> levelScreen();

    @Subcomponent.Builder
    interface Builder {
        LevelComponent build();
    }
}
