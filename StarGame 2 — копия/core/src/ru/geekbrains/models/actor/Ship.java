package ru.geekbrains.models.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.engine.Sprite;
import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.models.bullets.Bullet;
import ru.geekbrains.models.bullets.BulletPool;
import ru.geekbrains.models.effects.explosions.Explosion;
import ru.geekbrains.models.effects.explosions.ExplosionPool;

public abstract class Ship extends Sprite {

    protected final Vector2 v = new Vector2(); // скорость корабля
    protected Rect worldBounds; // границы мира
    protected int hp; // жизни корабля

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;


    protected final Vector2 bulletV = new Vector2(); // скорость пули
    protected float bulletHeight; // высота пули
    protected  int bulletDamage; // урон
    protected boolean bulletEnemy;

    protected float reloadInterval; // время перезарядки
    protected float reloadTimer; // таймер для стрельбы

    public Ship(TextureRegion region, ExplosionPool explosionPool) {
        super(region);
        this.explosionPool = explosionPool;
    }

    public Ship() {
    }

    public void shoot() {
        if (reloadTimer >= reloadInterval) {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage, bulletEnemy);
            reloadTimer = 0f;
        }
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void boom() {
        Explosion explosion = this.explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

}