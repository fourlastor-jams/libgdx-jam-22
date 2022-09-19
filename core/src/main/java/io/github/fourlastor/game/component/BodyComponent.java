package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

/** Actual body already running in Box2D */
public class BodyComponent implements Component {

    public Body body;

    public BodyComponent(Body body) {
        this.body = body;
    }
}
