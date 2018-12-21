package ru.geekbrains.poolClass;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.baseClass.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.spriteClass.Fog;

public class FogPool extends SpritesPool<Fog> {

    private TextureRegion textureRegion;
    private Rect worldBounds;

    public FogPool(TextureAtlas atlas, Rect worldBounds) {
        this.textureRegion = atlas.findRegion("fog");
        this.worldBounds = worldBounds;
    }

    @Override
    protected Fog newObject() {
        return new Fog(textureRegion, 5,5,25, worldBounds);
    }
}
