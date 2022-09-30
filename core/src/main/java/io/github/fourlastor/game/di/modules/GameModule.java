package io.github.fourlastor.game.di.modules;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dagger.Module;
import dagger.Provides;
import io.github.fourlastor.game.MyGdxGame;
import io.github.fourlastor.game.gameover.GameOverComponent;
import io.github.fourlastor.game.intro.IntroComponent;
import io.github.fourlastor.game.level.di.LevelComponent;
import java.util.Random;
import javax.inject.Singleton;

@Module
public class GameModule {

    private static final String PATH_TEXTURE_ATLAS = "images/included/packed/images.pack.atlas";

    @Provides
    @Singleton
    public AssetManager assetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.load(PATH_TEXTURE_ATLAS, TextureAtlas.class);

        assetManager.load("images/included/whitePixel.png", Texture.class);

        assetManager.load("images/included/intro/dragon_queen.png", Texture.class);
        assetManager.load("images/included/intro/earth_ground.png", Texture.class);
        assetManager.load("images/included/intro/earth_space.png", Texture.class);
        assetManager.load("images/included/intro/missiles_and_explosion_1.png", Texture.class);
        assetManager.load("images/included/intro/missiles_and_explosion_2.png", Texture.class);
        assetManager.load("images/included/intro/missiles_and_explosion_3.png", Texture.class);
        assetManager.load("images/included/intro/silo_and_skeleton.png", Texture.class);
        assetManager.load("images/included/intro/sky_and_mountains.png", Texture.class);
        assetManager.load("images/included/intro/sky_dragon.png", Texture.class);
        assetManager.load("images/included/intro/space.png", Texture.class);
        assetManager.load("images/included/intro/zebra_king.png", Texture.class);
        assetManager.load("images/included/intro/lyze.png", Texture.class);
        assetManager.load("images/included/intro/black_screen.png", Texture.class);
        assetManager.load("images/included/intro/black_screen.png", Texture.class);
        assetManager.load("images/included/hold to jump higher.png", Texture.class);

        assetManager.load("audio/sounds/190469__alxy__rapid-missile-launch.wav", Sound.class);
        assetManager.load("audio/sounds/379352__hard3eat__atomic-bomb.wav", Sound.class);
        assetManager.load("audio/sounds/sandra_intro.wav", Sound.class);
        assetManager.load("audio/sounds/sawblade.ogg", Sound.class);
        assetManager.load("audio/sounds/553418__eminyildirim__cinematic-boom-impact-hit-2021.wav", Sound.class);
        assetManager.load("audio/sounds/chargeJump.wav", Sound.class);
        for (int i = 0; i <= 4; i++) assetManager.load("audio/sounds/jumping/jump_" + i + ".wav", Sound.class);
        for (int i = 0; i <= 4; i++) assetManager.load("audio/sounds/onGround/onGround_" + i + ".wav", Sound.class);
        assetManager.load("audio/sounds/Blip_Select11.wav", Sound.class);
        assetManager.load("audio/sounds/grateSound.wav", Sound.class);
        assetManager.load("audio/sounds/445109__breviceps__mud-splat.wav", Sound.class);
        assetManager.load("audio/sounds/446115__justinvoke__wet-splat.wav", Sound.class);
        assetManager.load("audio/sounds/fish.mp3", Sound.class);

        assetManager.load("audio/music/428674__phantastonia__cinematic-vio2.wav", Music.class);
        assetManager.load("audio/music/608308__aidangig__radiation-ambience-effect.wav", Music.class);
        assetManager.load("audio/music/511887__lusmog__postapocalypse-theme-loop.mp3", Music.class);
        assetManager.load("audio/music/ambiance_mix.wav", Music.class);

        assetManager.finishLoading();

        return assetManager;
    }

    @Provides
    @Singleton
    public InputMultiplexer inputMultiplexer() {
        return new InputMultiplexer();
    }

    @Provides
    @Singleton
    public TextureAtlas textureAtlas(AssetManager assetManager) {
        return assetManager.get(PATH_TEXTURE_ATLAS, TextureAtlas.class);
    }

    @Provides
    @Singleton
    public MyGdxGame game(
            InputMultiplexer multiplexer,
            LevelComponent.Builder levelBuilder,
            IntroComponent.Builder introBuilder,
            GameOverComponent.Builder gameOverBuilder) {
        return new MyGdxGame(multiplexer, levelBuilder, introBuilder, gameOverBuilder);
    }

    @Provides
    public Random random() {
        return new Random();
    }
}
