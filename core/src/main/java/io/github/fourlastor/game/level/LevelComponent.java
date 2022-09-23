package io.github.fourlastor.game.level;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.route.RouterModule;

@ScreenScoped
@Subcomponent(modules = {LevelModule.class, PlayerAnimationsFactory.class, RouterModule.class})
public interface LevelComponent {

    @ScreenScoped
    LevelScreen screen();

    @Subcomponent.Builder
    interface Builder {

        Builder router(RouterModule routerModule);

        LevelComponent build();
    }
}
