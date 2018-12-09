package ru.mori.morou.pool;

import ru.mori.morou.base.SpritesPool;
import ru.mori.morou.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
