package io.github.fourlastor.game.intro;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;

@ScreenScoped
@Subcomponent(modules = {IntroModule.class})
public interface IntroComponent {

    @ScreenScoped
    IntroScreen.Factory introScreen();

    @Subcomponent.Builder
    interface Builder {
        IntroComponent build();
    }
}
