package ru.geekbrains.screenClass;


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

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.baseClass.ActionListener;
import ru.geekbrains.baseClass.Base2DScreen;
import ru.geekbrains.baseClass.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.poolClass.AsteroidPool;
import ru.geekbrains.poolClass.BulletPool;
import ru.geekbrains.poolClass.EnemyPool;
import ru.geekbrains.poolClass.ExplosionPool;
import ru.geekbrains.poolClass.FogPool;
import ru.geekbrains.spriteClass.Asteroid;
import ru.geekbrains.spriteClass.Background;
import ru.geekbrains.spriteClass.Bullet;
import ru.geekbrains.spriteClass.ButtonNewGame;
import ru.geekbrains.spriteClass.Enemy;
import ru.geekbrains.spriteClass.Fog;
import ru.geekbrains.spriteClass.GameMessage;
import ru.geekbrains.spriteClass.HpView;
import ru.geekbrains.spriteClass.MainShip;
import ru.geekbrains.spriteClass.Star;
import ru.geekbrains.utilsClass.AsteroidEmitter;
import ru.geekbrains.utilsClass.EnemiesEmitter;
import ru.geekbrains.utilsClass.FogEmitter;

public class GameScreen extends Base2DScreen implements ActionListener {

    private enum State {PLAYING, GAME_OVER}

    private State state;

    private static final int STARS_NUM = 70;
    private static final float FONT_SIZE = 0.02f;

    private static final float HEIGHT_GAME_OVER_BUTTON = 0.12f;
    private static final float BUTTON_MARGIN_GAME_OVER_BUTTON = -0.5f;

    private Background background;
    private Background cloud1;
    private Background cloud2;

    private Texture bg;
    private Texture cl;

    private TextureAtlas atlasText;
    private TextureAtlas mainShipAtlas;
    private TextureAtlas atlasMain;
    private TextureAtlas asteroidAtlas;
    private TextureAtlas fogAtlas;

    private List<Star> stars;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private AsteroidPool asteroidPool;
    private FogPool fogPool;

    private EnemiesEmitter enemiesEmitter;
    private AsteroidEmitter asteroidEmitter;
    private FogEmitter fogEmitter;

    private Music backgroundMusic;

    private Sound explosionSound;
    private Sound mainShipBulletSound;
    private Sound enemyShipBulletSound;

    private ButtonNewGame buttonNewGame;

    private GameMessage gameOver;

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHp = new StringBuilder();
    private StringBuilder sbStage = new StringBuilder();

    private int frags;
    private HpView hpView;

