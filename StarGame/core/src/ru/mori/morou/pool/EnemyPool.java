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
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, MainShip mainShip,
                     Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, mainShip, worldBounds, sound);
    }
}