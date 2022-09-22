package io.github.fourlastor.game.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParallaxImage extends Actor {

    private final TextureRegion texture;
    private final float factor;

    private float currentDelta = 0f;

    public ParallaxImage(float factor, TextureRegion texture) {
        super();
        setBounds(0f, 0f, texture.getRegionWidth(), texture.getRegionHeight());
        setPosition(0f, 0f);
        this.factor = factor;
        this.texture = texture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Camera camera = getStage().getCamera();

        currentDelta = -((camera.position.y * factor));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Camera camera = getStage().getCamera();

        float targetWidth = getWidth() * getScaleX();
        float targetHeight = getHeight() * getScaleY();
        float targetX = camera.position.x - targetWidth / 2;
        float targetY = camera.position.y - targetHeight / 2;
        float deltaY = currentDelta % targetHeight;

        batch.draw(texture, targetX, targetY + deltaY, targetWidth, targetHeight);
        batch.draw(texture, targetX, targetY + deltaY + targetHeight, targetWidth, targetHeight);
    }
}
