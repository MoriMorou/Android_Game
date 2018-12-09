package ru.mori.morou.screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Base2DScreen;
import ru.mori.morou.pool.BulletPool;
import ru.mori.morou.sprite.Background;
import ru.mori.morou.sprite.MainShip;
import ru.mori.morou.sprite.Star;

/**
 * @method checkCollisions - проверка столкновений
 */
public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas textureAtlas;

    private Background background;

    private Star[] star;

    private MainShip mainShip;

    private BulletPool bulletPool;

    private Music backgroundMusic;
    private Sound mainShipBulletSound;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/space5.png");
        background = new Background(new TextureRegion(bg));
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(textureAtlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(textureAtlas, bulletPool, worldBounds);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/Doom4  - BFG Division (Simpsonill Remix).mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
//        backgroundMusic.stop();
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        textureAtlas.dispose();
        bulletPool.dispose();
        backgroundMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }
}
