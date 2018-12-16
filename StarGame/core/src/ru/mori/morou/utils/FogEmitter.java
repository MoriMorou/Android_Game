package ru.mori.morou.utils;

import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.Marh.Rnd;
import ru.mori.morou.pool.FogPool;
import ru.mori.morou.sprite.space_bodies.Fog;

public class FogEmitter {

    private float generateInterval = 20f;
    private float generateTimer;
    private Rect worldBounds;

    private float height;
    private Vector2 v0;

    private FogPool fogPool;

    public FogEmitter(FogPool fogPool, Rect worldBounds) {
        this.fogPool = fogPool;
        this.worldBounds = worldBounds;
    }

    public void generateFog(float delta) {
        v0 = new Vector2(0f, Rnd.nextFloat(-0.2f, -0.05f));
        height = Rnd.nextFloat(0.3f, 0.7f);
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Fog fog = fogPool.obtain();
            fog.setBottom(worldBounds.getTop());
            fog.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + fog.getHalfWidth(),
                    worldBounds.getRight() - fog.getHalfWidth());
            fog.set(v0, height);
        }
    }
}
