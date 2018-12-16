package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Sprite;

public class BackgroundMenu extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    private boolean top;
    private float y;

    // для меню
    public BackgroundMenu(TextureRegion region) {
        super(region);
        this.top = false;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
        if(top){
            setBottom(worldBounds.getTop());
        }
    }
}
