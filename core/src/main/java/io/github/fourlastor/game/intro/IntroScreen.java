package io.github.fourlastor.game.intro;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Inject;

@ScreenScoped
public class IntroScreen extends ScreenAdapter {

    private final InputMultiplexer inputMultiplexer;
    private final TextureAtlas atlas;
    private final AssetManager assetManager;

    @Inject
    public IntroScreen(InputMultiplexer inputMultiplexer, TextureAtlas atlas, AssetManager assetManager) {
        this.inputMultiplexer = inputMultiplexer;
        this.atlas = atlas;
        this.assetManager = assetManager;
        //        Music music = assetManager.get("audio/music/398937__mypantsfelldown__metal-footsteps.wav",
        // Music.class);

    }
}
