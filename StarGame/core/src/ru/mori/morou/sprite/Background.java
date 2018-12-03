package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Sprite;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
