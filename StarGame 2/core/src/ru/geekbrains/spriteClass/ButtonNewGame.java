package ru.geekbrains.spriteClass;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.baseClass.ActionListener;
import ru.geekbrains.baseClass.ScaledTouchUpButton;
import ru.geekbrains.math.Rect;

public class ButtonNewGame extends ScaledTouchUpButton {

    private static final float HEIGHT = 0.1f;
    private static final float BOTTOM_MARGIN = 0.07f;
    private static final float PRESS_SCALE = 0.9f;

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener, Rect worldBounds) {
        super(atlas.findRegion("new_game"), actionListener, PRESS_SCALE);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }
}