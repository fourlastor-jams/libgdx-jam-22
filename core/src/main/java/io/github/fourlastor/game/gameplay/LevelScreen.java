package io.github.fourlastor.game.gameplay;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Inject;

@ScreenScoped
public class LevelScreen extends ScreenAdapter {

    private final Engine engine;
    private final Viewport viewport;

    @Inject
    public LevelScreen(Engine engine, TextureAtlas atlas, Viewport viewport) {
        this.engine = engine;
        this.viewport = viewport;
        Entity playerEntity = new Entity();
        playerEntity.add(new ActorComponent(new Image(atlas.findRegion("whitePixel"))));
        playerEntity.add(new PlayerComponent());
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
