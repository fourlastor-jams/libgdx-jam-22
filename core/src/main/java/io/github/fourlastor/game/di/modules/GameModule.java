package io.github.fourlastor.game.di.modules;

import com.badlogic.gdx.assets.AssetManager;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.MyGdxGame;
import io.github.fourlastor.game.gameplay.LevelComponent;
import javax.inject.Singleton;

@Module(subcomponents = LevelComponent.class)
public class GameModule {

    @Provides
    @Singleton
    public AssetManager assetManager() {
        return new AssetManager();
    }

    @Provides
    @Singleton
    public MyGdxGame game(AssetManager assetManager, LevelComponent.Builder builder) {
        return new MyGdxGame(assetManager, builder.build().levelScreen());
    }
}
