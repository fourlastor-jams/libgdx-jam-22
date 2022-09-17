package no.sandramoen.commanderqueen.di.di;

import dagger.Component;
import javax.inject.Singleton;
import no.sandramoen.commanderqueen.MyGdxGame;
import no.sandramoen.commanderqueen.di.di.modules.GameModule;

@Singleton
@Component(modules = GameModule.class)
public interface GameComponent {
    MyGdxGame game();
}
