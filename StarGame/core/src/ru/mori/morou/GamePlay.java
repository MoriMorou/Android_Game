package ru.mori.morou;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.baseClasses.Base2DScreen;

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


public class GamePlay extends Base2DScreen {

    private Texture spaceCraft;
    private Texture background;

    private Vector2 position = new Vector2(100, 100);
    private Vector2 finalPosition = new Vector2();
    private Vector2 speed = new Vector2();
    private Vector2 buffer = new Vector2();


    @Override
    public void show() {
        super.show();
        spaceCraft = new Texture("spacecraft2.png");
        background = new Texture("space2.png");

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        buffer.set(finalPosition);
        if (buffer.sub(position).len() > 0.5f) {
            position.add(speed);
        } else {
            position.set(finalPosition);
        }
        batch.begin();
        batch.draw(background, 0, 0, 2f, 2f);
        batch.draw(spaceCraft, position.x, position.y);
        batch.end();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        spaceCraft.dispose();
        background.dispose();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        //we must rotate the coordinate axis
        finalPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        speed.set(finalPosition.cpy().sub(position).setLength(0.5f));
        return false;
    }
}
