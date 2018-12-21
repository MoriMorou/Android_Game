package ru.geekbrains.control;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.engine.ActionListener;
import ru.geekbrains.engine.ui.ScaledTouchUpButton;

public class ButtonNewGame extends ScaledTouchUpButton {

    private static final float HEIGHT = 0.05f;
    private static final float TOP = -0.012f;
    private static final float PRESS_SCALE = 0.9f;

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), PRESS_SCALE, actionListener);
        setHeightProportion(HEIGHT);
        setTop(TOP);
    }
}