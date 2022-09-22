package io.github.fourlastor.game.intro;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;

@Module
public class IntroModule {

    @Provides
    @ScreenScoped
    public Stage stage(Viewport viewport) {
        return new Stage(viewport);
    }
}
