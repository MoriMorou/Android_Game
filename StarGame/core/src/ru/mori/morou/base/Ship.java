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

    //вектор скорости
    protected Vector2 v = new Vector2();
    //рамки
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected final Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;
    protected int hp;

    protected float reloadInternal;
    protected float reloadTimer;

    protected Sound sound;

    // для врагов
    public Ship() {

    }

    // для основного корабля
    public Ship(TextureRegion region, int rows, int columns, int frames, Rect worldBounds, Sound sound) {
        super(region, rows, columns, frames);
        this.worldBounds = worldBounds;
        this.sound = sound;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        sound.play();
    }
}