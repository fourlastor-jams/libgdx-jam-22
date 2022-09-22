package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.intro.IntroScreen;
import io.github.fourlastor.game.level.LevelScreen;
import javax.inject.Provider;

public class MyGdxGame extends Game {

    private final InputMultiplexer multiplexer;
    private final Provider<LevelScreen> levelScreenProvider;

    @SuppressWarnings({"FieldCanBeLocal", "unused"}) // TODO: add screen switching
    private final Provider<IntroScreen> introScreenProvider;

    public MyGdxGame(
            InputMultiplexer multiplexer,
            Provider<LevelScreen> levelScreenProvider,
            Provider<IntroScreen> introScreenProvider) {
        this.multiplexer = multiplexer;
        this.levelScreenProvider = levelScreenProvider;
        this.introScreenProvider = introScreenProvider;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(multiplexer);
        setScreen(levelScreenProvider.get());
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }
}
