package ru.geekbrains.models.actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.models.bullets.BulletPool;
import ru.geekbrains.models.effects.explosions.ExplosionPool;
import ru.geekbrains.screens.GameScreen;

public class Hero extends Ship {
    public boolean pressedLeft = false;
    public boolean pressedRight = false;
    private float x, y;
    private Vector2 endPoint;
    private Vector2 direction;
    private float speed;
    private float normalizeY;
    private Vector2 nose;
    private Vector2 leftWing;
    private Vector2 rigthWing;
    private Vector2 halfLeftWing;
    private Vector2 halfRigthWing;
    private TextureRegion bullet = new TextureRegion(new Texture("bullet_lite.png"));

    public Hero (TextureRegion region, ExplosionPool explosionPool, float startX, float startY,
                 float speed, BulletPool bulletPool) {
        super(region, explosionPool);
        setBottom(-0.3f);
        setHeightProportion(0.15f);
        this.endPoint = new Vector2(this.pos);
        this.direction = new Vector2(this.pos);
        this.speed = speed;
        this.nose = new Vector2();
        this.leftWing = new Vector2();
        this.rigthWing = new Vector2();
        this.halfLeftWing = new Vector2();
        this.halfRigthWing = new Vector2();
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletRegion = bullet;
        this.bulletHeight = 0.02f;
        this.bulletV.set(0, 0.5f);
        this.bulletDamage = 1;
        this.bulletEnemy = false;
        this.reloadInterval = 0.2f;
        setHp(100);
    }

    public void setToNewGame() {
        this.setBottom(-0.3f);
        this.pos.x=0;
        this.bulletHeight = 0.02f;
        this.bulletV.set(0, 0.5f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.2f;

        hp = 100;
        setDestroyed(false);
    }

    @Override
    public void update(float delta) {
        if (!isDestroyed) {
            if (hp <= 0) {
                boom();
                setDestroyed(true);
            }
            reloadTimer += delta;
            if (!this.pos.epsilonEquals(this.endPoint, 0.005f)) {
                pos.mulAdd(direction.nor().scl(this.speed), delta);
            } else {
                pos.set(this.endPoint);
            }
            if (this.pos.y >= -0.3f) {
                this.pos.y = -0.3f;
                normalizeY = 0;
            }
            if (this.pos.y < -0.3f) {
                normalizeY += 0.01f;
            }
            if (normalizeY > 0.50f) {
                this.pos.y += 0.001f;
            }
        }
    }

    public boolean spaceShipTouchNose (Vector2 obj, float height) {
        nose.set(pos.cpy().add(0,getHalfHeight()-height));
        leftWing.set(pos.cpy().add((getHalfWidth()/(-1.5f)),0));
        rigthWing.set(pos.cpy().add(getHalfWidth()/1.5f,0));
        halfLeftWing.set(pos.cpy().add(getHalfWidth()/3f,0));
        halfRigthWing.set(pos.cpy().add(getHalfWidth()/(-3f),0));

        if (nose.epsilonEquals(obj.cpy().add(0, height), height-(height*0.4f)) ||
                leftWing.epsilonEquals(obj.cpy().add(0, height), height-(height*0.4f)) ||
                rigthWing.epsilonEquals(obj.cpy().add(0, height), height-(height*0.4f)) ||
                halfLeftWing.epsilonEquals(obj.cpy().add(0, height), height-(height*0.4f)) ||
                halfRigthWing.epsilonEquals(obj.cpy().add(0, height), height-(height*0.4f)) ||
                pos.epsilonEquals(obj.cpy().add(0, height+(height/3)), height)) {
            this.setEndPoint(this.pos.x, this.pos.cpy().y-0.05f);
            this.hp-=(int) 15*height;
            return true;
        }
        return false;
    }

    public void setEndPoint(Vector2 endPoint) {
        x = endPoint.x;
        y = endPoint.y;
         if (x < GameScreen.WIDTH_SCREEN/2-GameScreen.WIDTH_SCREEN/12 && x > -GameScreen.WIDTH_SCREEN/2+GameScreen.WIDTH_SCREEN/12) {
        } else {
            if (x > 0) {
                x = GameScreen.WIDTH_SCREEN/2-GameScreen.WIDTH_SCREEN/12;
            } else {
                x = -GameScreen.WIDTH_SCREEN/2+GameScreen.WIDTH_SCREEN/12;
            }
        }
        if (y < 0.42f && y > -0.42f) {
        } else {
            if (endPoint.y > 0) {
                y = 0.42f;
            } else {
               y = -0.42f;
            }
        }
        this.endPoint.set(x,y);
        this.direction = this.endPoint.cpy().sub(this.pos);
    }

    public void setEndPoint(float endPositionX, float endPositionY) {
        x = endPositionX;
        y = endPositionY;

        if (x < GameScreen.WIDTH_SCREEN/2-GameScreen.WIDTH_SCREEN/12 && x > -GameScreen.WIDTH_SCREEN/2+GameScreen.WIDTH_SCREEN/12) {
        } else {
            if (x > 0) {
                x = GameScreen.WIDTH_SCREEN/2-GameScreen.WIDTH_SCREEN/12;
            } else {
                x = -GameScreen.WIDTH_SCREEN/2+GameScreen.WIDTH_SCREEN/12;
            }
        }
        if (y < 0.42f && y > -0.42f) {
        } else {
            if (endPoint.y > 0) {
                y = 0.42f;
            } else {
                y = -0.42f;
            }
        }
        this.endPoint.set(x,y);
        this.direction = this.endPoint.cpy().sub(this.pos);
    }

    public void stopPoint() {
        this.direction = pos.cpy().set(0,0);
    }
    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
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
        }
    }


    private void moveRight() {
        setEndPoint(pos.cpy().add(GameScreen.WIDTH_SCREEN,0));
    }

    private void moveLeft() {
        setEndPoint(pos.cpy().add(-GameScreen.WIDTH_SCREEN,0));
    }

    private void stop() {
        setEndPoint(pos);
    }
}

