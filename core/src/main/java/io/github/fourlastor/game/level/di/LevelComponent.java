package io.github.fourlastor.game.level.di;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.level.LevelScreen;
import io.github.fourlastor.game.level.PlayerAnimationsFactory;
import io.github.fourlastor.game.route.RouterModule;

@ScreenScoped
@Subcomponent(modules = {LevelModule.class, MapModule.class, PlayerAnimationsFactory.class, RouterModule.class})
public interface LevelComponent {

    @ScreenScoped
    LevelScreen screen();

    @Subcomponent.Builder
    interface Builder {

        Builder router(RouterModule routerModule);

        LevelComponent build();
    }
}
