package ru.mori.morou.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.pool.BulletPool;
import ru.mori.morou.sprite.Bullet;

/**
 * This class is responsible for all the graphics in the game
 */

public class Ship extends Sprite {

    protected Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected final Vector2 bulletV = new Vector2();

    protected Sound shootSound;

//    public Ship(BulletPool bulletPool, Rect worldBounds) {
//        this.bulletPool = bulletPool;
//        this.worldBounds = worldBounds;
//    }
//    public Ship(TextureRegion region, int rows, int columns, int frames) {
//        super(region, rows, columns, frames);
//
//    }

    public Ship(TextureRegion region, int rows, int columns, int frames, Rect worldBounds) {
        super(region, rows, columns, frames);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, worldBounds);
        shootSound.play();
    }
}