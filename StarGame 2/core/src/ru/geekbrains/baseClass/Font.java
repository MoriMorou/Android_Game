package ru.geekbrains.baseClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Font extends BitmapFont {

    public Font(String fontFile, String imgFile){
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imgFile), false, false);
    }

    public void setWorldSize(float worldSize){
        getData().setScale(worldSize / getCapHeight());
        getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int align){
        return super.draw(batch, str, x, y,0f, align, false);
    }
}
