package ru.geekbrains.models.effects.explosions;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.engine.pool.SpritesPool;

public class ExplosionMiniPool extends SpritesPool<ExplosionMini> {

    private Sound sound;
    private TextureAtlas explosionAtlas;

    public ExplosionMiniPool(TextureAtlas atlas, Sound sound) {
        explosionAtlas = atlas;
        this.sound = sound;
    }

    @Override
    protected ExplosionMini newObject() {
        return new ExplosionMini(explosionAtlas, sound);
    }
}


