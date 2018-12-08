package ru.mori.morou.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * pointer - индикатор нажатия (каким пальцем нажате)
 * pressed - состояние кнопки (нажата или нет)
 * pressScale - уменьшение кнопки после нажатия
 */

public abstract class ButtonAction extends Sprite {

    private int pointer;
    private boolean pressed;
    private float pressScale;

    public ButtonAction(TextureRegion region, float pressScale) {
        super(region);
        this.pressed = false;
        this.pressScale = pressScale;
    }
    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if(pressed || !isMe(touch)){
            return;
        }
        this.pointer = pointer;
        this.pressed = true;
        this.scale = pressScale;
    }
    @Override
    public void touchUp(Vector2 touch, int indicator) {
        if(this.pointer != pointer || !pressed){
            return;
        }
        if(isMe(touch)){
            actionPerformed();
        }
        this.pressed = false;
        this.scale = 1f;
    }

    protected abstract void actionPerformed();
}

