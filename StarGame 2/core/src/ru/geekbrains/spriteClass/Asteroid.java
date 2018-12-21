package ru.geekbrains.spriteClass;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.baseClass.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.poolClass.ExplosionPool;

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