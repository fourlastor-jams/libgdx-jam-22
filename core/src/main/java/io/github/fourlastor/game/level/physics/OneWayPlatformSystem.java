package io.github.fourlastor.game.level.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.MovingPlatformComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import javax.inject.Inject;

public class OneWayPlatformSystem extends EntitySystem {

    private static final Family FAMILY_PLATFORM =
            Family.all(MovingPlatformComponent.class, BodyComponent.class).get();
    private static final Family FAMILY_PLAYER =
            Family.all(PlayerComponent.class, BodyComponent.class).get();

    private final World world;
    private final ComponentMapper<BodyComponent> bodies;
    private ImmutableArray<Entity> platforms;
    private ImmutableArray<Entity> players;

    @Inject
    public OneWayPlatformSystem(World world, ComponentMapper<BodyComponent> bodies) {
        this.world = world;
        this.bodies = bodies;
    }

    @Override
    public void addedToEngine(Engine engine) {
        platforms = engine.getEntitiesFor(FAMILY_PLATFORM);
        players = engine.getEntitiesFor(FAMILY_PLAYER);
        world.setContactFilter(contactFilter);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        world.setContactFilter(null);
        platforms = null;
        players = null;
    }

    private final ContactFilter contactFilter = new ContactFilter() {
        @Override
        public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
            for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
                Body playerBody = bodies.get(players.get(playerIndex)).body;
                Fixture playerFixture = playerBody.getFixtureList().first();
                for (int platformIndex = 0; platformIndex < platforms.size(); platformIndex++) {
                    Body platformBody = bodies.get(platforms.get(platformIndex)).body;
                    Fixture platformFixture = platformBody.getFixtureList().first();
                    if ((playerFixture == fixtureA && platformFixture == fixtureB)
                            || (playerFixture == fixtureB && platformFixture == fixtureA)) {
                        Vector2 playerPosition = playerBody.getPosition();
                        Vector2 platformPosition = platformBody.getPosition();
                        if (playerPosition.y - playerFixture.getShape().getRadius() < platformPosition.y) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    };
}
