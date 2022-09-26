package io.github.fourlastor.game.level.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.fourlastor.game.level.EntitiesFactory;
import java.util.Random;
import javax.inject.Inject;

public class FishSpawnSystem extends IntervalSystem {

    private static final float FISH_HEIGHT = 1f;
    private static final float INTERVAL = 5f;
    private final Random random;
    private final EntitiesFactory factory;
    private final Viewport viewport;
    private final Camera camera;
    private Engine engine;

    @Inject
    public FishSpawnSystem(Random random, EntitiesFactory factory, Viewport viewport, Camera camera) {
        super(INTERVAL);
        this.random = random;
        this.factory = factory;
        this.viewport = viewport;
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.engine = null;
    }

    @Override
    protected void updateInterval() {
        if (random.nextBoolean()) {
            float y = camera.position.y + viewport.getWorldHeight() / 2 + FISH_HEIGHT;
            float x = MathUtils.random(0f, viewport.getWorldWidth());
            Vector2 position = new Vector2(x, y);
            engine.addEntity(factory.fish(position));
        }
    }
}
