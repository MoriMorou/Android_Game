package ru.mori.morou.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.base.Base2DScreen;

public class Hero extends Base2DScreen {

    private Texture hero;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;
    private Vector2 buf;

    @Override
    public void show() {
        super.show();
        hero = new Texture("spacecraft2.png");
        pos = new Vector2(0,0);
        v = new Vector2();
        touch = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        buf.set(touch);
        if(buf.set(pos).len() >= v.len()) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
        batch.begin();
        batch.draw(hero, pos.x, pos.y, 10f, 10f);
        batch.end();
    }

    @Override
    public void dispose() {
        hero.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        v.set(touch.cpy().sub(pos).scl(0.01f));
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        float step = 0.1f;
        switch (keycode) {
            case Input.Keys.UP:
                touch = new Vector2(pos.x, pos.y + step);
                break;
            case Input.Keys.RIGHT:
                touch = new Vector2(pos.x + step, pos.y);
                break;
            case Input.Keys.DOWN:
                touch = new Vector2(pos.x, pos.y - step);
                break;
            case Input.Keys.LEFT:
                touch = new Vector2(pos.x - step, pos.y);
                break;
            default:
                break;
        }
        if (touch != pos) {
            v = touch.cpy().sub(pos).nor();
        }
        return super.keyDown(keycode);
    }
}