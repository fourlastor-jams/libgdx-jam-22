package no.sandramoen.commanderqueen;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.fourlastor.game.MyGdxGame;
import java.awt.Dimension;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    private static final float PERCENT_OF_SCREEN_SIZE = 0.8f;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Post Apocalyptic");
        setWindowedMode(config);
        new Lwjgl3Application(MyGdxGame.createGame(), config);
    }

    private static void setWindowedMode(Lwjgl3ApplicationConfiguration config) {
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (dimension.height * PERCENT_OF_SCREEN_SIZE);

        float aspectRatio = 16f / 9f;
        int width = (int) (height / aspectRatio);

        System.out.println("Window dimensions => width: " + width + ", height: " + height);
        config.setWindowedMode(width, height);
    }
}
