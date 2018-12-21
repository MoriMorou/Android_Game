package ru.geekbrains.poolClass;

import ru.geekbrains.baseClass.SpritesPool;
import ru.geekbrains.spriteClass.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    private ExplosionPool explosionPool;

    public BulletPool(ExplosionPool explosionPool) {
        this.explosionPool = explosionPool;
    }

    @Override
    protected Bullet newObject() {
        return new Bullet(explosionPool);
    }
}
