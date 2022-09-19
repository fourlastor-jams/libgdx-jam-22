package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.gameplay.LevelScreen;
import javax.inject.Provider;

public class MyGdxGame extends Game {

    private final InputMultiplexer multiplexer;
    private final Provider<LevelScreen> screen;

    public MyGdxGame(InputMultiplexer multiplexer, Provider<LevelScreen> screen) {
        this.multiplexer = multiplexer;
        this.screen = screen;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(multiplexer);
        setScreen(screen.get());
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }
}
