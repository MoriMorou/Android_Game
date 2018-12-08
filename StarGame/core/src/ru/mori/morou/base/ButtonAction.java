package ru.mori.morou.base;

/**
 * pointer - индикатор нажатия (каким пальцем нажате)
 * pressed - состояние кнопки (нажата или нет)
 * pressScale - уменьшение кнопки после нажатия
 */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ButtonAction extends Sprite {

    private int pointer;
    private boolean pressed;
    private ActionListener actionListener;
    private float pressScale;
    public ButtonAction(TextureRegion region, ActionListener actionListener, float pressScale) {
        super(region);
        this.pressed = false;
        this.actionListener = actionListener;
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
            actionListener.actionPerformed(this);
        }
        this.pressed = false;
        this.scale = 1f;
    }
}