    GameScreen(Game game) {
        super(game);
        state = State.PLAYING;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        cl = new Texture("textures/cloud.png");
        background = new Background(new TextureRegion(bg));
        cloud1 = new Background(new TextureRegion(cl), true);
        cloud2 = new Background(new TextureRegion(cl), false);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/fon.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        mainShipBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/piu.wav"));
        enemyShipBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ph.wav"));

        atlasMain = new TextureAtlas("textures/mainAtlas.tpack");
        mainShipAtlas = new TextureAtlas("textures/mainShipAtlas.pack");
        atlasText = new TextureAtlas("textures/text.pack");
        asteroidAtlas = new TextureAtlas("textures/asteroidAtlas.pack");
        fogAtlas = new TextureAtlas("textures/fogAtlas.pack");

        gameOver = new GameMessage(atlasText.findRegion("game_over"), HEIGHT_GAME_OVER_BUTTON, BUTTON_MARGIN_GAME_OVER_BUTTON);

        TextureRegion starRegion = atlasMain.findRegion("star");
        stars = new ArrayList<Star>();
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f),
                    Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.0008f, 0.009f)));
        }
        explosionPool = new ExplosionPool(atlasMain, explosionSound);
        bulletPool = new BulletPool(explosionPool);
        mainShip = new MainShip(mainShipAtlas, bulletPool, explosionPool, worldBounds, mainShipBulletSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip, enemyShipBulletSound);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyPool, mainShipAtlas);

        asteroidPool = new AsteroidPool(asteroidAtlas, worldBounds, explosionPool);
        asteroidEmitter = new AsteroidEmitter(asteroidPool, worldBounds);

        fogPool = new FogPool(fogAtlas, worldBounds);
        fogEmitter = new FogEmitter(fogPool, worldBounds);

        buttonNewGame = new ButtonNewGame(atlasText, this, worldBounds);

        font = new Font("font/font.fnt", "font/font.png");
        font.setWorldSize(FONT_SIZE);

        hpView = new HpView(asteroidAtlas.findRegion("ryg"), 1, 4, 4, mainShip, worldBounds);

        startNewGame();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbStage.setLength(0);
        font.draw(batch, sbFrags.append("Frags: ").append(frags), worldBounds.getLeft(), worldBounds.getTop() - 0.05f);
        font.draw(batch, sbHp.append("HP: ").append(mainShip.getHp()), worldBounds.getLeft(), worldBounds.getTop() - 0.01f);
        font.draw(batch, sbStage.append("Stage: ").append(enemiesEmitter.getStage()), worldBounds.getRight(), worldBounds.getTop() - 0.05f, Align.right);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        switch (state) {
            case PLAYING:
                cloud1.update(delta);
                cloud2.update(delta);
                mainShip.update(delta, frags);
                enemyPool.updateActiveSprites(delta);
                enemiesEmitter.generateEnemies(delta, frags);
                asteroidEmitter.generateAsteroid(delta, frags);
                fogEmitter.generateFog(delta);
                hpView.update(delta);
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                    backgroundMusic.stop();
                }
                break;
            case GAME_OVER:
                break;
        }
        explosionPool.updateActiveSprites(delta);
        asteroidPool.updateActiveSprites(delta);
        fogPool.updateActiveSprites(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        background.draw(batch);
        cloud1.draw(batch);
        cloud2.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        asteroidPool.drawActiveSprites(batch);


        switch (state) {
            case PLAYING:
                mainShip.draw(batch);
                hpView.draw(batch);
                break;
            case GAME_OVER:
                gameOver.draw(batch);
                buttonNewGame.draw(batch);
                break;
        }
        fogPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        cloud1.resize(worldBounds);
        cloud2.resize(worldBounds);

        for (Star star : stars) {
            star.resize(worldBounds);
        }
        hpView.resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlasMain.dispose();
        mainShipAtlas.dispose();
        atlasText.dispose();
        asteroidAtlas.dispose();
        fogAtlas.dispose();

        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        asteroidPool.dispose();
        fogPool.dispose();

        backgroundMusic.dispose();
        explosionSound.dispose();
        mainShipBulletSound.dispose();
        enemyShipBulletSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer);
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer);
        }
    }

    private void checkCollision() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }

            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                enemy.boom();
                mainShip.damage(enemy.getBoomDamagePower());
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                if (!mainShip.isDestroyed()) {
                    bullet.boom();
                }
                bullet.destroy();

            }
        }

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
                    if (!enemy.isDestroyed()) {
                        bullet.boom();
                    }
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                        mainShip.upHp(1);
                        break;
                    }
                }
            }
        }

        List<Asteroid> asteroidList = asteroidPool.getActiveObjects();
        for (Asteroid asteroid : asteroidList) {
            if (asteroid.isDestroyed()) {
                continue;
            }
            if (mainShip.isAsteroidCollision(asteroid)) {
                mainShip.damage(asteroid.getDamage());
                asteroid.boom();
                asteroid.destroy();
            }
            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                if (enemy.isAsteroidCollision(asteroid)) {
                    enemy.damage(asteroid.getDamage());
                    asteroid.boom();
                    asteroid.destroy();
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == mainShip) {
                for (Asteroid asteroid : asteroidList) {
                    if (asteroid.isBulletMainShipCollision(bullet)) {
                        bullet.boom();
                        bullet.destroy();
                    }
                }
            }

            for (Enemy enemy : enemyList) {
                if (bullet.getOwner() == enemy) {
                    for (Asteroid asteroid : asteroidList) {
                        if (asteroid.isBulletEnemyCollision(bullet)) {
                            bullet.boom();
                            bullet.destroy();
                        }
                    }
                }
            }
        }

        List<Fog> fogList = fogPool.getActiveObjects();
        for (Fog fog : fogList) {
            if (fog.isDestroyed()) {
                continue;
            }
            fog.isFogWithShipCollision(mainShip);
        }

    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        asteroidPool.freeAllDestroyedActiveSprites();
        fogPool.freeAllDestroyedActiveSprites();
    }


    private void startNewGame() {
        state = State.PLAYING;
        frags = 0;

        mainShip.setToNewGame();
        enemiesEmitter.setToNewGame();

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        asteroidPool.freeAllActiveSprites();
        fogPool.freeAllActiveSprites();
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame) {
            startNewGame();
        } else {
            throw new RuntimeException("Unknown src");
        }
    }
}