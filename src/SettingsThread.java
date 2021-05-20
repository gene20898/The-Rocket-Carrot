package com.company;

import java.util.ArrayList;

public class SettingsThread extends Thread {
    private ArrayList<MyImageIcon> backgroundImgList;
    private MySoundEffect backgroundMusic;
    private boolean muteMusic = false;
    private boolean muteEffect = false;
    private boolean closeProgram = false;
    private String playerName = "";
    private int bg_index = 0;
    private int difficulty = 1;

    SettingsThread() {
        backgroundImgList = new ArrayList<>();
        backgroundImgList.add(new MyImageIcon("resources/background/ground bg.png"));
        backgroundImgList.add(new MyImageIcon("resources/background/sky bg.png"));
        backgroundImgList.add(new MyImageIcon("resources/background/space bg.png"));
        backgroundImgList.add(new MyImageIcon("resources/background/mystic bg.png"));
        backgroundImgList.add(new MyImageIcon("resources/background/sun bg.png"));
        backgroundImgList.add(new MyImageIcon("resources/background/moon bg.png"));

        backgroundMusic = new MySoundEffect("resources/sound/ThemeSong.wav");

        start();
    }

    public void run() {
        backgroundMusic.playLoop();
        while (!closeProgram) {
            try {
                sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    public void stopMusic() {
        backgroundMusic.stop();
        muteMusic = true;
    }

    public void startMusic() {
        backgroundMusic.playLoop();
        muteMusic = false;
    }

    public boolean isMuteMusic() {
        return muteMusic;
    }

    public void muteSoundEffect() {
        muteEffect = true;
    }

    public void unMuteSoundEffect() {
        muteEffect = false;
    }

    public boolean isMuteEffect() {
        return muteEffect;
    }

    public void setBackground(int i) {
        bg_index = i;
    }

    public int getBg_index() {
        return bg_index;
    }

    public MyImageIcon getBackgroundImg() {
        return backgroundImgList.get(bg_index);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setDifficulty(int i) {
        difficulty = i;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setCloseProgram(boolean closeProgram) {
        this.closeProgram = closeProgram;
    }


    public static void main(String[] args) throws InterruptedException {
        new SettingsThread();
    }
}
