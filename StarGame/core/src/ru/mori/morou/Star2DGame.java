package ru.mori.morou;

import com.badlogic.gdx.Game;
import ru.mori.morou.screen.MenuScreen;


public class Star2DGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen());
    }
}
