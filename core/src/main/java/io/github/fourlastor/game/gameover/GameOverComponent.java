package io.github.fourlastor.game.gameover;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;

@ScreenScoped
@Subcomponent(modules = GameOverModule.class)
public interface GameOverComponent {

    @ScreenScoped
    GameOverScreen.Factory screen();

    @Subcomponent.Builder
    interface Builder {
        GameOverComponent build();
    }
}
