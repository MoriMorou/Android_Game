package ru.mori.morou.screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Base2DScreen;
import ru.mori.morou.sprite.BackgroundMenu;
import ru.mori.morou.sprite.buttons.ButtonExit;
import ru.mori.morou.sprite.buttons.ButtonStart;
import ru.mori.morou.sprite.space_bodies.Star;

/**
 * @method add() - Сложение двух векторов.
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
 * @param
 *
 *
 */
public class MenuScreen extends Base2DScreen{

    private static final int STAR_COUNT = 256;
    private Star[] star;

    private BackgroundMenu backgroundMenu;
    private Texture bg;
    private TextureAtlas menuAtlas;

    private ButtonExit buttonExit;
    private ButtonStart buttonStart;

    public MenuScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        menuAtlas = new TextureAtlas("menu/menu");
        bg = new Texture("menu/space5.png");
        backgroundMenu = new BackgroundMenu(new TextureRegion(bg));
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(menuAtlas);
        }
        buttonExit = new ButtonExit(menuAtlas);
        buttonStart = new ButtonStart(menuAtlas, game);
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
        Gdx.gl.glClearColor(0, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundMenu.draw(batch);
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
        backgroundMenu.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonStart.resize(worldBounds);
    }

    @Override
    public void dispose() {
        menuAtlas.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonStart.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonStart.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

}
