package ru.mori.morou.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.mori.morou.Star2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Star Game";
		float aspect = 3f/4f;
		config.width = 600;
		config.height = (int) (config.width / aspect);
		config.resizable = false;
		new LwjglApplication(new Star2DGame(), config);
	}
}
