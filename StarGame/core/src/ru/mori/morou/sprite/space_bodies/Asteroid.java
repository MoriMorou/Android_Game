package ru.mori.morou.sprite.space_bodies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Sprite;
import ru.mori.morou.pool.ExplosionPool;
import ru.mori.morou.sprite.Bullet;
import ru.mori.morou.sprite.Explosion;

public class Asteroid extends Sprite {

    private ExplosionPool explosionPool;


    private int damage;
    private Rect worldBounds;
    private Vector2 v;

    private float animateInterval = 0.015f;
    private float animateTimer;

    public Asteroid(TextureRegion region, int rows, int columns, int frames, Rect worldBounds, ExplosionPool explosionPool) {
        super(region, rows, columns, frames);
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        damage = 5;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        animateTimer += delta;
        if(animateTimer >= animateInterval){
            animateTimer = 0f;
            if(frame == regions.length-1){
                frame = 0;
            }else {
                frame++;
            }
        }
        if(getTop() <= worldBounds.getBottom())
            destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight()*0.8f, pos);
    }

    public int getDamage() {
        return damage;
    }

    public void setV(Vector2 v) {
        this.v = v;
    }

    public boolean isBulletMainShipCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft() + getHalfWidth()/2
                || bullet.getLeft() > getRight() - getHalfWidth()/2
                || bullet.getTop() < getBottom());
    }

    public boolean isBulletEnemyCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft() + getHalfWidth()/2
                || bullet.getLeft() > getRight() - getHalfWidth()/2
                || bullet.getBottom() > getTop() - getHalfHeight()
                || bullet.getTop() < getBottom());
    }
}