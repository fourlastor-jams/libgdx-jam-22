package io.github.fourlastor.game.gameover;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.route.RouterModule;

@ScreenScoped
@Subcomponent(modules = {GameOverModule.class, RouterModule.class})
public interface GameOverComponent {

    @ScreenScoped
    GameOverScreen screen();

    @Subcomponent.Builder
    interface Builder {

        Builder router(RouterModule routerModule);

        GameOverComponent build();
    }
}
