package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import io.github.fourlastor.game.ui.ParallaxImage;

public class ImageComponent implements Component {

    public final ParallaxImage image;

    public ImageComponent(ParallaxImage image) {
        this.image = image;
    }
}
