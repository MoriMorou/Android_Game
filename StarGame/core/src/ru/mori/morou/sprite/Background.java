package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Sprite;

public class Background extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    private boolean top;

    public Background(TextureRegion region, boolean top) {
        super(region);
        this.top = top;
        v.set(0, -0.3f);
    }

    public Background(TextureRegion region) {
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

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
    }


}