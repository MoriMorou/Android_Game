package ru.mori.morou.baseClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *  show - отоброжение
 *  render - частота обновления экрана (60 раз в сек)
 *  resize - изменение размеров экрана
 *  pause - свернули приложение
 *  resume - развернули приложение
 *  hide - закрытие
 *  dispose - очищяем все
 *
 *  keyDown - нажали кнопку
 *  keyUp - отпустили
 *  keyTyped
 *  touchDown - нажатие на экран
 *  touchUp
 *  touchDragged - зажали пальцем кнопку и протянули по экрану
 *  mouseMoved
 *  scrolled
*/

public class Base2DScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
