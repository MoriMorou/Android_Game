package ru.mori.morou.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.ButtonAction;
import ru.mori.morou.screen.GameScreen;


public class ButtonStart extends ButtonAction {

    private Game game;

    public ButtonStart(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("startUp"));
        setHeightProportion(0.03f);
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.01f);
        setLeft(worldBounds.getLeft() + 0.01f);
    }

    @Override
    protected void actionPerformed() {
        game.setScreen(new GameScreen(game));
    }
}

