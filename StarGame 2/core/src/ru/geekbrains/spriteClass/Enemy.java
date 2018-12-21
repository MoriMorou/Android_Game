package ru.geekbrains.spriteClass;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.baseClass.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.poolClass.BulletPool;
import ru.geekbrains.poolClass.ExplosionPool;

public class Enemy extends Ship {

    private enum State {DESCENT, FIGHT}

    private MainShip mainShip;

    private State state;

    private Vector2 v0 = new Vector2();
    private Vector2 descentV = new Vector2(0, -0.25f);

    private int boomDamagePower;

    public Enemy(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool,
                 MainShip mainShip, Sound sound) {
        super(bulletPool, worldBounds, explosionPool, sound);
        this.v.set(v0);
        this.state = State.DESCENT;
        this.v.set(descentV);
        this.mainShip = mainShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);

        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop() - getHalfHeight() / 2) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()) {
                    mainShip.damage(bulletDamage);
                    boom();
                    destroy();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp,
            int boomDamagePower
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        this.v.set(descentV);
        this.state = State.DESCENT;
        this.hp = hp;
        this.boomDamagePower = boomDamagePower;
    }

    @Override
    protected void shoot() {
        super.shoot();
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }

    public int getBoomDamagePower() {
        return boomDamagePower;
    }
}
