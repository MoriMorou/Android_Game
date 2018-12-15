package ru.mori.morou.sprite;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mori.morou.base.Sprite;

public class Text extends Sprite {

    public Text(TextureRegion text, float height) {
        super(text);
        setHeightProportion(height);
    }
}
