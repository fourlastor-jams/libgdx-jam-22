package io.github.fourlastor.game.di.modules;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.MyGdxGame;
import io.github.fourlastor.game.level.LevelComponent;
import javax.inject.Singleton;

@Module(subcomponents = LevelComponent.class)
public class GameModule {

    private static final String PATH_TEXTURE_ATLAS = "images/included/packed/images.pack.atlas";

    @Provides
    @Singleton
    public AssetManager assetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.load(PATH_TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.load("images/included/background/background_layer 0.png", Texture.class);
        assetManager.load("images/included/background/background_layer 1.png", Texture.class);
        assetManager.load("images/included/background/background_layer 2.png", Texture.class);
        assetManager.load("images/included/background/background_layer 3.png", Texture.class);
        assetManager.finishLoading();
        return assetManager;
    }

    @Provides
    @Singleton
    public InputMultiplexer inputMultiplexer() {
        return new InputMultiplexer();
    }

    @Provides
    @Singleton
    public TextureAtlas textureAtlas(AssetManager assetManager) {
        return assetManager.get(PATH_TEXTURE_ATLAS, TextureAtlas.class);
    }

    @Provides
    @Singleton
    public MessageManager messageManager() {
        return MessageManager.getInstance();
    }

    @Provides
    @Singleton
    public MyGdxGame game(InputMultiplexer multiplexer, LevelComponent.Builder builder) {
        return new MyGdxGame(multiplexer, builder.build().levelScreen());
    }
}
