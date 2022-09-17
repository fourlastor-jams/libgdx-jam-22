package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorComponent implements Component {

    public final Actor actor;

    public ActorComponent(Actor actor) {
        this.actor = actor;
    }
}
