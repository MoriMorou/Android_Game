package ru.geekbrains.models.enemys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.models.actor.Ship;
import ru.geekbrains.models.bullets.BulletPool;
import ru.geekbrains.models.effects.explosions.ExplosionPool;
import ru.geekbrains.screens.GameScreen;

public class EnemyShip extends Ship {
    private TextureRegion bullet = new TextureRegion(new Texture("bullet_lite2.png"));
    private float startY = 0.6f;
    public int damage;

    public EnemyShip() {
        regions = new TextureRegion[1];
        this.bulletRegion = bullet;
        this.bulletHeight = 0.02f;
        this.bulletV.set(0, -0.3f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.8f;
        this.bulletEnemy = true;
        this.damage = 5;
    }

    public void set(
            TextureRegion region,
            ExplosionPool explosionPool,
            BulletPool bulletPool,
            float startX,
            float speed
    ) {
        super.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        regions[0] = region;
        setHeightProportion(0.15f);
        pos.set(startX, startY);
        v.set(0,speed);
        this.hp = 3;
    }

    @Override
    public void update(float delta) {
        reloadTimer += delta;
        this.pos.mulAdd(v, delta);
        this.shoot();
        if (this.pos.y<-0.6f) {
            setDestroyed(true);
        }
        if (this.getHp()<=0) {
            super.boom();
            GameScreen.frags+=1;
            setDestroyed(true);
        }
    }


}
