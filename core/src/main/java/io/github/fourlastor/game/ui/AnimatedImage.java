package io.github.fourlastor.game.ui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.IntMap;

public class AnimatedImage extends Image {

    private IntMap<TextureRegionDrawable> frameCache;
    private Animation<TextureRegion> animation;

    public boolean playing = true;
    public float playTime = 0f;

    public AnimatedImage(Animation<TextureRegion> animation) {
        super(animation.getKeyFrame(0));
        setAnimation(animation);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!playing) {
            return;
        }
        playTime += delta;
        int index = animation.getKeyFrameIndex(playTime);
        TextureRegionDrawable cached = frameCache.get(index);
        if (cached == null) {
            cached = new TextureRegionDrawable(animation.getKeyFrame(playTime));
            frameCache.put(index, cached);
        }

        setDrawable(cached);
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
        this.frameCache = new IntMap<>(animation.getKeyFrames().length);
        this.playTime = 0f;
    }
}
