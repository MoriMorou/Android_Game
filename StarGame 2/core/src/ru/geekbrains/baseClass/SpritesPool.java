package ru.geekbrains.baseClass;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<T>();
    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain(){
        T object;
        if(freeObjects.isEmpty()){
            object = newObject();
        }else{
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta){
        for (int i = 0; i < activeObjects.size(); i++) {
            Sprite sprite = activeObjects.get(i);
            if(sprite.isDestroyed()){
                throw new RuntimeException("Обнавляем уничтоженный объект");
            }
            sprite.update(delta);
        }
    }

    public void freeAllDestroyedActiveSprites(){
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if(sprite.isDestroyed()){
                free(sprite);
                i--;
                sprite.flushDestrioy();
            }
        }
    }

    public void freeAllActiveSprites(){
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    public void free(T object){
        if(!activeObjects.remove(object)){
            throw new RuntimeException("Попытка удаления несуществующего объекта");
        }
        freeObjects.add(object);
    }

    public void drawActiveSprites(SpriteBatch batch){
        for (int i = 0; i < activeObjects.size(); i++) {
            Sprite sprite = activeObjects.get(i);
            if(sprite.isDestroyed()){
                throw new RuntimeException("Рисуем уничтоженный объект");
            }
            sprite.draw(batch);
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }
}
