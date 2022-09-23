package io.github.fourlastor.game.gameover;

import com.badlogic.gdx.ScreenAdapter;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import io.github.fourlastor.game.route.Router;

public class GameOverScreen extends ScreenAdapter {

    private final Router router;

    @AssistedInject
    public GameOverScreen(@Assisted Router router) {

        this.router = router;
    }

    @AssistedFactory
    public interface Factory {
        GameOverScreen create(Router router);
    }
}
