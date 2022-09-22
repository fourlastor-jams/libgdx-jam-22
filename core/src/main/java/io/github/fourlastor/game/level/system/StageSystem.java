package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class StageSystem extends EntitySystem implements EntityListener {

    private static final Family FAMILY =
            Family.one(AnimatedImageComponent.class, ActorComponent.class).get();
    private final Stage stage;
    private final ComponentMapper<ActorComponent> actors;
    private final List<Group> layers;

    @Inject
    public StageSystem(Stage stage, ComponentMapper<ActorComponent> actors) {
        this.stage = stage;
        this.actors = actors;
        int layersCount = ActorComponent.Layer.values().length;
        layers = new ArrayList<>(layersCount);
        for (int i = 0; i < layersCount; i++) {
            layers.add(new Group());
        }
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(FAMILY, this);
        for (Group layer : layers) {
            stage.addActor(layer);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(this);
        for (Group layer : layers) {
            layer.remove();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        if (actors.has(entity)) {
            ActorComponent actorComponent = actors.get(entity);
            Actor actor = actorComponent.actor;
            ActorComponent.Layer layer = actorComponent.layer;
            layers.get(layer.ordinal()).addActor(actor);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        if (actors.has(entity)) {
            actors.get(entity).actor.remove();
        }
    }
}
