package io.github.fourlastor.game.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import io.github.fourlastor.game.route.Router;

public class GameOverScreen extends ScreenAdapter {

    private final Router router;
    private final InputMultiplexer multiplexer;

    @AssistedInject
    public GameOverScreen(@Assisted Router router, InputMultiplexer multiplexer) {
        this.router = router;
        this.multiplexer = multiplexer;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void show() {
        multiplexer.addProcessor(processor);
    }

    @Override
    public void hide() {
        multiplexer.removeProcessor(processor);
    }

    private final InputProcessor processor = new InputAdapter() {
        @Override
        public boolean keyUp(int keycode) {
            if (keycode == Input.Keys.R) {
                router.goToLevel();
                return true;
            }
            return false;
        }
    };

    @AssistedFactory
    public interface Factory {
        GameOverScreen create(Router router);
    }
}
