package ru.geekbrains.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class LevelDesinger {

    private AssetManager assetManager;

    public LevelDesinger() {
        assetManager = new AssetManager();
        assetManager.load("data/mysound3.mp3", Music.class);
        assetManager.finishLoadingAsset("data/mysound3.mp3");

        assetManager.load("data/mysound1.mp3", Music.class);
        assetManager.finishLoadingAsset("data/mysound1.mp3");
    }
    public boolean startMusicMenu() {
        if(assetManager.isLoaded("data/mysound3.mp3")) {
            Music music = assetManager.get("data/mysound3.mp3", Music.class);
            music.play();
            music.setLooping(true);
        }else {
            System.out.println("not loaded yet");
            return false;
        }
        return true;
    }

    public boolean stopMusicMenu() {
        assetManager.dispose();
        return true;
    }

    public boolean startMusicGame() {
        if(assetManager.isLoaded("data/mysound1.mp3")) {
            Music music = assetManager.get("data/mysound1.mp3", Music.class);
            music.play();
            music.setLooping(true);
        }else {
            System.out.println("not loaded yet");
            return false;
        }
        return true;
    }

    public boolean stopMusicGame() {
        assetManager.dispose();
        return true;
    }
}
