package io.github.fourlastor.game.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.di.ScreenScoped;
import io.github.fourlastor.game.system.PhysicsDebugSystem;
import io.github.fourlastor.game.system.PhysicsSystem;
import io.github.fourlastor.game.system.PlayerInputSystem;
import io.github.fourlastor.game.system.StageSystem;

@Module
public class LevelModule {

    @Provides
    @ScreenScoped
    public Engine engine(
            PhysicsSystem physicsSystem,
            StageSystem stageSystem,
            PhysicsDebugSystem physicsDebugSystem,
            PlayerInputSystem playerInputSystem) {
        Engine engine = new Engine();
        engine.addSystem(playerInputSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(stageSystem);
        engine.addSystem(physicsDebugSystem);
        return engine;
    }

    @Provides
    @ScreenScoped
    public Viewport viewport() {
        return new FitViewport(9f, 16f);
    }

    @Provides
    @ScreenScoped
    public Stage stage(Viewport viewport) {
        return new Stage(viewport);
    }

    @Provides
    @ScreenScoped
    public Camera camera(Viewport viewport) {
        return viewport.getCamera();
    }

    @Provides
    @ScreenScoped
    public World world() {
        return new World(new Vector2(0f, -1f), true);
    }
}
