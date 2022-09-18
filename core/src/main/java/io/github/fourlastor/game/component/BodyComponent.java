package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class BodyComponent implements Component {

    public final BodyDef def;
    public Body body = null;

    public BodyComponent(BodyDef def) {
        this.def = def;
    }
}
