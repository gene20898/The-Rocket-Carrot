package com.company;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;

public class MainApplication extends JFrame {
    private JPanel contentpane;
    private JLabel drawpane, gameBadge;
    private JTextField enterName;
    private String playerName = "";
    private JButton playButton, howToButton, settingButton;
    private MyImageIcon startScreen_bg, gameBadgeImg;
    private MyImageIcon playButtonImg, howToButtonImg, settingButtonImg;
    private MySoundEffect clickEffect, enterEffect;
    private SettingsThread settings;

    private int frameWidth = 1366, frameHeight = 768;
    private int gameBadgeWidth = 640, gameBadgeHeight = 171;
    private int textFieldWidth = 400, textFieldHeight = 60;
    private int buttonWidth = 194, buttonHeight = 108;

    MainApplication(SettingsThread set) {
        setTitle("Rocket Carrot");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (set == null) {
            settings = new SettingsThread();
            System.out.println("Null >> ");
        } else {
            settings = set;
        }

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
        contentpane.setLayout(new BorderLayout());
        Addcomponents();
    }

    public void Addcomponents() {
        startScreen_bg = new MyImageIcon("resources/background/Start screen.png").resize(frameWidth, frameHeight);
        gameBadgeImg = new MyImageIcon("resources/components/badge/title.png");

        drawpane = new JLabel();
        drawpane.setIcon(startScreen_bg);
        drawpane.setLayout(null);

        gameBadge = new JLabel(gameBadgeImg);
        gameBadge.setBounds((frameWidth - gameBadgeWidth) / 2, gameBadgeHeight - 50, gameBadgeWidth, gameBadgeHeight);

        clickEffect = new MySoundEffect("resources/sound/click.wav");
        enterEffect = new MySoundEffect("resources/sound/select.wav");

        playerName = settings.getPlayerName();
        enterName = new JTextField("Enter your name");
        enterName.setBounds((frameWidth - textFieldWidth) / 2, frameHeight / 2 - textFieldHeight, textFieldWidth, textFieldHeight);
        enterName.setFont(new Font("SanSerif",Font.BOLD,40));
        enterName.setBorder(null);
        enterName.setHorizontalAlignment(JTextField.CENTER);
        enterName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(playerName.compareToIgnoreCase("Enter your name") == 0)
                enterName.setText("");
            }
        });
        enterName.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent caretEvent) {
                playerName = enterName.getText();
                settings.setPlayerName(playerName);
            }
        });

        howToButtonImg = new MyImageIcon("resources/components/buttons/howtoButton.png").resize(buttonWidth, buttonHeight);
        howToButton = new JButton();
        howToButton.setIcon(howToButtonImg);
        howToButton.setContentAreaFilled(false);
        howToButton.setBorderPainted(false);
        howToButton.setBounds((frameWidth - 4 * buttonWidth) / 2, frameHeight / 2 + 50, buttonWidth, buttonHeight);
        howToButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect()) {
                    clickEffect.playOnce();
                }
                new howToPlayDialog();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                howToButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!settings.isMuteEffect()) {
                    enterEffect.playOnce();
                }
            }
        });

        playButtonImg = new MyImageIcon("resources/components/buttons/PlayButton.png").resize(buttonWidth, buttonHeight);
        playButton = new JButton();
        playButton.setIcon(playButtonImg);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setBounds((frameWidth - buttonWidth) / 2, frameHeight / 2 + 50, buttonWidth, buttonHeight);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect()) {
                    clickEffect.playOnce();
                }
                new PlayScreen(settings);
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!settings.isMuteEffect()) {
                    enterEffect.playOnce();
                }
            }
        });

        settingButtonImg = new MyImageIcon("resources/components/buttons/settingButton.png").resize(buttonWidth, buttonHeight);
        settingButton = new JButton();
        settingButton.setIcon(settingButtonImg);
        settingButton.setContentAreaFilled(false);
        settingButton.setBorderPainted(false);
        settingButton.setBounds((frameWidth+2*buttonWidth)/2,frameHeight/2 + 50,buttonWidth,buttonHeight);
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect()) {
                    clickEffect.playOnce();
                }
                    new SettingsDialog(settings);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!settings.isMuteEffect()) {
                    enterEffect.playOnce();
                }
            }
        });

        drawpane.add(gameBadge);
        drawpane.add(enterName);
        drawpane.add(playButton);
        drawpane.add(howToButton);
        drawpane.add(settingButton);

        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }

    public static void main(String[] args) {
        new MainApplication(null);
    }
}
