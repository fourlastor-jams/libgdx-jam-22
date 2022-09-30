package io.github.fourlastor.game.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class FixedImage extends Image {

    public FixedImage(TextureRegion sprite) {
        super(sprite);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float worldHeight = getStage().getViewport().getWorldHeight();
        Camera camera = getStage().getCamera();
        Vector3 cameraPosition = camera.position;
        float newY = cameraPosition.y - worldHeight / 2;
        setY(newY);
    }
}
