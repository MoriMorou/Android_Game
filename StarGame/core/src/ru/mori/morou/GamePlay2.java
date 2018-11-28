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


public class GamePlay2 extends Base2DScreen {

    private Texture spaceCraft;
    private Texture background;

    private Vector2 position = new Vector2(100, 100);
    private Vector2 target = new Vector2();
    private Vector2 speed = new Vector2(1,1);
    float speedIn = 10f;

    private void moving() {
        if (speed.len() > 0) {
            final Vector2 currentPos = position.cpy();
            final Vector2 newPos = target.cpy();
            float distance = currentPos.sub(newPos).len();

            if (distance > speed.len()) {
                position.add(speed);
            } else {
                position.set(target);
                stopMoving();
            }
        }
    }

    private void stopMoving() {
        target.set(0,0);
    }


    @Override
    public void show() {
        super.show();
        spaceCraft = new Texture("spacecraft2.png");
        background = new Texture("space2.png");
        moving();

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, 1000, 1000);
        batch.draw(spaceCraft, target.x, target.y, 100, 100);
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
        target.set(screenX, Gdx.graphics.getHeight() - screenY);
        movingTo(target.x, target.y);
        return false;
    }

    private void movingTo(float targetX, float targetY){
        float deltaX = targetX - position.x;
        float deltaY = targetY - position.y;
        float angle = (float) Math.atan2(deltaY, deltaX);
        target.x += 2 * Math.cos(angle);//Using cos
        target.y += 2 * Math.sin(angle);//or sin
        System.out.print(target);
    }
}
