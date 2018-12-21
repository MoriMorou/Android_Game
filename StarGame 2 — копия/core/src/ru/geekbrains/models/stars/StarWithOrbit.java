package ru.geekbrains.models.stars;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.Sprite;
import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.math.Rnd;


public class StarWithOrbit extends Sprite {

    private Vector2 v = new Vector2();
    private float startPos;
    private float speed;
    private Rect worldBounds;
    private Vector2 orbit = new Vector2();
    float alpha;


    public StarWithOrbit(TextureRegion region,
                         float speed, //
                         float orditX, float orditY,
                         float startPos, float size) {
        super(region);
        setHeightProportion(size);
        this.speed = speed;
        this.startPos = startPos;
        this.orbit.x = orditX; this.orbit.y = orditY;
        this.alpha += this.startPos;
    }
    @Override
    public void update(float delta) {
        this.alpha -= delta*this.speed;
        this.v.x = this.orbit.x*(float)Math.cos(this.alpha);
        this.v.y = this.orbit.y*(float)Math.sin(this.alpha);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame], // текущий регион
                v.x, v.y, // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(), // ширина и высота
                scale, scale, // масштаб по x и y
                angle // угол вращения
        );
    }

    protected void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }
}
