package ru.mori.morou.sprite.space_bodies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Sprite;
import ru.mori.morou.sprite.MainShip;

public class Fog extends Sprite {

    private float animateInterval = 0.5f;
    private float animateTimer;

    private Vector2 v;
    private Rect worldBounds;

    public Fog(TextureRegion region, int rows, int columns, int frames, Rect worldBounds) {
        super(region, rows, columns, frames);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;
            }
        }

        if (getTop() <= worldBounds.getBottom())
            destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void set(Vector2 v, float height) {
        setHeightProportion(height);
        this.v = v;
    }

//    public void isFogWithShipCollision(MainShip ship) {
//        if (!(ship.getRight() < getLeft() + getHalfWidth()
//                || ship.getLeft() > getRight() - getHalfWidth()
//                || ship.getBottom() > getTop() - (getHalfHeight() + getHalfHeight()/4)
//                || ship.getTop() < getBottom() + (getHalfHeight() + getHalfHeight()/4))) {
//            ship.setClearVisionForShoot(false);
//        } else {
//            ship.setClearVisionForShoot(true);
//        }
//    }
}