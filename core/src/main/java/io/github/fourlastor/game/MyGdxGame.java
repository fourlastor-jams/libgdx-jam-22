package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.gameplay.LevelScreen;
import javax.inject.Provider;

public class MyGdxGame extends Game {

    private final Provider<LevelScreen> screen;

    public MyGdxGame(Provider<LevelScreen> screen) {
        this.screen = screen;
    }

    @Override
    public void create() {
        setScreen(screen.get());
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }
}
