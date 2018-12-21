package ru.geekbrains.models.effects.explosions;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.Sprite;

public class Explosion extends Sprite {
    private static float animationSpeed = 0.033f;

    private Sound sound;
    private TextureAtlas atlas;
    private Animation runningAnimation;
    private float elapsedTime;

    public Explosion(TextureAtlas atlas, Sound sound) {
        super(atlas.getRegions().first());
        this.atlas = atlas;
        this.runningAnimation = new Animation(animationSpeed,this.atlas.findRegions("explosion"));
        this.sound = sound;
        this.atlas = atlas;
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
        sound.play();
    }

    @Override
    public void update(float delta) {
        if (elapsedTime<1f) {
            elapsedTime+=0.01f;
        } else {
            elapsedTime=0;
            setDestroyed(true);
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                (TextureRegion) runningAnimation.getKeyFrame(elapsedTime, false), // текущий регион
                pos.x -halfHeight, pos.y - halfHeight, // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(), // ширина и высота
                scale, scale, // масштаб по x и y
                angle // угол вращения
        );
    }

}
