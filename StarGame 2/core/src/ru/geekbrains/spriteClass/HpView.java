package ru.geekbrains.spriteClass;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.baseClass.Sprite;
import ru.geekbrains.math.Rect;

public class HpView extends Sprite {

    public static final float HIGHT = 0.03f;
    private MainShip mainShip;
    private int hp;
    private Rect worldBounds;

    public HpView(TextureRegion region, int rows, int columns, int frames, MainShip mainShip, Rect worldBounds) {
        super(region, rows, columns, frames);
        setHeight(HIGHT);
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
    }

    @Override
    public void resize(Rect worldBounds) {
        setLeft(worldBounds.getLeft() + 0.05f);
        setTop(worldBounds.getTop() - 0.005f);
    }

    @Override
    public void update(float delta) {
        hp = mainShip.getHp();
        super.update(delta);
        if (hp > 135) {
            frame = 3;
            changeHpView(hp);
        } else if (hp > 69) {
            frame = 2;
            changeHpView(hp);
        } else if (hp > 34) {
            frame = 1;
            changeHpView(hp);
        } else {
            frame = 0;
            changeHpView(hp);
        }
    }

    private void changeHpView(int hp) {
        if (hp > 135) {
            setLeft(worldBounds.getLeft() + 0.05f);
            setWidth(0.005f * 135);
        } else {
            setLeft(worldBounds.getLeft() + 0.05f);
            setWidth(0.005f * hp);
        }
    }
}
