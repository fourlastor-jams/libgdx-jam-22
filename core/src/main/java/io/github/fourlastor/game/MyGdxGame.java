package io.github.fourlastor.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dagger.Lazy;
import io.github.fourlastor.game.di.GameComponent;
import io.github.fourlastor.game.gameplay.LevelScreen;
import javax.inject.Inject;

public class MyGdxGame extends Game {

    private final AssetManager assetManager;
    private final Lazy<LevelScreen> screen;

    @Inject
    public MyGdxGame(AssetManager assetManager, Lazy<LevelScreen> screen) {
        this.assetManager = assetManager;
        this.screen = screen;
    }

    @Override
    public void create() {
        assetManager.load("images/included/packed/images.pack.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        setScreen(screen.get());
    }

    public static MyGdxGame createGame() {
        return GameComponent.component().game();
    }
}
