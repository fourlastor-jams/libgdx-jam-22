package io.github.fourlastor.game.gameover;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;

@Module
public class GameOverModule {

    @Provides
    @ScreenScoped
    public Viewport viewport() {
        return new FitViewport(9f, 16f);
    }

    @Provides
    @ScreenScoped
    public Stage stage(Viewport viewport) {
        return new Stage(viewport);
    }
}
