package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.ActionListener;
import ru.mori.morou.base.ButtonAction;

public class ButtonExit extends ButtonAction {

    public ButtonExit(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btExit"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.03f);
        setRight(worldBounds.getRight() - 0.03f);
    }
}