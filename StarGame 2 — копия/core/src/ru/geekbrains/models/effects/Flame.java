package ru.geekbrains.models.effects;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import ru.geekbrains.engine.Sprite;
import ru.geekbrains.models.actor.Hero;

public class Flame extends Sprite {

    private Animation runningAnimation;
    private float elapsedTime;
    private TextureAtlas atlas;

    public Flame(TextureAtlas atlas, TextureRegion region) {
        super(region);
        this.atlas = atlas;
        this.elapsedTime=63f;
        this.runningAnimation = new Animation(0.013f,this.atlas.findRegions("explosion"));
    }

    public void draw(SpriteBatch batch, Hero hero, float delta) {
        if (this.elapsedTime>0.5f) {
            this.elapsedTime-=delta;
        } else {
            this.elapsedTime=63f;
        }
        batch.draw(
                (TextureRegion) this.runningAnimation.getKeyFrame(this.elapsedTime, true), // текущий регион
                hero.pos.x-0.025f, hero.pos.y-hero.getHalfHeight() - 0.05f, // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                0.05f, 0.05f, // ширина и высота
                scale, scale, // масштаб по x и y
                angle // угол вращения
        );
    }

}
