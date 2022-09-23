package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.intro.IntroScreen;
import io.github.fourlastor.game.level.LevelScreen;
import io.github.fourlastor.game.route.Router;

public class MyGdxGame extends Game implements Router {

    private final InputMultiplexer multiplexer;

    private final LevelScreen.Factory levelScreenFactory;

    private final IntroScreen.Factory introScreenFactory;

    public MyGdxGame(
            InputMultiplexer multiplexer,
            LevelScreen.Factory levelScreenFactory,
            IntroScreen.Factory introScreenFactory) {
        this.multiplexer = multiplexer;
        this.levelScreenFactory = levelScreenFactory;
        this.introScreenFactory = introScreenFactory;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(multiplexer);
        goToIntro();
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }

    @Override
    public void goToIntro() {
        setScreen(introScreenFactory.create(this));
    }

    @Override
    public void goToLevel() {
        setScreen(levelScreenFactory.create(this));
    }

    @Override
    public void goToGameOver() {}
}
