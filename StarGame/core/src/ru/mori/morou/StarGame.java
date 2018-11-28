package ru.mori.morou;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sun.print.BackgroundLookupListener;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture background;
	TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("spacecraft2.png");
		background = new Texture("space2.png");
//		region = new TextureRegion(img, 20, 30, 100, 100);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, 1000, 1000);
//		batch.setColor(0.1f, 0.1f, 0.1f, 2);
		batch.draw(img, 10, 10, 100, 100);
//		batch.draw(region, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		background.dispose();
	}
}
