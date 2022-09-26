package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.component.SoundComponent;
import javax.inject.Inject;

public class SoundSystem extends IteratingSystem {
    private static final Family FAMILY =
            Family.all(SoundComponent.class, BodyComponent.class).get();
    private static final Family FAMILY_PLAYERS =
            Family.all(PlayerComponent.class, BodyComponent.class).get();

    private final ComponentMapper<BodyComponent> bodies;
    private final ComponentMapper<SoundComponent> sounds;
    private ImmutableArray<Entity> players;

    @Inject
    public SoundSystem(ComponentMapper<BodyComponent> bodies, ComponentMapper<SoundComponent> sounds) {
        super(FAMILY);
        this.bodies = bodies;
        this.sounds = sounds;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {}

            @Override
            public void entityRemoved(Entity entity) {
                sounds.get(entity).stop();
            }
        });
        players = engine.getEntitiesFor(FAMILY_PLAYERS);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        players = null;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        for (int i = 0; i < players.size(); i++) {
            Vector2 playerPosition = bodies.get(players.get(i)).body.getPosition();
            Vector2 soundPosition = bodies.get(entity).body.getPosition();
            SoundComponent sound = sounds.get(entity);
            float dst = playerPosition.dst(soundPosition);
            float volume = .9f / (dst * dst);
            sound.play(volume);
        }
    }
}
