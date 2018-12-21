package ru.geekbrains.baseClass;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ScaledTouchUpButton extends Sprite {

    private int pointer;
    private boolean pressed;
    private float pressScale;
    private ActionListener actionListener;

    public ScaledTouchUpButton(TextureRegion region, ActionListener actionListener, float pressScale) {
        super(region);
        this.pressScale = pressScale;
        this.pressed = false;
        this.actionListener = actionListener;
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if(pressed || !isMe(touch)){
            return;
        }
        this.pointer = pointer;
        this.scale = pressScale;
        this.pressed = true;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
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