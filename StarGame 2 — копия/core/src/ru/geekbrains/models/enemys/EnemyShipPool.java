package ru.geekbrains.models.enemys;

import java.util.List;

import ru.geekbrains.engine.pool.SpritesPool;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
    public  List<EnemyShip> getActiveObjects() {
        return activeObjects;
    }
}