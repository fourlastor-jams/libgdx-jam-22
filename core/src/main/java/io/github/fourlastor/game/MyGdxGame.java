package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import dagger.Lazy;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.gameplay.LevelScreen;

public class MyGdxGame extends Game {

    private final Lazy<LevelScreen> screen;

    public MyGdxGame(Lazy<LevelScreen> screen) {
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
