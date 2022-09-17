package io.github.fourlastor.game.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import io.github.fourlastor.game.di.EntityComponents;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComponentMappers {

    private final Map<Class<?>, ComponentMapper> mappers;

    @Inject
    public ComponentMappers(@EntityComponents Map<Class<?>, ComponentMapper> mappers) {
        this.mappers = mappers;
    }

    public <T extends Component> ComponentMapper<T> get(Class<T> clazz) {
        //noinspection unchecked
        return mappers.get(clazz);
    }
}
