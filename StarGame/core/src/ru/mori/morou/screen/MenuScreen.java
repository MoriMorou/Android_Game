package ru.mori.morou.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Base2DScreen;
import ru.mori.morou.sprite.ButtonExit;
import ru.mori.morou.sprite.ButtonStart;
import ru.mori.morou.sprite.Hero;
import ru.mori.morou.sprite.Star;
import ru.mori.morou.sprite.Background;

/**
 * add() - Сложение двух векторов.
 * sub() - Вычитание векторов.
 * scl() - Умножение вектора на скаляр.
 * len() - Получение длины вектора.
 * nor() - Нормирование вектора.
 * cpy() - Копирование вектора.
 * dot() - Скалярное произведение.
 *
 * This class is responsible for the gameplay
 * Method moving - changing of a Spacecraft position on the map
 *
 */

public class MenuScreen extends Base2DScreen{

    private static final int STAR_COUNT = 100;
    private static final float PRESS_SCALE = 0.9f;
    private static final float START_BUTTON_HEIGHT = 0.05f;
    private static final float EXIT_BUTTON_HEIGHT = 0.05f;

    private Texture bg;
    private TextureAtlas atlasMain;
    private Background background;
    private Star[] star;
    private Hero hero;
    private ButtonExit buttonExit;
    private ButtonStart buttonStart;
    private TextureAtlas menuNew;


    @Override
    public void show() {
        super.show();
        atlasMain = new TextureAtlas("textures/menuNew");
        bg = new Texture("space.png");
        background = new Background(new TextureRegion(bg));
        star = new Star[STAR_COUNT];
        hero = new Hero();

        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlasMain);
        }

        buttonExit = new ButtonExit(atlasMain, this, PRESS_SCALE);
        buttonExit.setHeightProportion(EXIT_BUTTON_HEIGHT);

        buttonStart = new ButtonStart(atlasMain, this, PRESS_SCALE);
        buttonStart.setHeightProportion(START_BUTTON_HEIGHT);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0.3f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        buttonExit.draw(batch);
        buttonStart.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonStart.resize(worldBounds);
    }

    @Override
    public void dispose() {
        atlasMain.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        buttonExit.touchDown(touch, pointer);
        buttonStart.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        buttonExit.touchUp(touch, pointer);
        buttonStart.touchUp(touch, pointer);
        return false;
    }
}


