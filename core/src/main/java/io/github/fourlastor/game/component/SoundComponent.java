package io.github.fourlastor.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent implements Component {

    private final Sound sound;
    private long id = -1;

    public SoundComponent(Sound sound) {
        this.sound = sound;
    }

    public void play(float volume) {
        if (id == -1) {
            id = sound.loop();
        }
        sound.setVolume(id, volume);
    }

    public void stop() {
        if (id != -1) {
            sound.stop(id);
            id = -1;
        }
    }
}
