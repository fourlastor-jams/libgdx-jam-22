package io.github.fourlastor.game.gameover;

import com.badlogic.gdx.Gdx;
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
        float screenHeight = Gdx.graphics.getHeight();
        float screenWidth = screenHeight / 16f * 9f;

        return new FitViewport(screenWidth, screenHeight);
    }

    @Provides
    @ScreenScoped
    public Stage stage(Viewport viewport) {
        return new Stage(viewport);
    }
}
