package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.fourlastor.game.component.ActorComponent.Layer;
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
                assetManager.get("images/included/background/background_layer_0.png", Texture.class),
                0.125f,
                Layer.BG_PARALLAX));
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_1.png", Texture.class),
                0.25f,
                Layer.BG_PARALLAX));
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_2.png", Texture.class),
                0.5f,
                Layer.FG_PARALLAX));
        engine.addEntity(entitiesFactory.parallaxBackground(
                assetManager.get("images/included/background/background_layer_3.png", Texture.class),
                1f,
                Layer.FG_PARALLAX));
        for (int i = 0; i < 6; i++) {
            PlatformWidth width;
            PlatformType type = PlatformType.LARGE_GRID;
            if (i > 4) {
                width = PlatformWidth.NINE;
            } else if (i > 2) {
                width = PlatformWidth.FOUR;
            } else {
                type = PlatformType.SMALL_GRID;
                width = PlatformWidth.ONE;
            }
            engine.addEntity(entitiesFactory.ground(4.5f, 4f * i, type, width));
        }
        engine.addEntity(entitiesFactory.player());
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
