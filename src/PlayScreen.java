package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class PlayScreen extends JFrame {

    private JPanel contentpane, textPanel;
    private JLabel drawpane;
    private JLabel rocketLabel;
    private JTextField scoreText;
    private MyImageIcon backgroundImg, platformImg, rocketImg;
    private SettingsThread settings;
    private MySoundEffect jump, crash;

    private int frameWidth = 1366, frameHeight = 768;
    private final int rocket_width = 23, rocket_height = 69;
    private double rocket_Y = frameHeight - rocket_height * 2;
    private double rocket_X = (frameWidth - rocket_width) / 2;
    private double rocket_velocity = 0;
    private final int JUMP_LIMIT = (int) (frameHeight * 0.4 + rocket_width);
    private boolean isJumping = false;

    private int platform_width = 152, platform_height = 38;
    private int platformBaseSpeed = 2;
    private int platformRandomSpeed = 6;
    private int platformNum = 3;
    private int platformRow = 3;
    private int platform_velocity, velocity_flag = platformRow;

    private CyclicBarrier finish = new CyclicBarrier(platformRow);
    private boolean gameOver = false;
    private int score = 0;

    public PlayScreen(SettingsThread set) {
        setTitle("Rocket Carrot Game");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        settings = set;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(new JFrame(), "Do you want to exit game?", "Exit", JOptionPane.OK_CANCEL_OPTION);
                if (i == 0) {
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        contentpane = (JPanel) getContentPane();
        contentpane.setBackground(Color.BLACK);
        contentpane.setLayout(new BorderLayout());

        this.requestFocus();
        AddComponents();
    }

    public void AddComponents() {
        backgroundImg = settings.getBackgroundImg();
        switch (settings.getDifficulty()) {
            case 0: platformRandomSpeed = 3; break;
            case 1: platformRandomSpeed = 6; break;
            case 2: platformRandomSpeed = 10; break;
        }

        jump = new MySoundEffect("resources/sound/jump.wav");
        crash = new MySoundEffect("resources/sound/Hit.wav");

        platformImg = new MyImageIcon("resources/components/icon/platform.png").resize(platform_width, platform_height);
        rocketImg = new MyImageIcon("resources/components/icon/rocket.png").resize(rocket_width, rocket_height);

        drawpane = new JLabel(backgroundImg);
        drawpane.setLayout(null);

        textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.setBounds((frameWidth - contentpane.getHeight()) / 2, frameHeight / 30, contentpane.getHeight(), 150);
        textPanel.setOpaque(false);

        scoreText = new JTextField("0");
        scoreText.setFont(new Font("SanSerif", Font.BOLD, 110));
        scoreText.setBorder(null);
        scoreText.setEditable(false);
        scoreText.setOpaque(false);
        scoreText.setHighlighter(null);
        scoreText.setForeground(Color.WHITE);
        textPanel.add(scoreText);

        drawpane.add(textPanel);

        rocketLabel = new JLabel(rocketImg);
        rocketLabel.setBounds((int) rocket_X, (int) rocket_Y, rocket_width, rocket_height);
        drawpane.add(rocketLabel);
        setRocketThread();

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isJumping = true;
                    if( !settings.isMuteEffect())
                        jump.playOnce();
                }
            }
        });

        for (int i = 0; i < platformRow; i++) {
            setPlatformThread(i * (backgroundImg.getIconHeight() / platformRow));
        }

        contentpane.add(drawpane);
        validate();
    }

    public void setRocketThread() {
        Thread rocketThread = new Thread() {
            public void run() {
                while (!gameOver) {
                    rocket_Y += rocket_velocity;
                    rocket_velocity += 0.538;

                    if (rocket_Y + rocket_height > backgroundImg.getIconHeight()) {
                        rocket_velocity = -7.7;
                    }
                    if (isJumping) {
                        rocket_Y -= 16;
                        rocket_velocity = -7.7;
                        isJumping = false;
                    }
                    if (rocket_Y < JUMP_LIMIT) {
                        rocket_Y = JUMP_LIMIT;
                    }
                    rocketLabel.setLocation((int) rocket_X, (int) rocket_Y);
                    repaint();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }

        };
        rocketThread.start();
    }

    public void setPlatformThread(int y) {
        Thread platformThread = new Thread() {
            public void run() {
                int platform_Y = y;
                boolean scoreCounted = false;
                ArrayList<Platform> platforms = new ArrayList<>();

                for (int i = 0; i < platformNum; i++) {
                    Random rand = new Random();
                    JLabel platformLabel = new JLabel(platformImg);
                    int platform_X = rand.nextInt(contentpane.getHeight() - platform_width) + (frameWidth - contentpane.getHeight()) / 2;
                    int speed = platformBaseSpeed + rand.nextInt(platformRandomSpeed);
                    boolean moveLeft;

                    if (rand.nextInt(2) == 0) {
                        moveLeft = true;
                    } else {
                        moveLeft = false;
                    }

                    platformLabel.setBounds(platform_X, platform_Y, platform_width, platform_height);
                    drawpane.add(platformLabel);
                    platforms.add(new Platform(platformLabel, platform_X, speed, moveLeft));
                }

                while (!gameOver) {
                    if (rocket_Y <= JUMP_LIMIT || velocity_flag != platformRow) {
                        platform_Y -= moveDown();
                        try {
                            finish.await();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    for (Platform plat : platforms) {
                        plat.move();
                        if (plat.getPlatform_X() < (frameWidth - contentpane.getHeight()) / 2) {
                            plat.setMoveLeft(false);
                        } else if (plat.getPlatform_X() > (frameWidth + contentpane.getHeight()) / 2 - platform_width) {
                            plat.setMoveLeft(true);
                        }

                        plat.getPlatformLabel().setLocation(plat.getPlatform_X(), platform_Y);
                        if (rocketLabel.getBounds().intersects(plat.getPlatformLabel().getBounds()) && !gameOver) {
                            gameOver = true;
                            if (!settings.isMuteEffect()) {
                                crash.playOnce();
                            }
                            new Flash();
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new GameoverScreen(settings, score);
                            dispose();
                        }
                    }
                    if (!scoreCounted && rocket_Y < platform_Y) {
                        updateScore();
                        scoreCounted = true;
                    }
                    if (platform_Y >= backgroundImg.getIconHeight()) {
                        platform_Y -= backgroundImg.getIconHeight();
                        scoreCounted = false;
                    }

                    repaint();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        };
        platformThread.start();
    }

    synchronized public int moveDown() {
        if (velocity_flag >= platformRow) {
            platform_velocity = (int) rocket_velocity;
            velocity_flag = 0;
        }
        velocity_flag++;
        return platform_velocity;
    }

    void updateScore() {
        score++;
        scoreText.setText(Integer.toString(score));
        validate();

    }
}
