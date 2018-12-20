package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.base.ButtonAction;
import ru.mori.morou.screen.GameScreen;

public class StartNewGame extends ButtonAction {

    private GameScreen gameScreen;

    public StartNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
        setHeightProportion(0.05f);
        setTop(-0.012f);
    }

    @Override
    protected void actionPerformed() {
        gameScreen.startNewGame();
    }
}
