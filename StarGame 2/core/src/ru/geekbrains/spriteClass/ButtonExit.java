package ru.geekbrains.spriteClass;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.baseClass.ActionListener;
import ru.geekbrains.baseClass.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btExit"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.03f);
        setRight(worldBounds.getRight() - 0.03f);
    }
}
