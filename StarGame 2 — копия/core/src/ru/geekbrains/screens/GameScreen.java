package ru.geekbrains.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

import ru.geekbrains.control.ButtonNewGame;
import ru.geekbrains.control.MessageGameOver;
import ru.geekbrains.engine.ActionListener;
import ru.geekbrains.engine.Base2DScreen;
import ru.geekbrains.engine.font.Font;
import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.math.Rnd;
import ru.geekbrains.models.actor.Hero;
import ru.geekbrains.models.asteroids.Asteroid;
import ru.geekbrains.models.backgrounds.Background_Game;
import ru.geekbrains.models.bullets.BulletPool;
import ru.geekbrains.models.effects.Flame;
import ru.geekbrains.models.effects.Smoke;
import ru.geekbrains.models.effects.explosions.ExplosionMiniPool;
import ru.geekbrains.models.effects.explosions.ExplosionPool;
import ru.geekbrains.models.enemys.EnemyShip;
import ru.geekbrains.models.enemys.EnemyShipPool;
import ru.geekbrains.models.stars.Star;

public class GameScreen extends Base2DScreen implements ActionListener {

    public GameScreen(Game game) {
        super(game);
    }

    private enum State { PLAYING, GAME_OVER }
    private State state;

    public static float WIDTH_SCREEN = 0.5625f;
    private static final float FONT_SIZE = 0.02f;

    private Texture backgroundTexture;
    private Texture shipTexture;
    private Texture[] starTexture= new Texture[4];
    private Texture[] enemyShips = new Texture[2];
    private Texture smokeTexture;
    private Texture img128x128;
    private TextureAtlas atlas;

    private Background_Game background_game;
    private LevelDesinger desinger = new LevelDesinger();
    public static Hero hero;
    private Star[] stars = new Star[50];
    private Smoke smoke;
    private Smoke smoke2;

    TextureAtlas flameAtlas;
    private Flame flame;
    public static int frags;
    TextureAtlas explosionAtlas;

    TextureAtlas asteroidAtlas;
    TextureAtlas asteroidAtlas2;
    private Asteroid[] asteroids = new Asteroid[40];
    private Asteroid[] asteroids2 = new Asteroid[40];

    ExplosionPool explosionPool;
    ExplosionMiniPool explosionMiniPool;
    TextureAtlas explosionPoolAtlas;
    TextureAtlas explosionMiniPoolAtlas;
    Sound soundExplosion;

    EnemyShipPool enemyShipPool;
    float respawnTimer = 15f;

    BulletPool bulletPool;
    Sound shotSound;
    Sound crushSound;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbStage = new StringBuilder();

    Vector2 keyboardTouch = new Vector2();


    Random random = new Random();

