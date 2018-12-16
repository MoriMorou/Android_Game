package ru.mori.morou.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.SpritesPool;
import ru.mori.morou.sprite.space_bodies.Fog;

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
