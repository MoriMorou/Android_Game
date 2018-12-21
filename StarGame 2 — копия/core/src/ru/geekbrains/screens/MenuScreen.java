package ru.geekbrains.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;

import ru.geekbrains.control.ExitButton;
import ru.geekbrains.control.PlayButton;
import ru.geekbrains.engine.ActionListener;
import ru.geekbrains.engine.Base2DScreen;
import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.math.Rnd;
import ru.geekbrains.models.backgrounds.Background_Rorate;
import ru.geekbrains.models.stars.StarWithOrbit;


public class MenuScreen extends Base2DScreen implements ActionListener {

    private static final float BUTTON_HEIGHT = 0.15f;
    private static final float BUTTON_PRESS_SCALE = 0.9f;

    private Texture backgroundTexture;
    private Background_Rorate backgroundRorate;
    private Texture playImg;
    private Texture exitImg;
    private Texture[] star = new Texture[4];
    private Random random = new Random();
    private float orbit;

    LevelDesinger desinger = new LevelDesinger();

    PlayButton playButton;
    ExitButton exitButton;

    private StarWithOrbit[] starWithOrbits = new StarWithOrbit[400];
    private StarWithOrbit[] starsCenter = new StarWithOrbit[100];

    public MenuScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("bg_menu1.jpg");
        backgroundRorate = new Background_Rorate(new TextureRegion(backgroundTexture));

        playImg = new Texture("play1.png");
        playButton = new PlayButton(new TextureRegion(playImg), BUTTON_PRESS_SCALE, this);
        playButton.setHeightProportion(BUTTON_HEIGHT);

        exitImg = new Texture("exit1.png");
        exitButton = new ExitButton(new TextureRegion(exitImg), BUTTON_PRESS_SCALE, this);
        exitButton.setHeightProportion(BUTTON_HEIGHT);
        star[0] = new Texture("starWhite.png");
        star[1] = new Texture("starRed1.png");
        star[2] = new Texture("starBlue1.png");
        star[3] = new Texture("starYellow.png");
        for (int i = 0; i < starsCenter.length; i++) {
            starsCenter[i]=new StarWithOrbit(new TextureRegion(star[random.nextInt(3)]),
                    Rnd.nextFloat(0.03f, 0.1f),
                    orbit = Rnd.nextFloat(0.015f, 0.15f),orbit/Rnd.nextFloat(0.55f, 0.95f),
                    Rnd.nextFloat(-3.14f, 3.14f),
                    Rnd.nextFloat(0.005f, 0.01f));
        }
        for (int i = 0; i < starWithOrbits.length; i++) {
            starWithOrbits[i]=new StarWithOrbit(new TextureRegion(star[random.nextInt(3)]),
                    Rnd.nextFloat(0.1f, 0.2f),
                    orbit=Rnd.nextFloat(0.1f, 0.5f),orbit/Rnd.nextFloat(0.8f, 1f),
                    Rnd.nextFloat(-3.14f, 3.14f),
                    Rnd.nextFloat(0.01f, 0.02f));
        }
        desinger.startMusicMenu();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < starsCenter.length; i++) {
            starsCenter[i].update(delta);
        }
        for (int i = 0; i < starWithOrbits.length; i++) {
            starWithOrbits[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgroundRorate.draw(batch);
        for (int i = 0; i < starsCenter.length; i++) {
            starsCenter[i].draw(batch);
        }
        for (int i = 0; i < starWithOrbits.length; i++) {
            starWithOrbits[i].draw(batch);
        }
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        backgroundRorate.resize(worldBounds);
        for (int i = 0; i < starsCenter.length; i++) {
            starsCenter[i].resize(worldBounds);
        }
        for (int i = 0; i < starWithOrbits.length; i++) {
            starWithOrbits[i].resize(worldBounds);
        }
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playImg.dispose();
        exitImg.dispose();
        for (int i = 0; i < star.length ; i++) {
            star[i].dispose();
        }
        super.dispose();
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        exitButton.touchUp(touch, pointer);
        playButton.touchUp(touch, pointer);
    }
    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        exitButton.touchDown(touch, pointer);
        playButton.touchDown(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == exitButton) {
            Gdx.app.exit();
        } else if (src == playButton) {
            desinger.stopMusicMenu();
            game.setScreen(new GameScreen(game));
        } else {
            throw new RuntimeException("Unknown src " + src);
        }
    }
}
