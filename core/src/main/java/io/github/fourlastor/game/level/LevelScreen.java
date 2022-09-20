package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Inject;

@ScreenScoped
public class LevelScreen extends ScreenAdapter {

    private final Engine engine;
    private final Viewport viewport;

    @Inject
    public LevelScreen(Engine engine, Viewport viewport, EntitiesFactory entitiesFactory, AssetManager assetManager) {
        this.engine = engine;
        this.viewport = viewport;
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_0.png", Texture.class), 0.125f));
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_1.png", Texture.class), 0.25f));
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_2.png", Texture.class), 0.5f));
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_3.png", Texture.class), 1f));
        engine.addEntity(entitiesFactory.player());
        for (int i = 0; i < 6; i++) {
            engine.addEntity(entitiesFactory.ground(5.5f, 4f * i));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }
}
