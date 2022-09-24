package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class MovingComponent implements Component {

    public int position = 0;
    public final List<Vector2> path;
    public final float speed;

    public MovingComponent(List<Vector2> path, float speed) {
        this.path = path;
        this.speed = speed;
    }
}
