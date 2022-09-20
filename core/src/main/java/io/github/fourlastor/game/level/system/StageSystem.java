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
import io.github.fourlastor.game.component.ImageComponent;
import javax.inject.Inject;

public class StageSystem extends EntitySystem implements EntityListener {

    private static final Family FAMILY =
            Family.one(AnimatedImageComponent.class, ImageComponent.class).get();
    private final Stage stage;
    private final ComponentMapper<AnimatedImageComponent> animatedImages;
    private final ComponentMapper<ImageComponent> images;

    @Inject
    public StageSystem(
            Stage stage,
            ComponentMapper<AnimatedImageComponent> animatedImages,
            ComponentMapper<ImageComponent> images) {
        this.stage = stage;
        this.animatedImages = animatedImages;
        this.images = images;
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
        if (animatedImages.has(entity)) {
            Actor actor = animatedImages.get(entity).image;
            stage.addActor(actor);
        }
        if (images.has(entity)) {
            Actor actor = images.get(entity).image;
            stage.addActor(actor);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        if (animatedImages.has(entity)) {
            animatedImages.get(entity).image.remove();
        }
        if (images.has(entity)) {
            images.get(entity).image.remove();
        }
    }
}
