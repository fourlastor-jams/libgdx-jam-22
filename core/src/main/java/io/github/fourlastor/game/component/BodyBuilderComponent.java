package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/** Request to build a body in Box2D, will create a body through the factory*/
public class BodyBuilderComponent implements Component {

    public final Factory factory;

    public BodyBuilderComponent(Factory factory) {
        this.factory = factory;
    }

    public interface Factory {
        Body build(World world);
    }
}
