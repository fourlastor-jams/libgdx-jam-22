package io.github.fourlastor.game.level.platform;

import io.github.fourlastor.game.di.ScreenScoped;
import java.util.Random;
import javax.inject.Inject;

@ScreenScoped
public class PlatformFactory {

    private final Random random;
    private int platformsCount = 0;

    @Inject
    public PlatformFactory(Random random) {
        this.random = random;
    }

    public PlatformSpec nextPlatform() {
        int index = nextPlatformIndex();
        if (index == 0) {
            return safeGround(index);
        }
        PlatformSpec.Width width = randomWidth();
        PlatformSpec.Type type;
        if (width == PlatformSpec.Width.ONE) {
            type = PlatformSpec.Type.SMALL_GRID;
        } else {
            type = randomType();
        }
        PlatformSpec.Speed speed = randomSpeed();
        float x = random.nextFloat() * 4f + 2f;
        float y = 4f * index;
        return new PlatformSpec(width, type, speed, x, y, randomGoingLeft());
    }

    private PlatformSpec safeGround(int index) {
        return new PlatformSpec(
                PlatformSpec.Width.NINE,
                PlatformSpec.Type.LARGE_GRID,
                PlatformSpec.Speed.IMMOBILE,
                4.5f,
                platformY(index),
                false);
    }

    private boolean randomGoingLeft() {
        return random.nextBoolean();
    }

    private PlatformSpec.Type randomType() {
        PlatformSpec.Type[] values = PlatformSpec.Type.values();
        return values[random.nextInt(values.length)];
    }

    private PlatformSpec.Width randomWidth() {
        PlatformSpec.Width[] values = PlatformSpec.Width.values();
        return values[random.nextInt(values.length)];
    }

    private PlatformSpec.Speed randomSpeed() {
        PlatformSpec.Speed[] values = PlatformSpec.Speed.values();
        return values[random.nextInt(values.length)];
    }

    private float platformY(int index) {
        return 4f * index;
    }

    private int nextPlatformIndex() {
        int index = platformsCount;
        platformsCount += 1;
        return index;
    }
}
