package io.github.fourlastor.game.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import javax.inject.Inject;

public class LevelScreen extends ScreenAdapter {

    private final Engine engine;

    @Inject
    public LevelScreen(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void dispose() {}
}
