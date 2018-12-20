package ru.mori.morou.pool;


import com.badlogic.gdx.audio.Sound;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.SpritesPool;
import ru.mori.morou.sprite.Enemy;
import ru.mori.morou.sprite.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;
    private Rect worldBounds;
    private Sound shootSound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, MainShip mainShip,
                     Rect worldBounds, Sound shootSound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, mainShip, worldBounds, shootSound);
    }
}
