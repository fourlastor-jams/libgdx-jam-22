package io.github.fourlastor.game.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.di.LevelEngine;
import io.github.fourlastor.game.system.PhysicsDebugSystem;
import io.github.fourlastor.game.system.PhysicsSystem;
import io.github.fourlastor.game.system.StageSystem;
import io.github.fourlastor.game.utils.ComponentMappers;
import javax.inject.Inject;

public class LevelScreen extends ScreenAdapter {

    private final Engine engine;
    private final FitViewport viewport;

    @Inject
    public LevelScreen(ComponentMappers componentMappers, @LevelEngine Engine engine, AssetManager assetManager) {
        this.engine = engine;
        viewport = new FitViewport(9f, 16f);
        World world = new World(new Vector2(0f, -1f), true);
        engine.addSystem(new PhysicsSystem(world, componentMappers));
        engine.addSystem(new StageSystem(new Stage(viewport), componentMappers));
        engine.addSystem(new PhysicsDebugSystem(viewport.getCamera(), world));
        Entity playerEntity = new Entity();
        TextureAtlas atlas = assetManager.get("images/included/packed/images.pack.atlas", TextureAtlas.class);
        playerEntity.add(new ActorComponent(new Image(atlas.findRegion("whitePixel"))));
        engine.addEntity(playerEntity);
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
