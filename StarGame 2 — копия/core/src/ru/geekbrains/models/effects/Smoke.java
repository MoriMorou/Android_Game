package ru.geekbrains.models.effects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.engine.Sprite;
import ru.geekbrains.engine.math.Rect;

public class Smoke extends Sprite {
    private float speed;

    public Smoke(TextureRegion region, float speed, float position) {
        super(region);
        this.speed = speed;
        setTop(position);
        this.pos.y = position;
    }
    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
    }

    @Override
    public void update(float delta) {
        if (this.pos.y+0.5f>-0.5f) {
            this.pos.y -= speed;
        } else {
            setBottom(0.5f);
        }

    }

}
