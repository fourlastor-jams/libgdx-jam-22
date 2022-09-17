package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.gameplay.LevelScreen;
import javax.inject.Inject;

public class MyGdxGame extends Game {

    private final LevelScreen screen;

    @Inject
    public MyGdxGame(LevelScreen screen) {
        this.screen = screen;
    }

    @Override
    public void create() {
        setScreen(screen);
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }
}
