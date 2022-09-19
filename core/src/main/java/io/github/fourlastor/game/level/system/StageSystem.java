package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.fourlastor.game.component.AnimatedImageComponent;
import javax.inject.Inject;

public class StageSystem extends EntitySystem implements EntityListener {

    private static final Family FAMILY =
            Family.all(AnimatedImageComponent.class).get();
    private final Stage stage;
    private final ComponentMapper<AnimatedImageComponent> actors;

    @Inject
    public StageSystem(Stage stage, ComponentMapper<AnimatedImageComponent> actors) {
        this.stage = stage;
        this.actors = actors;
    }

    @Override
    public void update(float deltaTime) {
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
        Actor actor = actors.get(entity).image;
        stage.addActor(actor);
    }

    @Override
    public void entityRemoved(Entity entity) {
        actors.get(entity).image.remove();
    }
}
