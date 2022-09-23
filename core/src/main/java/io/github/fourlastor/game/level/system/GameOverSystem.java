package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.Message;
import javax.inject.Inject;

public class GameOverSystem extends IteratingSystem {

    private static final Family FAMILY =
            Family.all(PlayerComponent.class, BodyComponent.class).get();

    private final Camera camera;
    private final ComponentMapper<BodyComponent> bodies;
    private final MessageDispatcher messageDispatcher;

    @Inject
    public GameOverSystem(Camera camera, ComponentMapper<BodyComponent> bodies, MessageDispatcher messageDispatcher) {
        super(FAMILY);
        this.camera = camera;
        this.bodies = bodies;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Body body = bodies.get(entity).body;
        float lowerBound = camera.position.y - camera.viewportHeight / 2;
        if (body.getPosition().y < lowerBound - 2f) {
            messageDispatcher.dispatchMessage(Message.GAME_OVER.ordinal());
        }
    }
}
