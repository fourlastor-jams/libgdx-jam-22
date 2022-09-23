package io.github.fourlastor.game.gameover;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
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
