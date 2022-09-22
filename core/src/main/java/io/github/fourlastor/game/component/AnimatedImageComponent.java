package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import io.github.fourlastor.game.ui.AnimatedImage;

public class AnimatedImageComponent implements Component {

    public final AnimatedImage image;

    public AnimatedImageComponent(AnimatedImage image) {
        this.image = image;
    }
}
