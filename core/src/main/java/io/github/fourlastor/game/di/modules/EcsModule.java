package io.github.fourlastor.game.di.modules;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.di.EntityComponents;
import io.github.fourlastor.game.di.LevelEngine;
import javax.inject.Singleton;

@SuppressWarnings("rawtypes")
@Module
public class EcsModule {

    @Provides
    @Singleton
    @LevelEngine
    public Engine provideEngine() {
        return new Engine();
    }

    @Provides
    @IntoMap
    @EntityComponents
    @ClassKey(ActorComponent.class)
    public ComponentMapper actorComponent() {
        return ComponentMapper.getFor(ActorComponent.class);
    }

    @Provides
    @IntoMap
    @EntityComponents
    @ClassKey(BodyComponent.class)
    public ComponentMapper bodyComponent() {
        return ComponentMapper.getFor(BodyComponent.class);
    }

    @Provides
    @IntoMap
    @EntityComponents
    @ClassKey(PlayerComponent.class)
    public ComponentMapper playerComponent() {
        return ComponentMapper.getFor(PlayerComponent.class);
    }
}
