package ru.geekbrains.screenClass;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.baseClass.ActionListener;
import ru.geekbrains.baseClass.Base2DScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.spriteClass.Background;
import ru.geekbrains.spriteClass.ButtonExit;
import ru.geekbrains.spriteClass.ButtonPlay;
import ru.geekbrains.spriteClass.GameMessage;
import ru.geekbrains.spriteClass.Star;

public class MenuScreen extends Base2DScreen implements ActionListener{

    private static final int STARS_NUM = 100;
    private static final float PRESS_SCALE = 0.9f;
    private static final float START_BUTTON_HEIGHT = 0.15f;
    private static final float EXIT_BUTTON_HEIGHT = 0.125f;

    private static final float HEIGHT_STAR_GAME = 0.1f;
    private static final float BUTTON_MARGIN_STAR_GAME = -0.5f;

    private Background background;
    private Texture bg;

    private TextureAtlas atlasText;
    private TextureAtlas atlasMain;
    private List<Star> stars;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    private GameMessage starGame;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));

        atlasMain = new TextureAtlas("textures/menuAtlas.tpack");
        atlasText = new TextureAtlas("textures/text.pack");
        TextureRegion starRegion = atlasMain.findRegion("star");
        stars = new ArrayList<Star>();
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.0008f, 0.009f)));
        }

        starGame = new GameMessage(atlasText.findRegion("star_game"), HEIGHT_STAR_GAME,
                BUTTON_MARGIN_STAR_GAME);

        buttonExit = new ButtonExit(atlasMain, this, PRESS_SCALE);
        buttonExit.setHeightProportion(EXIT_BUTTON_HEIGHT);

        buttonPlay = new ButtonPlay(atlasMain, this, PRESS_SCALE);
        buttonPlay.setHeightProportion(START_BUTTON_HEIGHT);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for(Star star : stars){
            star.update(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for(Star star : stars){
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        starGame.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlasMain.dispose();
        atlasText.dispose();
        super.dispose();
    }


    @Override
    public void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star : stars){
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonExit){
            Gdx.app.exit();
        }else if(src == buttonPlay){
            game.setScreen(new GameScreen(game));
        }else{
            throw new RuntimeException("Unknown src");
        }
    }
}