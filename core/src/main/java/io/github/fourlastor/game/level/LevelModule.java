package io.github.fourlastor.game.level;

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
import io.github.fourlastor.game.level.input.PlayerInputSystem;
import io.github.fourlastor.game.level.physics.PhysicsDebugSystem;
import io.github.fourlastor.game.level.physics.PhysicsSystem;
import io.github.fourlastor.game.level.system.ActorFollowBodySystem;
import io.github.fourlastor.game.level.system.MovePlatformsDownSystem;
import io.github.fourlastor.game.level.system.StageSystem;

@Module
public class LevelModule {

    @Provides
    @ScreenScoped
    public Engine engine(
            PlayerInputSystem playerInputSystem,
            MovePlatformsDownSystem movePlatformsDownSystem,
            PhysicsSystem physicsSystem,
            ActorFollowBodySystem actorFollowBodySystem,
            StageSystem stageSystem,
            PhysicsDebugSystem physicsDebugSystem) {
        Engine engine = new Engine();
        engine.addSystem(playerInputSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(movePlatformsDownSystem);
        engine.addSystem(actorFollowBodySystem);
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
        return new World(new Vector2(0f, -10f), true);
    }
}
