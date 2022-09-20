package io.github.fourlastor.game.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParallaxImage extends Actor {

    private final float factor;
    private final TextureRegion region;

    public ParallaxImage(float factor, Texture texture) {
        super();
        setBounds(0f, 0f, texture.getWidth(), texture.getHeight());
        setPosition(0f, 0f);
        this.factor = factor;
        this.region = new TextureRegion(texture);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Camera camera = getStage().getCamera();

        region.setRegionY((int) (-camera.position.y * factor / getScaleY()));
        region.setRegionHeight((int) getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Camera camera = getStage().getCamera();

        float targetWidth = getWidth() * getScaleX();
        float targetHeight = getHeight() * getScaleY();
        float targetX = camera.position.x - targetWidth / 2;
        float targetY = camera.position.y - targetHeight / 2;

        batch.draw(region, targetX, targetY, targetWidth, targetHeight);
    }
}
