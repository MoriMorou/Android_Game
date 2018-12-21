package ru.geekbrains.spriteClass;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.baseClass.Sprite;
import ru.geekbrains.math.Rect;

public class GameMessage extends Sprite {

    float bottomMargin;

    public GameMessage(TextureRegion region, float height, float bottomMargin) {
        super(region);
        setHeightProportion(height);
        this.bottomMargin = bottomMargin;
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(bottomMargin, 0);
    }
}