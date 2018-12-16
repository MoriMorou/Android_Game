package ru.mori.morou.utils;

import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.Marh.Rnd;
import ru.mori.morou.pool.AsteroidPool;
import ru.mori.morou.sprite.space_bodies.Asteroid;

public class AsteroidEmitter {

    private float generateInterval = 4f;
    private float generateTimer;
    private Rect worldBounds;

    private int stage = 1;
    private int masAtackSage = 2;

    private Vector2 v0;

    private AsteroidPool asteroidPool;

    public AsteroidEmitter(AsteroidPool asteroidPool, Rect worldBounds) {
        this.asteroidPool = asteroidPool;
        this.worldBounds = worldBounds;
    }

    public void generateAsteroid(float delta, int frags) {
        stage = frags / 10 + 1;

        if(stage == masAtackSage){
            for (int i = 0; i < 7; i++) {
                Asteroid asteroid = asteroidPool.obtain();
                asteroid.setHeightProportion(Rnd.nextFloat(0.04f, 0.16f));
                asteroid.setBottom(worldBounds.getTop() + 0.3f);
                asteroid.pos.x = Rnd.nextFloat(
                        worldBounds.getLeft() + asteroid.getHalfWidth(),
                        worldBounds.getRight() - asteroid.getHalfWidth());
                v0 = new Vector2(0f, Rnd.nextFloat(-0.9f, -0.2f));
                asteroid.setV(v0);
            }
            masAtackSage += 5;
        }

        v0 = new Vector2(0f, Rnd.nextFloat(-1.3f, -0.4f));
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Asteroid asteroid = asteroidPool.obtain();
            asteroid.setBottom(worldBounds.getTop());
            asteroid.setHeightProportion(Rnd.nextFloat(0.04f, 0.12f));
            asteroid.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + asteroid.getHalfWidth(),
                    worldBounds.getRight() - asteroid.getHalfWidth());
            asteroid.setV(v0);
        }
    }
}
