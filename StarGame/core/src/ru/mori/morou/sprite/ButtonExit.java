package ru.mori.morou.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.ButtonAction;


public class ButtonExit extends ButtonAction {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("exitUp"));
        setHeightProportion(0.03f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.01f);
        setRight(worldBounds.getRight() - 0.01f);
    }

    @Override
    public void actionPerformed() {
        Gdx.app.exit();
    }
}
