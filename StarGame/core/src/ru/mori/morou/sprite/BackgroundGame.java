package ru.mori.morou.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Sprite;

public class BackgroundGame extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    private boolean top;
    private float y;

    public BackgroundGame(TextureRegion region, float y) {
        super(region);
        this.y = y;

    }

    @Override
    public void draw(SpriteBatch batch) {
        this.y -= 0.003f;
        batch.draw(
                regions[frame], // текущий регион
                getLeft(), this.y, // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(), // ширина и высота
                scale, scale, // масштаб по x и y
                angle // угол вращения
        );
    }

    @Override
    public void resize(Rect worldBounds) {
        setWidthProportion(worldBounds.getWidth());
        pos.set(worldBounds.pos);
    }
}
