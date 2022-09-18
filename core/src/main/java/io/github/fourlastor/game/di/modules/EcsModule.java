package io.github.fourlastor.game.di.modules;

import com.badlogic.ashley.core.ComponentMapper;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.di.EntityComponents;

@SuppressWarnings("rawtypes")
@Module
public class EcsModule {

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
    @ClassKey(BodyBuilderComponent.class)
    public ComponentMapper bodyBuilderComponent() {
        return ComponentMapper.getFor(BodyBuilderComponent.class);
    }

    @Provides
    @IntoMap
    @EntityComponents
    @ClassKey(PlayerComponent.class)
    public ComponentMapper playerComponent() {
        return ComponentMapper.getFor(PlayerComponent.class);
    }
}
