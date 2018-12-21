package ru.geekbrains.models.asteroids;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;

import ru.geekbrains.engine.Sprite;
import ru.geekbrains.engine.math.Rnd;
import ru.geekbrains.models.actor.Hero;
import ru.geekbrains.screens.GameScreen;

public class Asteroid extends Sprite {
    public static ArrayList<Asteroid> activeAsteroid = new ArrayList<Asteroid>();

    private Animation runningAnimation;
    private Animation explosionAnimation;
    private float elapsedTime;
    private float elapsedExplosionTime;
    private TextureAtlas atlas;
    private TextureAtlas explosion;
    private TextureRegion region;
    private Vector2 speed;
    private float speedRorate;
    private float rorate;
    private float size;
    private float positiveOrNegative;
    private boolean whole;
    private boolean createSplinter;
    private boolean[] crashOnScreen;
    private boolean stopAnimation;
    private Asteroid[] crash;
    private Sound chushSound;

    public Asteroid(TextureAtlas atlas, TextureAtlas explosion, TextureRegion region, float positionX, float positionY, float speedX, float speedY, float size, float speedRorate, float rorate, Sound chushSound) {
        super(region);
        this.chushSound = chushSound;
        this.atlas = atlas;
        this.explosion = explosion;
        this.region = region;
        this.runningAnimation = new Animation(speedRorate,this.atlas.findRegions("asteroid"));
        this.elapsedTime = 0f;

        this.explosionAnimation = new Animation(0.05f ,this.explosion.findRegions("explosion"));
        this.elapsedExplosionTime = 20f;

        setTop(positionY);
        setLeft(positionX);

        this.speed = new Vector2();
        this.speed.set(speedX, speedY);

        setHeightProportion(size);
        this.size = size;

        this.speedRorate = speedRorate;
        this.rorate = rorate;

        this.whole = true;
        this.createSplinter = false;
        this.crashOnScreen = new boolean[8];
        this.stopAnimation = false;
        System.out.println("Size (Height proportion) = " + size);
        System.out.println("Real size = " + this.getHeight());
    }

    public void update(float delta, Hero hero) {
        if (isDestroyed) {
            return;
        }
        if (whole) {
            pos.mulAdd(speed, delta);
            angle += rorate;
            if (pos.y > 0.6f) {
                return;
            } else {
                activeAsteroid.add(this);
            }
            if (pos.y < -0.7f || pos.x < -GameScreen.WIDTH_SCREEN || pos.x > GameScreen.WIDTH_SCREEN) {
                activeAsteroid.remove(this);
                this.setDestroyed(true);
                return;
            }
            if (hero.spaceShipTouchNose(pos, getHalfHeight()-0.1f*getHalfHeight()) && this.getHeight()>=0.03f) {
                setWhole(false);
            }
            if (elapsedTime < 25f ) {
                elapsedTime += delta;
            } else {
                elapsedTime = 0f;
            }
        } else {
            if (pos.y>-0.7) {
                pos.mulAdd(speed.set(0,-0.06f),delta);
            } else {
                setDestroyed(true);
            }

        }
    }

    public void draw(SpriteBatch batch, float delta, Hero hero) {
        if (whole) {
            batch.draw(
                    (TextureRegion) runningAnimation.getKeyFrame(elapsedTime, true), // текущий регион
                    getLeft(), getTop(), // точка отрисовки
                    halfWidth, halfHeight, // точка вращения
                    getWidth(), getHeight(), // ширина и высота
                    scale, scale, // масштаб по x и y
                    angle // угол вращения
            );
        } else {
            if (elapsedExplosionTime>0.01f) {
                elapsedExplosionTime-=delta;
            } else {
                stopAnimation=true;
            }
            if (!stopAnimation) {
                batch.draw(
                        (TextureRegion) explosionAnimation.getKeyFrame(elapsedExplosionTime, true), // текущий регион
                        getLeft(), getTop(), // точка отрисовки
                        halfWidth, halfHeight, // точка вращения
                        getWidth(), getHeight(), // ширина и высота
                        2f, 2f, // масштаб по x и y
                        angle // угол вращения
                );
            }
            createSplinter();
            for (int i = 0; i < crash.length; i++) {
                if (crashOnScreen[i]) {
                    crash[i].update(delta, hero);
                    crash[i].draw(batch, delta, hero);
                }
                if (crash[i].pos.x> GameScreen.WIDTH_SCREEN/2 || crash[i].pos.x<-GameScreen.WIDTH_SCREEN/2 || crash[i].pos.y<-0.5f || crash[i].pos.y > 0.5f) {
                    crashOnScreen[i]=false;
                }
            }
        }
    }

    public void createSplinter() {
        if (!createSplinter) {
            crash = new Asteroid[8];
            for (int i = 0; i < crash.length; i++) {
                crash[i] = new Asteroid(atlas, explosion, region, pos.x, pos.y,
                        Rnd.nextFloat(0.1f, 0.4f) * Rnd.positiveOrNegative(),
                        speed.y * Rnd.nextFloat(0.2f, 0.8f) * Rnd.positiveOrNegative(),
                        size * Rnd.nextFloat(0.1f, 0.5f),
                        Rnd.nextFloat(0.03f, 0.06f),
                        Rnd.nextFloat(0f, 2f), chushSound);
            }
            createSplinter = true;
            for (int i = 0; i < crashOnScreen.length; i++) {
                crashOnScreen[i] = true;
            }
        }
    }
    public void setWhole(boolean whole) {
        chushSound.play(0.05f);
        this.whole = whole;
    }
    public boolean getWhole() {
        return this.whole;
    }
}
