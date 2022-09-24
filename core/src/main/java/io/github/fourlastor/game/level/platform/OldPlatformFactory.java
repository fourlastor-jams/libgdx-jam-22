package io.github.fourlastor.game.level.platform;

import io.github.fourlastor.game.di.ScreenScoped;
import java.util.Random;
import javax.inject.Inject;

@ScreenScoped
public class OldPlatformFactory {

    public static final int SAFE_GROUND_THRESHOLD = 15;
    private final Random random;
    private int platformsCount = 0;
    private int platformsSinceSafeGround = 0;
    private PlatformSpec lastSpec;

    @Inject
    public OldPlatformFactory(Random random) {
        this.random = random;
    }

    public PlatformSpec nextPlatform() {
        lastSpec = generateNextPlatform(nextPlatformIndex(), lastSpec);
        if (lastSpec.speed == PlatformSpec.Speed.IMMOBILE && lastSpec.width == PlatformSpec.Width.NINE) {
            platformsSinceSafeGround = 0;
        } else {
            platformsSinceSafeGround += 1;
        }
        return lastSpec;
    }

    private int nextPlatformIndex() {
        int index = platformsCount;
        platformsCount += 1;
        return index;
    }

    public PlatformSpec generateNextPlatform(int index, PlatformSpec lastSpec) {
        if (shouldBeSafeGround(index) || lastSpec == null) {
            return safeGround(index);
        }
        PlatformSpec.Width width = randomWidth();
        PlatformSpec.Type type;
        if (width == PlatformSpec.Width.ONE) {
            type = PlatformSpec.Type.SMALL_GRID;
        } else {
            type = randomType();
        }
        PlatformSpec.Speed speed = randomSpeed(lastSpec);
        float x = random.nextFloat() * 4f + 2f;
        float y = platformY(index);
        return new PlatformSpec(width, type, speed, x, y, randomGoingLeft());
    }

    private boolean shouldBeSafeGround(int index) {
        return index == 0 || platformsSinceSafeGround >= SAFE_GROUND_THRESHOLD;
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

    private PlatformSpec.Speed randomSpeed(PlatformSpec lastSpec) {
        if (lastSpec.speed == PlatformSpec.Speed.IMMOBILE) {
            return random.nextBoolean() ? PlatformSpec.Speed.FAST : PlatformSpec.Speed.MEDIUM;
        }
        PlatformSpec.Speed[] values = PlatformSpec.Speed.values();
        return values[random.nextInt(values.length)];
    }

    private float platformY(int index) {
        return 4f * index;
    }
}
