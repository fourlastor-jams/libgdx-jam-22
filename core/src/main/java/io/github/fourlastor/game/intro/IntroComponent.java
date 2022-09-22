package io.github.fourlastor.game.intro;

import dagger.Subcomponent;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Provider;

@ScreenScoped
@Subcomponent(modules = {IntroModule.class})
public interface IntroComponent {

    @ScreenScoped
    Provider<IntroScreen> introScreen();

    @Subcomponent.Builder
    interface Builder {
        IntroComponent build();
    }
}
