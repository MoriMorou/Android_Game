package ru.mori.morou.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * pointer - индикатор нажатия (каким пальцем нажате)
 * pressed - состояние кнопки (нажата или нет)
 * pressScale - уменьшение кнопки после нажатия
 */

public abstract class ButtonAction extends Sprite {
    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean pressed;

    public ButtonAction(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) {
            return false;
        }
        pressed = true;
        this.pointer = pointer;
        scale = PRESS_SCALE;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touch)) {
            actionPerformed();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    protected abstract void actionPerformed();
}
