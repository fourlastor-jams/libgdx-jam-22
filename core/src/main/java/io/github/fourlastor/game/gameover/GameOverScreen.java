package io.github.fourlastor.game.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.tommyettinger.textra.TypingLabel;
import io.github.fourlastor.game.route.Router;
import javax.inject.Inject;

public class GameOverScreen extends ScreenAdapter {

    private final Router router;
    private final InputMultiplexer multiplexer;
    private final Stage stage;
    private final Viewport viewport;
    private final Image gameOverImage;
    private final TypingLabel infoText;
    private Sound clickSound;

    @Inject
    public GameOverScreen(
            Router router,
            InputMultiplexer multiplexer,
            Stage stage,
            TextureAtlas atlas,
            Viewport viewport,
            AssetManager assetManager) {
        this.router = router;
        this.multiplexer = multiplexer;
        this.stage = stage;
        this.viewport = viewport;
        gameOverImage = new Image(atlas.findRegion("gameOver!"));
        gameOverImage.setScale(screenWidth() / gameOverImage.getWidth());
        gameOverImage.setY(-gameOverImage.getHeight() / gameOverImage.getScaleY());
        Label.LabelStyle label32Style = new Label.LabelStyle();
        label32Style.font = new BitmapFont(Gdx.files.internal("fonts/font-32.fnt"));
        label32Style.fontColor = Color.WHITE;
        label32Style.font.setColor(Color.WHITE);
        infoText = new TypingLabel("(press R to restart)", label32Style);
        infoText.setPosition(0f, 4f);
        infoText.setWidth(screenWidth());
        infoText.setWrap(true);
        infoText.setAlignment(Align.center);
        infoText.setColor(new Color(1f, 1f, 0.949f, 0f));
        Table table = new Table();
        table.setFillParent(true);
        table.add(infoText).growX().expandY().bottom().padBottom(0.2f);
        stage.addActor(table);
        stage.addActor(gameOverImage);

        clickSound = assetManager.get("audio/sounds/Blip_Select11.wav", Sound.class);
        Sound chargeJumpSound = assetManager.get("audio/sounds/chargeJump.wav", Sound.class);
        chargeJumpSound.stop();
        Music levelMusic = assetManager.get("audio/music/511887__lusmog__postapocalypse-theme-loop.mp3", Music.class);
        levelMusic.stop();
        Sound sound =
                assetManager.get("audio/sounds/553418__eminyildirim__cinematic-boom-impact-hit-2021.wav", Sound.class);
        sound.play();
    }

    @Override
    public void show() {
        MoveToAction move = new MoveToAction();
        move.setActor(gameOverImage);
        move.setDuration(0.6f);
        move.setY(screenHeight() / 3f);
        AlphaAction showText = Actions.fadeIn(0.5f);
        showText.setActor(infoText);
        Action actions = Actions.sequence(move, Actions.run(() -> multiplexer.addProcessor(processor)), showText);

        stage.addAction(actions);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        multiplexer.removeProcessor(processor);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    private int screenWidth() {
        return viewport.getScreenWidth();
    }

    private int screenHeight() {
        return viewport.getScreenHeight();
    }

    private final InputProcessor processor = new InputAdapter() {
        @Override
        public boolean keyUp(int keycode) {
            if (keycode == Input.Keys.R) {
                clickSound.play(.25f);
                router.goToLevel();
                return true;
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            router.goToLevel();
            return true;
        }
    };
}
