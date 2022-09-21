package io.github.fourlastor.game.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.tommyettinger.textra.TypingLabel;

import io.github.fourlastor.game.di.ScreenScoped;

import javax.inject.Inject;

@ScreenScoped
public class IntroScreen extends ScreenAdapter {

    private final InputMultiplexer inputMultiplexer;
    private final TextureAtlas atlas;
    private final AssetManager assetManager;
    private Stage stage;

    private Image dragon_queen;
    private Image earth_ground;
    private Image earth_space;
    private Image missiles_and_explosion_1;
    private Image missiles_and_explosion_2;
    private Image missiles_and_explosion_3;
    private Image silo_and_skeleton;
    private Image sky_and_mountains;
    private Image sky_dragon;
    private Image space;
    private Image zebra_king;
    private Image lyze;
    private Image black_screen;

    private TypingLabel subtitles;

    @Inject
    public IntroScreen(InputMultiplexer inputMultiplexer, TextureAtlas atlas, AssetManager assetManager) {
        this.inputMultiplexer = inputMultiplexer;
        this.atlas = atlas;
        this.assetManager = assetManager;
        Viewport viewport = new ScreenViewport();
        stage = new Stage(viewport);


        Label.LabelStyle label32Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("fonts/font-32.fnt"));
        label32Style.font = myFont;
        label32Style.fontColor = Color.WHITE;
        label32Style.font.setColor(Color.WHITE);
        subtitles = new TypingLabel("(press any key to skip)", label32Style);
        subtitles.setPosition(Gdx.graphics.getWidth() / 32, Gdx.graphics.getHeight() / 16);
        subtitles.setWidth(Gdx.graphics.getWidth());
        subtitles.setWrap(true);
        subtitles.setAlignment(Align.center);
        subtitles.getColor().a = 0;
        subtitles.addAction(Actions.fadeIn(1f));
        subtitles.setColor(new Color(1f, 1f, 0.949f, 1f));

        dragon_queen = new Image(assetManager.get("images/included/intro/dragon_queen.png", Texture.class));
        earth_ground = new Image(assetManager.get("images/included/intro/earth_ground.png", Texture.class));
        earth_space = new Image(assetManager.get("images/included/intro/earth_space.png", Texture.class));
        missiles_and_explosion_1 = new Image(assetManager.get("images/included/intro/missiles_and_explosion_1.png", Texture.class));
        missiles_and_explosion_2 = new Image(assetManager.get("images/included/intro/missiles_and_explosion_2.png", Texture.class));
        missiles_and_explosion_3 = new Image(assetManager.get("images/included/intro/missiles_and_explosion_3.png", Texture.class));
        silo_and_skeleton = new Image(assetManager.get("images/included/intro/silo_and_skeleton.png", Texture.class));
        sky_and_mountains = new Image(assetManager.get("images/included/intro/sky_and_mountains.png", Texture.class));
        sky_dragon = new Image(assetManager.get("images/included/intro/sky_dragon.png", Texture.class));
        lyze = new Image(assetManager.get("images/included/intro/lyze.png", Texture.class));
        space = new Image(assetManager.get("images/included/intro/space.png", Texture.class));
        zebra_king = new Image(assetManager.get("images/included/intro/zebra_king.png", Texture.class));
        black_screen = new Image(assetManager.get("images/included/intro/black_screen.png", Texture.class));

        stage.addActor(space);
        stage.addActor(dragon_queen);
        stage.addActor(earth_ground);
        stage.addActor(earth_space);
        stage.addActor(missiles_and_explosion_1);
        stage.addActor(missiles_and_explosion_2);
        stage.addActor(missiles_and_explosion_3);
        stage.addActor(sky_and_mountains);
        stage.addActor(silo_and_skeleton);
        stage.addActor(sky_dragon);
        stage.addActor(lyze);
        stage.addActor(zebra_king);
        stage.addActor(black_screen);
        stage.addActor(subtitles);

        dragon_queen.getColor().a = 0;
        earth_ground.getColor().a = 0;
        earth_space.getColor().a = 0;
        missiles_and_explosion_1.getColor().a = 0;
        missiles_and_explosion_2.getColor().a = 0;
        missiles_and_explosion_3.getColor().a = 0;
        silo_and_skeleton.getColor().a = 0;
        sky_and_mountains.getColor().a = 0;
        sky_dragon.getColor().a = 0;
        lyze.getColor().a = 0;
        zebra_king.getColor().a = 0;
        black_screen.getColor().a = 0;

