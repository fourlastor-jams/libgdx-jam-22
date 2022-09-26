package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorComponent implements Component {

    public final Actor actor;
    public final Layer layer;

    public ActorComponent(Actor actor, Layer layer) {
        this.actor = actor;
        this.layer = layer;
    }

    public enum Layer {
        BG_PARALLAX,
        PLATFORM,
        ENEMIES,
        CHARACTER,
        FG_PARALLAX
    }
}
