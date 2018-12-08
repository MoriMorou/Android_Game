package ru.mori.morou.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.ButtonAction;
import ru.mori.morou.screen.MenuScreen;

public class ButtonExit extends ButtonAction {

    public ButtonExit(TextureAtlas atlas, MenuScreen menuScreen, float pressScale) {
        super(atlas.findRegion("exitUp"), pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.03f);
        setRight(worldBounds.getRight() - 0.03f);
    }


    @Override
    protected void actionPerformed() {
        Gdx.app.exit();
    }
}