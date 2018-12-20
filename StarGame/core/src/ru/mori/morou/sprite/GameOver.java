package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mori.morou.base.Sprite;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(0.07f);
        setBottom(0.009f);
    }
}
