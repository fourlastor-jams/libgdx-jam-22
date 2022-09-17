package io.github.fourlastor.game.di.modules;

import com.badlogic.gdx.assets.AssetManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class GameModule {

    @Provides
    @Singleton
    public AssetManager assetManager() {
        return new AssetManager();
    }
}
