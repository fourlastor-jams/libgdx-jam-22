package io.github.fourlastor.game.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.fourlastor.game.di.ScreenScoped;
import javax.inject.Inject;

@ScreenScoped
public class IntroScreen extends ScreenAdapter {

    private final InputMultiplexer inputMultiplexer;
    private final TextureAtlas atlas;
    private final AssetManager assetManager;
    private Stage stage;

    @Inject
    public IntroScreen(InputMultiplexer inputMultiplexer, TextureAtlas atlas, AssetManager assetManager) {
        this.inputMultiplexer = inputMultiplexer;
        this.atlas = atlas;
        this.assetManager = assetManager;
        Viewport viewport = new ScreenViewport();
        stage = new Stage(viewport);

        Image dragonQueen = new Image(assetManager.get("images/included/intro/dragon_queen.png", Texture.class));
        /*dragonQueen.addAction(Actions.fadeOut(0));
        dragonQueen.addAction(Actions.fadeOut(1));*/
        stage.addActor(dragonQueen);

        assetManager.get("images/included/intro/earth_ground.png", Texture.class);
        assetManager.get("images/included/intro/earth_space.png", Texture.class);
        assetManager.get("images/included/intro/missiles_and_explosion_1.png", Texture.class);
        assetManager.get("images/included/intro/missiles_and_explosion_2.png", Texture.class);
        assetManager.get("images/included/intro/missiles_and_explosion_3.png", Texture.class);
        assetManager.get("images/included/intro/silo_and_skeleton.png", Texture.class);
        assetManager.get("images/included/intro/sky_and_mountains.png", Texture.class);
        assetManager.get("images/included/intro/sky_dragon.png", Texture.class);
        assetManager.get("images/included/intro/space.png", Texture.class);
        assetManager.get("images/included/intro/zebra_king.png", Texture.class);

        //        Music music = assetManager.get("audio/music/398937__mypantsfelldown__metal-footsteps.wav",
        // Music.class);

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
}
