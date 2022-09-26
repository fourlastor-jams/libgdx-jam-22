package io.github.fourlastor.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Panel;

import io.github.fourlastor.game.MyGdxGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                GwtApplicationConfiguration config = new GwtApplicationConfiguration(true);
                config.padHorizontal = 0;
                config.padVertical = 0;
                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return MyGdxGame.createGame();
        }

        @Override
        public Preloader.PreloaderCallback getPreloaderCallback() {
                return createPreloaderPanel(GWT.getHostPageBaseURL() + "preloadlogo.png");
        }

        @Override
        protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
                meterPanel.setStyleName("gdx-meter");
                meterPanel.addStyleName("nostripes");
                meterStyle.setProperty("backgroundColor", "#ffffff");
                meterStyle.setProperty("backgroundImage", "none");
        }
}
