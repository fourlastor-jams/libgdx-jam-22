package no.sandramoen.commanderqueen;

import com.badlogic.gdx.Game;
import javax.inject.Inject;
import no.sandramoen.commanderqueen.di.di.DaggerGameComponent;
import no.sandramoen.commanderqueen.screens.gameplay.gameplay.LevelScreen;

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
        return DaggerGameComponent.create().game();
    }
}