    @Override
    public void show() {
        super.show();
        shotSound = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
        crushSound = Gdx.audio.newSound(Gdx.files.internal("crush.wav"));
        explosionPoolAtlas = new TextureAtlas(Gdx.files.internal("explosionAtlas4.tpack"));
        explosionMiniPoolAtlas = new TextureAtlas(Gdx.files.internal("explosionAtlas.tpack"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        explosionPool = new ExplosionPool(explosionPoolAtlas,soundExplosion);
        explosionMiniPool = new ExplosionMiniPool(explosionMiniPoolAtlas,soundExplosion);
        bulletPool = new BulletPool(soundExplosion);
        enemyShipPool = new EnemyShipPool();

        backgroundTexture = new Texture("bg_level_2.png");
        background_game = new Background_Game(new TextureRegion(backgroundTexture), -0.5f);

        starTexture[0] = new Texture("starWhite.png");
        starTexture[1] = new Texture("starBlue1.png");
        starTexture[2] = new Texture("starRed1.png");
        starTexture[3] = new Texture("starYellow.png");

        enemyShips[0] = new Texture("enemyShip4.png");
        enemyShips[1] = new Texture("enemyShip3.png");
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(new TextureRegion(starTexture[random.nextInt(3)]),Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f);
        }

        smokeTexture = new Texture("smoke1.png");
        smoke = new Smoke(new TextureRegion(smokeTexture), 0.001f,1f);

        smokeTexture = new Texture("smoke6.png");
        smoke2 = new Smoke(new TextureRegion(smokeTexture), 0.001f,0f);

        shipTexture = new Texture("ship2.png");
        hero = new Hero(new TextureRegion(shipTexture), explosionPool,0, -0.4f,0.3f, bulletPool);

        img128x128 = new Texture("128x128.png");

        asteroidAtlas = new TextureAtlas(Gdx.files.internal("asteroidAtlas.tpack"));
        asteroidAtlas2 = new TextureAtlas(Gdx.files.internal("asteroidAtlas2.tpack"));
        explosionAtlas = new TextureAtlas(Gdx.files.internal("explosionAtlas3.tpack"));

        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid(asteroidAtlas, explosionAtlas,new TextureRegion(img128x128),
                    Rnd.nextFloat(-0.24f, 0.24f), Rnd.nextFloat(3f, 55f), // стартовые значения положения X и Y
                    0f, Rnd.nextFloat(-0.5f, -0.2f), // скорость движения по X и Y
                    Rnd.nextFloat(0.03f, 0.09f), // пропорция относительно оси Y, max 1f
                    Rnd.nextFloat(0.03f,0.06f), // скорость анимации
                    Rnd.nextFloat(0f, 1f),
                    crushSound); // скорость ващения (angle)
        }

        for (int i = 0; i < asteroids2.length; i++) {
            asteroids2[i] = new Asteroid(asteroidAtlas2, explosionAtlas,new TextureRegion(img128x128),
                    Rnd.nextFloat(-0.24f, 0.24f), Rnd.nextFloat(3f, 55f), // стартовые значения положения X и Y
                    0f, Rnd.nextFloat(-0.5f, -0.2f), // скорость движения по X и Y
                    Rnd.nextFloat(0.03f, 0.09f), // пропорция относительно оси Y, max 1f
                    Rnd.nextFloat(0.03f,0.06f), // скорость анимации
                    Rnd.nextFloat(0f, 1f),
                    crushSound); // скорость ващения (angle)
        }
        flameAtlas = new TextureAtlas(Gdx.files.internal("fireAtlas.tpack"));
        flame = new Flame(flameAtlas, new TextureRegion(img128x128));
        this.desinger.startMusicGame();

        atlas = new TextureAtlas("mainAtlas.tpack");
        this.messageGameOver = new MessageGameOver(atlas);
        this.buttonNewGame = new ButtonNewGame(atlas, this);

        this.font = new Font("font.fnt", "font.png");
        this.font.setWordSize(FONT_SIZE);
        state=State.PLAYING;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (state == State.PLAYING) {
            update(delta);
        }
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background_game.draw(batch);
        printInfo();
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        smoke.draw(batch);
        smoke2.draw(batch);
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].draw(batch, delta, hero);
        }
        for (int i = 0; i < asteroids2.length; i++) {
            asteroids2[i].draw(batch, delta, hero);
        }
        flame.draw(batch, hero, delta);
        bulletPool.freeAllDestroyedActiveObjects();
        bulletPool.drawActiveObjects(batch);
        enemyShipPool.freeAllDestroyedActiveObjects();
        enemyShipPool.drawActiveObjects(batch);
        hero.draw(batch);
        explosionMiniPool.freeAllDestroyedActiveObjects();
        explosionMiniPool.drawActiveObjects(batch);
        explosionPool.freeAllDestroyedActiveObjects();
        explosionPool.drawActiveObjects(batch);
        if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }

    public void createEnemy() {
        EnemyShip enemyShip = enemyShipPool.obtain();
        enemyShip.set(new TextureRegion(enemyShips[random.nextInt(2)]),explosionPool, bulletPool, Rnd.nextFloat((-WIDTH_SCREEN/2)+0.07f, (WIDTH_SCREEN/2)-0.07f), Rnd.nextFloat(-0.2f, -0.1f));
    }

    public void update(float delta) {
        if (hero.getHp()<=0) {
            state = State.GAME_OVER;
        }
        if (respawnTimer<=1f) {
            respawnTimer+=0.001f;
        } else {
            createEnemy();
            System.out.println("Враг рядом");
            respawnTimer = 0;
        }
        hero.update(delta);
        smoke.update(delta);
        smoke2.update(delta);
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].update(delta, hero);
        }
        for (int i = 0; i < asteroids2.length; i++) {
            asteroids2[i].update(delta, hero);
        }
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        enemyShipPool.updateActiveObjects(delta);
        bulletPool.updateActiveObjects(delta, enemyShipPool, hero, explosionMiniPool);
        explosionPool.updateActiveObjects(delta);
        explosionMiniPool.updateActiveObjects(delta);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        shipTexture.dispose();
        for (int i = 0; i < starTexture.length; i++) {
            starTexture[i].dispose();
        }
        smokeTexture.dispose();
        flameAtlas.dispose();
        asteroidAtlas.dispose();
        asteroidAtlas2.dispose();
        desinger.stopMusicGame();
        bulletPool.dispose();
        enemyShipPool.dispose();
        shotSound.dispose();
        crushSound.dispose();
        soundExplosion.dispose();
        explosionPool.dispose();
        explosionMiniPool.dispose();
        super.dispose();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background_game.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        smoke.resize(worldBounds);
        smoke2.resize(worldBounds);
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].resize(worldBounds);
        }
        for (int i = 0; i < asteroids2.length; i++) {
            asteroids2[i].resize(worldBounds);
        }
        flame.resize(worldBounds);
        hero.resize(worldBounds);
        WIDTH_SCREEN = worldBounds.getWidth();
    }
    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbStage.setLength(0);
        font.draw(batch, sbFrags.append("Frags:").append(frags), worldBounds.getLeft()+0.01f, worldBounds.getTop()-0.02f);
        font.draw(batch, sbHP.append("HP:").append(hero.getHp()), worldBounds.pos.x, worldBounds.getTop()-0.02f, Align.center);
    }
    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            hero.stopPoint();
        } else {
            buttonNewGame.touchUp(touch, pointer);
        }

    }
    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            if (touch.y < -0.38f) {
                hero.shoot();
                shotSound.play(0.2f);
            } else {
                hero.setEndPoint(touch.cpy().set(touch.x, hero.pos.y));
            }
        } else {
            buttonNewGame.touchDown(touch, pointer);
        }
    }
    @Override
    protected void touchDragged(Vector2 touch, int pointer) {
            hero.setEndPoint(touch.cpy().set(touch.x, hero.pos.y));
    }
    @Override
    public boolean keyDown(int keycode) {
        hero.keyDown(keycode);
        if (keycode == Input.Keys.SPACE) {
            hero.shoot();
            shotSound.play(0.2f);
            System.out.println("СТРЕЛЯЕМ!!!");
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        hero.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character=" + character);
        return false;
    }

    private void startNewGame() {
        state = State.PLAYING;
        frags = 0;
        hero.setToNewGame();
        bulletPool.freeAllActiveObjects();
        enemyShipPool.freeAllActiveObjects();
        explosionMiniPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
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
