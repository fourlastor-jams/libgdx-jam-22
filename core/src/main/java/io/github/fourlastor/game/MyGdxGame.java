package io.github.fourlastor.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.gameover.GameOverComponent;
import io.github.fourlastor.game.intro.IntroComponent;
import io.github.fourlastor.game.level.di.LevelComponent;
import io.github.fourlastor.game.route.Router;
import io.github.fourlastor.game.route.RouterModule;

public class MyGdxGame extends Game implements Router {

    private final InputMultiplexer multiplexer;

    private final LevelComponent.Builder levelScreenFactory;
    private final IntroComponent.Builder introScreenFactory;
    private final GameOverComponent.Builder gameOverFactory;

    private Screen pendingScreen = null;

    public MyGdxGame(
            InputMultiplexer multiplexer,
            LevelComponent.Builder levelScreenFactory,
            IntroComponent.Builder introScreenFactory,
            GameOverComponent.Builder gameOverFactory) {
        this.multiplexer = multiplexer;
        this.levelScreenFactory = levelScreenFactory;
        this.introScreenFactory = introScreenFactory;
        this.gameOverFactory = gameOverFactory;
    }

    @Override
    public void create() {
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            Cursor customCursor =
                    Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("images/included/cursor.png")), 0, 0);
            Gdx.graphics.setCursor(customCursor);
        }
        Gdx.input.setInputProcessor(multiplexer);
        goToIntro();
    }

    @Override
    public void render() {
        if (pendingScreen != null) {
            setScreen(pendingScreen);
            pendingScreen = null;
        }
        super.render();
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }

    @Override
    public void goToIntro() {
        pendingScreen =
                introScreenFactory.router(new RouterModule(this)).build().screen();
    }

    @Override
    public void goToLevel() {
        pendingScreen =
                levelScreenFactory.router(new RouterModule(this)).build().screen();
    }

    @Override
    public void goToGameOver() {
        pendingScreen = gameOverFactory.router(new RouterModule(this)).build().screen();
    }
}
