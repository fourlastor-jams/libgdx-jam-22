package io.github.fourlastor.game.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.di.LevelEngine;
import io.github.fourlastor.game.system.StageSystem;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class LevelScreen extends ScreenAdapter {

    private final Engine engine;

    @Inject
    public LevelScreen(ComponentMappers componentMappers, @LevelEngine Engine engine, AssetManager assetManager) {
        this.engine = engine;
        engine.addSystem(new StageSystem(new Stage(new FitViewport(9f, 16f)), componentMappers));
        Entity entity = new Entity();
        TextureAtlas atlas = assetManager.get("images/included/packed/images.pack.atlas", TextureAtlas.class);
        entity.add(new ActorComponent(new Image(atlas.findRegion("whitePixel"))));
        engine.addEntity(entity);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void dispose() {}
}
