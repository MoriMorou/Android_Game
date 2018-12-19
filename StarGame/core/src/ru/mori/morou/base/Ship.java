package ru.mori.morou.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.pool.BulletPool;
import ru.mori.morou.pool.ExplosionPool;
import ru.mori.morou.sprite.Bullet;
import ru.mori.morou.sprite.Explosion;

/**
 * This class is responsible for all the graphics in the game
 */

public class Ship extends Sprite {

    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;

    protected Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected boolean clearVisionForShoot = true;

    protected int hp;

    public Ship(TextureRegion region, int rows, int cols, int frames, Sound shootSound) {
        super(region, rows, cols, frames);
        this.shootSound = shootSound;
    }

    public Ship(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, Sound sound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.shootSound = sound;
    }

    public Ship() {

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            if (frame < regions.length - 2) {
                frame++;
            } else {
                frame = 0;
            }
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion , pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        shootSound.play();
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            setDestroyed(true);
            boom();
        }
        damageAnimateTimer = 0f;
        frame = regions.length - 1;
    }

    public int getHp() {
        return hp;
    }
}
