package io.github.fourlastor.game.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.ActorComponent.Layer;
import io.github.fourlastor.game.level.blueprint.ChunkFactory;

import javax.inject.Inject;

public class LevelScreen extends ScreenAdapter {

    private final Engine engine;
    private final Viewport viewport;
    private final EntitiesFactory entitiesFactory;

    private final ChunkFactory chunkFactory;
    private final World world;
    private Music music;

    private Stage stage;

    @Inject
    public LevelScreen(
            Engine engine, Viewport viewport, EntitiesFactory entitiesFactory, ChunkFactory chunkFactory, World world, AssetManager assetManager) {
        this.engine = engine;
        this.viewport = viewport;
        this.entitiesFactory = entitiesFactory;
        this.chunkFactory = chunkFactory;
        this.world = world;
        this.stage = new Stage(viewport);

        music = setUpMusic(assetManager, "511887__lusmog__postapocalypse-theme-loop.mp3");
        Music ambientMusic = setUpMusic(assetManager, "ambiance_mix.wav");
        ambientMusic.setPosition(MathUtils.random(0, 3 * 60));

        addTutorialImage(assetManager);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        engine.update(delta);

        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        engine.addEntity(entitiesFactory.parallaxBackground(0.125f, Layer.BG_PARALLAX, 0));
        engine.addEntity(entitiesFactory.parallaxBackground(0.25f, Layer.BG_PARALLAX, 1));
        engine.addEntity(entitiesFactory.parallaxBackground(0.5f, Layer.FG_PARALLAX, 2));
        engine.addEntity(entitiesFactory.parallaxBackground(1f, Layer.FG_PARALLAX, 3));

        for (int i = 0; i < 5; i++) {
            for (Entity entity : chunkFactory.generate()) {
                engine.addEntity(entity);
            }
        }
        engine.addEntity(entitiesFactory.player());
    }

    @Override
    public void hide() {
        engine.removeAllEntities();
        engine.removeAllSystems();
        music.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }

    private Music setUpMusic(AssetManager assetManager, String file) {
        Music temp = assetManager.get("audio/music/" + file, Music.class);
        temp.setVolume(.25f);
        temp.setLooping(true);
        temp.play();
        return temp;
    }

    private void addTutorialImage(AssetManager assetManager) {
        Image tutorialImage = new Image(assetManager.get("images/included/hold to jump higher.png", Texture.class));
        tutorialImage.setScale(.032f);
        tutorialImage.setPosition(1.4f, 7.5f);
        tutorialImage.addAction(Actions.sequence(
                Actions.fadeOut(0),
                Actions.delay(3f),
                Actions.fadeIn(1f),
                Actions.delay(3f),
                Actions.fadeOut(1f)
        ));
        stage.addActor(tutorialImage);
    }
}
