package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.ActionListener;
import ru.mori.morou.base.ButtonAction;

public class ButtonStart extends ButtonAction {

    public ButtonStart(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("startUp"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0f);
        setLeft(worldBounds.getLeft() + 0f);
    }
}

