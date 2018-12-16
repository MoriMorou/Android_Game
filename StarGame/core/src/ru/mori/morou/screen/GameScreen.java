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
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.mori.morou.Marh.Rect;
import ru.mori.morou.base.Base2DScreen;
import ru.mori.morou.base.Font;
import ru.mori.morou.pool.BulletPool;
import ru.mori.morou.pool.EnemyPool;
import ru.mori.morou.pool.ExplosionPool;
import ru.mori.morou.sprite.BackgroundGame;
import ru.mori.morou.sprite.BackgroundMenu;
import ru.mori.morou.sprite.Bullet;
import ru.mori.morou.sprite.Enemy;
import ru.mori.morou.sprite.GameOver;
import ru.mori.morou.sprite.MainShip;
import ru.mori.morou.sprite.space_bodies.Star;
import ru.mori.morou.sprite.StartNewGame;
import ru.mori.morou.utils.EnemiesEmitter;

/**
 * @method checkCollisions - проверка столкновений
 */
public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture bg;
    private TextureAtlas textureAtlas;
    private TextureAtlas enemiesAtlas;
    private TextureAtlas heroAtlas;

    private BackgroundGame backgroundGame;

    private Star[] star;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemiesEmitter enemiesEmitter;

    private Music music;
    private Sound mainShipShootSound;
    private Sound enemyShipShootSound;
    private Sound explosionSound;

    private GameOver gameOver;
    private StartNewGame startNewGame;

    private enum State {PLAYING, GAME_OVER}

    private State state;

    private int frags;

    private Font font;

    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/Doom4  - BFG Division (Simpsonill Remix).mp3"));
        music.setLooping(true);
        music.play();
        textureAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        heroAtlas = new TextureAtlas("ship/mainship/hero.tpack");
        enemiesAtlas = new TextureAtlas("textures/enemies.atlas");
        bg = new Texture("textures/bg_level_2.png");
        backgroundGame = new BackgroundGame(new TextureRegion(bg), -0.5f);
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(textureAtlas);
        }
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
        explosionPool = new ExplosionPool(textureAtlas, explosionSound);
        mainShipShootSound = Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav"));
        mainShip = new MainShip(heroAtlas, bulletPool, explosionPool, worldBounds, mainShipShootSound);
        enemyShipShootSound = Gdx.audio.newSound(Gdx.files.internal("sound/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool, explosionPool, mainShip, worldBounds, enemyShipShootSound);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyPool, enemiesAtlas);
        gameOver = new GameOver(textureAtlas);
        startNewGame = new StartNewGame(textureAtlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(FONT_SIZE);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        update(delta);
        if (state == State.PLAYING) {
            checkCollisions();
        }
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state) {
            case PLAYING:
                if (!mainShip.isDestroyed()) {
                    mainShip.update(delta);
                }
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemiesEmitter.generate(delta, frags);
                break;
            case GAME_OVER:
                break;
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.setDestroyed(true);
                enemy.boom();
                mainShip.damage(mainShip.getHp());
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                bullet.setDestroyed(true);
                mainShip.damage(bullet.getDamage());
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundGame.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        switch (state) {
            case PLAYING:
                if (!mainShip.isDestroyed()) {
                    mainShip.draw(batch);
                }
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                break;
            case GAME_OVER:
                gameOver.draw(batch);
                startNewGame.draw(batch);
                break;
        }
        printInfo();
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        backgroundGame.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        textureAtlas.dispose();
        heroAtlas.dispose();
        enemiesAtlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        mainShipShootSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchDown(touch, pointer);
                break;
            case GAME_OVER:
                startNewGame.touchDown(touch, pointer);
                break;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchUp(touch, pointer);
                break;
            case GAME_OVER:
                startNewGame.touchUp(touch, pointer);
                break;
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    public void startNewGame() {
        state = State.PLAYING;

        frags = 0;
        mainShip.setToNewGame();

        enemiesEmitter.setToNewGame();

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}