package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Inject;

@ScreenScoped
public class LevelScreen extends ScreenAdapter {

    private final Engine engine;
    private final Viewport viewport;

    @Inject
    public LevelScreen(Engine engine, Viewport viewport, EntitiesFactory entitiesFactory) {
        this.engine = engine;
        this.viewport = viewport;
        engine.addEntity(entitiesFactory.player());
        engine.addEntity(entitiesFactory.ground());
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
