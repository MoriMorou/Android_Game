package ru.geekbrains.control;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.engine.ActionListener;
import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.ui.ScaledTouchUpButton;


public class ExitButton extends ScaledTouchUpButton {

    public ExitButton(TextureRegion region, float pressScale, ActionListener actionListener) {
        super(region, pressScale, actionListener);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
