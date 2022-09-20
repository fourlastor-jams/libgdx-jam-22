package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import javax.inject.Inject;

public class ClearScreenSystem extends EntitySystem {

    @Inject
    public ClearScreenSystem() {}

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}
