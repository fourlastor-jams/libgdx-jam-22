package io.github.fourlastor.game.di.modules;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.MyGdxGame;
import io.github.fourlastor.game.gameplay.LevelComponent;
import javax.inject.Singleton;

@Module(subcomponents = LevelComponent.class)
public class GameModule {

    private static final String PATH_TEXTURE_ATLAS = "images/included/packed/images.pack.atlas";

    @Provides
    @Singleton
    public AssetManager assetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.load(PATH_TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        return assetManager;
    }

    @Provides
    @Singleton
    public TextureAtlas textureAtlas(AssetManager assetManager) {
        return assetManager.get(PATH_TEXTURE_ATLAS, TextureAtlas.class);
    }

    @Provides
    @Singleton
    public MyGdxGame game(LevelComponent.Builder builder) {
        return new MyGdxGame(builder.build().levelScreen());
    }
}
