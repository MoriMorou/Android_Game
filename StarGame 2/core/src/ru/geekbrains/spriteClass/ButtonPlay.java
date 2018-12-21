package ru.geekbrains.spriteClass;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.baseClass.ActionListener;
import ru.geekbrains.baseClass.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;

public class ButtonPlay extends ScaledTouchUpButton {

    public ButtonPlay(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btPlay"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.03f);
        setLeft(worldBounds.getLeft() + 0.03f);
    }
}
