package ru.geekbrains.baseClass;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.poolClass.BulletPool;
import ru.geekbrains.poolClass.ExplosionPool;
import ru.geekbrains.spriteClass.Bullet;
import ru.geekbrains.spriteClass.Explosion;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected final Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected boolean clearVisionForShoot = true;

    protected int hp;

    // для врагов
    public Ship(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, Sound sound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.shootSound = sound;
    }

    // главный корабль
    public Ship(TextureRegion region, int rows, int columns, int frames, Rect worldBounds, Sound sound) {
        super(region, rows, columns, frames);
        this.worldBounds = worldBounds;
        this.shootSound = sound;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            if (frame < regions.length - 1) {
                frame++;
            } else {
                frame = 0;
            }
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
            shootSound.play();
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight() * 1.5f, pos);
        hp = 0;
    }

    public void damage(int damage) {
        frame = regions.length - 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            boom();
            destroy();
        }
    }

    public int getHp() {
        return hp;
    }

    public boolean isAsteroidCollision(Rect asteroid) {
        return !(getLeft() > (asteroid.getRight() - getHalfWidth()/2)
                || getRight() < (asteroid.getLeft() + getHalfWidth()/2)
                || getTop() < (asteroid.getBottom() + getHalfHeight()/2)
                || getBottom() > (asteroid.getTop() - getHalfHeight()/2)
        );
    }
}
