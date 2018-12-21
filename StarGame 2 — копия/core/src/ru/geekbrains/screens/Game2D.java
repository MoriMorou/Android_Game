package ru.geekbrains.screens;

import com.badlogic.gdx.Game;

public class Game2D extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
