package com.company;

import javax.swing.*;

public class Platform {
    private JLabel platformLabel;
    private int platform_X;
    private int speed;
    private boolean moveLeft;

    Platform(JLabel platformLabel, int platform_X, int speed, boolean moveLeft) {
        this.platformLabel = platformLabel;
        this.platform_X = platform_X;
        this.speed = speed;
        this.moveLeft = moveLeft;
    }

    public JLabel getPlatformLabel() {
        return platformLabel;
    }

    public int getPlatform_X() {
        return platform_X;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void move() {
        if (moveLeft) {
            platform_X -= speed;
        } else {
            platform_X += speed;
        }
    }
}