        space.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        dragon_queen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        earth_space.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        earth_ground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        silo_and_skeleton.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sky_dragon.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lyze.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sky_and_mountains.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        missiles_and_explosion_1.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        missiles_and_explosion_2.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        missiles_and_explosion_3.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        zebra_king.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        black_screen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        earth_space.addAction(Actions.sequence(
                actI(),
                actII()
        ));

        //        Music music = assetManager.get("audio/music/398937__mypantsfelldown__metal-footsteps.wav",
        // Music.class);

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    private Action actI() {
        return Actions.sequence(
                Actions.fadeIn(2f),
                Actions.delay(1f),
                Actions.run(() -> {
                    missiles_and_explosion_1.addAction(Actions.fadeIn(1f));
                    subtitles.restart();
                    subtitles.setText("The war of the floating cities...");
                }),
                Actions.delay(.1f),
                Actions.run(() -> zebra_king.addAction(Actions.fadeIn(6f))),
                Actions.delay(.3f),
                Actions.run(() -> dragon_queen.addAction(Actions.fadeIn(6f))),
                Actions.delay(3f),
                Actions.run(() -> {
                    missiles_and_explosion_1.addAction(Actions.fadeOut(1f));
                    missiles_and_explosion_2.addAction(Actions.fadeIn(1f));
                    subtitles.restart();
                    subtitles.setText("left this world in ruins...");
                }),
                Actions.delay(2f),
                Actions.run(() -> {
                    missiles_and_explosion_2.addAction(Actions.fadeOut(1f));
                    missiles_and_explosion_3.addAction(Actions.fadeIn(1f));
                }),
                Actions.delay(2f),
                Actions.run(() -> {
                    zebra_king.addAction(Actions.sequence(
                            Actions.fadeOut(1f),
                            Actions.run(() -> zebra_king.setVisible(false)))
                    );
                    dragon_queen.addAction(Actions.sequence(
                            Actions.fadeOut(1f),
                            Actions.run(() -> dragon_queen.setVisible(false)))
                    );
                    missiles_and_explosion_3.addAction(Actions.sequence(
                            Actions.fadeOut(1f),
                            Actions.run(() -> missiles_and_explosion_3.setVisible(false)))
                    );
                    earth_space.addAction(Actions.sequence(
                            Actions.fadeOut(1f),
                            Actions.run(() -> earth_space.setVisible(false)))
                    );
                    space.addAction(Actions.sequence(
                            Actions.fadeOut(1f),
                            Actions.run(() -> space.setVisible(false)))
                    );
                    subtitles.addAction(Actions.fadeOut(1f));
                })
        );
    }


    private Action actII() {
        return Actions.sequence(
                Actions.run(() -> {
                    sky_and_mountains.addAction(Actions.fadeIn(2));
                    earth_ground.addAction(Actions.fadeIn(2));
                    silo_and_skeleton.addAction(Actions.fadeIn(2));
                    sky_dragon.addAction(Actions.sequence(
                            Actions.fadeIn(0),
                            Actions.moveTo(-Gdx.graphics.getWidth() * .6f, -Gdx.graphics.getHeight() * .1f, 0),
                            Actions.moveTo(Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() * .4f, 30)
                    ));
                    lyze.setPosition(Gdx.graphics.getWidth() * .05f, 0);
                    lyze.addAction(Actions.sequence(
                            Actions.fadeIn(0f),
                            Actions.moveTo(-Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f, 30f)
                    ));
                    subtitles.addAction(Actions.fadeIn(1f));
                    subtitles.setColor(new Color(0.039f, 0.039f, 0.043f, 1f));
                    subtitles.restart();
                    subtitles.setText("but from the destruction...");
                }),
                Actions.delay(3f),
                Actions.run(() -> {
                    subtitles.restart();
                    subtitles.setText("well, life always finds a way");
                }),
                Actions.delay(2f),
                Actions.run(() -> subtitles.addAction(Actions.fadeOut(1f))),
                Actions.delay(2f),
                Actions.run(() -> black_screen.addAction(Actions.fadeIn(1f)))
        );
    }
}
