package ru.geekbrains.poolClass;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.baseClass.SpritesPool;
import ru.geekbrains.spriteClass.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private Sound sound;

    private TextureRegion textureRegion;

    public ExplosionPool(TextureAtlas atlas, Sound sound) {
        this.textureRegion = atlas.findRegion("explosion");
        this.sound = sound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 9,9,74, sound);
    }
}