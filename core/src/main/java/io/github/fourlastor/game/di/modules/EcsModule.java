package io.github.fourlastor.game.di.modules;

import com.badlogic.ashley.core.ComponentMapper;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.ChunkComponent;
import io.github.fourlastor.game.component.ChunkRemovalComponent;
import io.github.fourlastor.game.component.MovingPlatformComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import javax.inject.Singleton;

@Module
public class EcsModule {

    @Provides
    @Singleton
    public ComponentMapper<AnimatedImageComponent> animatedImageComponent() {
        return ComponentMapper.getFor(AnimatedImageComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<ActorComponent> imageComponent() {
        return ComponentMapper.getFor(ActorComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<BodyComponent> bodyComponent() {
        return ComponentMapper.getFor(BodyComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<BodyBuilderComponent> bodyBuilderComponent() {
        return ComponentMapper.getFor(BodyBuilderComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<PlayerComponent> playerComponent() {
        return ComponentMapper.getFor(PlayerComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<MovingPlatformComponent> movingPlatformComponent() {
        return ComponentMapper.getFor(MovingPlatformComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<ChunkComponent> chunkComponent() {
        return ComponentMapper.getFor(ChunkComponent.class);
    }

    @Provides
    @Singleton
    public ComponentMapper<ChunkRemovalComponent> chunkRemovalComponent() {
        return ComponentMapper.getFor(ChunkRemovalComponent.class);
    }
}
