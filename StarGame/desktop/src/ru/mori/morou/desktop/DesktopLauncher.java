package ru.mori.morou.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.mori.morou.Star2DGame;
import ru.mori.morou.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Star Game";
		config.height = 1000;
		config.width = 1000;
		new LwjglApplication(new Star2DGame(), config);
	}
}
