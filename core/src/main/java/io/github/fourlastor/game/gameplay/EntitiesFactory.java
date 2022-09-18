package io.github.fourlastor.game.gameplay;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.fourlastor.game.component.ActorComponent;
import io.github.fourlastor.game.component.BodyBuilderComponent;
import io.github.fourlastor.game.component.PlayerComponent;
import javax.inject.Inject;

public class EntitiesFactory {

    private final TextureAtlas atlas;

    @Inject
    public EntitiesFactory(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Entity player() {
        Entity playerEntity = new Entity();
        playerEntity.add(new ActorComponent(new Image(atlas.findRegion("whitePixel"))));
        playerEntity.add(new PlayerComponent());
        return playerEntity;
    }

    public Entity ground() {
        Entity entity = new Entity();
        entity.add(new BodyBuilderComponent(world -> {
            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.position.set(new Vector2(5.5f, 0f));
            Body body = world.createBody(groundBodyDef);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox(6, 1);
            body.createFixture(groundBox, 0.0f);
            groundBox.dispose();
            return body;
        }));

        return entity;
    }
}
