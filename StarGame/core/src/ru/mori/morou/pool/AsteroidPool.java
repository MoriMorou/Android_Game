package ru.mori.morou.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.SpritesPool;
import ru.mori.morou.sprite.space_bodies.Asteroid;


public class AsteroidPool extends SpritesPool<Asteroid> {

    private TextureRegion textureRegion;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public AsteroidPool(TextureAtlas atlas, Rect worldBounds, ExplosionPool explosionPool) {
        this.textureRegion = atlas.findRegion("asteroid");
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid(textureRegion, 2,8,16, worldBounds, explosionPool);
    }
}