package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.fourlastor.game.component.BodyComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.level.Message;
import io.github.fourlastor.game.route.Router;
import javax.inject.Inject;

public class GameOverSystem extends IteratingSystem implements Telegraph {

    private static final Family FAMILY =
            Family.all(PlayerComponent.class, BodyComponent.class).get();

    private final Camera camera;
    private final ComponentMapper<BodyComponent> bodies;
    private final Router router;
    private final MessageDispatcher dispatcher;

    @Inject
    public GameOverSystem(
            Camera camera, ComponentMapper<BodyComponent> bodies, Router router, MessageDispatcher dispatcher) {
        super(FAMILY);
        this.camera = camera;
        this.bodies = bodies;
        this.router = router;
        this.dispatcher = dispatcher;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        dispatcher.addListener(this, Message.GAME_OVER.ordinal());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Body body = bodies.get(entity).body;
        float lowerBound = camera.position.y - camera.viewportHeight / 2;
        if (body.getPosition().y < lowerBound - 2f) {
            router.goToGameOver();
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.message == Message.GAME_OVER.ordinal()) {
            router.goToGameOver();
            return true;
        }
        return false;
    }
}
