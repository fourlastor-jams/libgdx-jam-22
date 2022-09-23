package io.github.fourlastor.game.intro;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;

@ScreenScoped
@Subcomponent
public interface IntroComponent {

    @ScreenScoped
    IntroScreen.Factory screen();

    @Subcomponent.Builder
    interface Builder {
        IntroComponent build();
    }
}
