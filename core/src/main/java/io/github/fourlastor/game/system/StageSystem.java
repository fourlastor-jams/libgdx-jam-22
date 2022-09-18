package io.github.fourlastor.game.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.utils.ComponentMappers;

public class StageSystem extends EntitySystem implements EntityListener {

    private static final Family FAMILY = Family.all(ActorComponent.class).get();
    private final Stage stage;
    private final ComponentMapper<ActorComponent> actors;

    public StageSystem(Stage stage, ComponentMappers componentMappers) {
        this.stage = stage;
        actors = componentMappers.get(ActorComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        Actor actor = actors.get(entity).actor;
        stage.addActor(actor);
    }

    @Override
    public void entityRemoved(Entity entity) {
        actors.get(entity).actor.remove();
    }
}
