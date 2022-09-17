package no.sandramoen.commanderqueen.di.di.modules;

import com.badlogic.ashley.core.Engine;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class GameModule {

    @Provides
    @Singleton
    public Engine provideEngine() {
        return new Engine();
    }
}
