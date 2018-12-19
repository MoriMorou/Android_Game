package ru.mori.morou.sprite;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Ship;
import ru.mori.morou.pool.BulletPool;
import ru.mori.morou.pool.ExplosionPool;


/**
 * @param
 */


public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;

    private Vector2 v0 = new Vector2(0.5f, 0);

    private boolean pressedLeft;
    private boolean pressedRight;
    private boolean pressedUp;
    private boolean pressedDown;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(atlas.findRegion("mainship"), 1, 8, 8, sound);
        setHeightProportion(0.2f);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.reloadInterval = 0.4f;
        this.bulletRegion = atlas.findRegion("laserBlue01");
        this.bulletHeight = 0.05f;
        this.bulletV.set(0, 0.8f);
        this.bulletDamage = 1;
        this.worldBounds = worldBounds;
        setToNewGame();
    }

    public void setToNewGame() {
        stop();
        pos.x = worldBounds.pos.x;
        this.hp = 100;
        setDestroyed(false);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
//        if (reloadTimer >= reloadInterval) {
//            reloadTimer = 0f;
//            shoot();
//        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if (getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
            stop();
        }
        if (getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
            stop();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.D:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                pressedUp = true;
                moveUp();
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                pressedDown = true;
                moveDown();
                break;
            case Input.Keys.SPACE:
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                pressedUp = false;
                if (pressedDown) {
                    moveDown();
                } else {
                    stop();
                }
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                pressedDown = false;
                if (pressedUp) {
                    moveUp();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x){
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }


    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void moveUp() {
        v.set(v0).rotate(90);
    }


    private void moveDown() {
        v.set(v0).rotate(-90);
    }

    private void stop() {
        v.setZero();
    }

    @Override
    protected void shoot() {
        if(clearVisionForShoot) {
            super.shoot();
        }
    }

    public void setClearVisionForShoot(boolean clearVisionForShoot) {
        this.clearVisionForShoot = clearVisionForShoot;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }
}
