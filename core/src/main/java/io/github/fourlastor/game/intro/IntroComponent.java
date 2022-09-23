package io.github.fourlastor.game.intro;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.route.RouterModule;

@ScreenScoped
@Subcomponent(modules = RouterModule.class)
public interface IntroComponent {

    @ScreenScoped
    IntroScreen screen();

    @Subcomponent.Builder
    interface Builder {

        Builder router(RouterModule routerModule);

        IntroComponent build();
    }
}
