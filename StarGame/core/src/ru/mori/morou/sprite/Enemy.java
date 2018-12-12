package ru.mori.morou.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Ship;
import ru.mori.morou.pool.BulletPool;

public class Enemy extends Ship {

    private MainShip mainShip;

    private Rect worldBounds;


    private Vector2 v0 = new Vector2();
    private Vector2 VV = new Vector2(0, -0.25f);

    public Enemy(BulletPool bulletPool, MainShip mainShip, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        this.v0.set(v0);
        this.v.set(VV);
        this.sound = sound;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getTop() < worldBounds.getTop()- getHalfHeight() / 2){
            v.set(v0);
            reloadTimer += delta;
            if (reloadTimer >= reloadInternal) {
                reloadTimer = 0f;
                shoot();
            }
        }
        if (getBottom() <= worldBounds.getBottom()) {
            this.setDestroyed(true);
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
            int hp) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInternal = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        v.set(v0);
    }

    @Override
    protected void shoot() {
        super.shoot();
    }
}
