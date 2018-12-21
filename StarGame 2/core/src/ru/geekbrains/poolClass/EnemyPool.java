package ru.geekbrains.poolClass;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.baseClass.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.spriteClass.Enemy;
import ru.geekbrains.spriteClass.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private ExplosionPool explosionPool;
    private BulletPool bulletPool;
    private Rect worldBounds;
    private MainShip mainShip;

    private Sound sound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MainShip mainShip, Sound sound){
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
        this.sound = sound;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, worldBounds, explosionPool, mainShip, sound);
    }
}