package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameoverScreen extends JFrame {
    private JPanel contentpane;
    private JLabel drawpane, gameoverBadge, missionReportLabel;
    private JButton restartButton, homeButton;
    private MyImageIcon gameOver_bg, gameoverBadgeImg, missionReportImg;
    private MyImageIcon restartButtonImg, homeButtonImg;
    private MySoundEffect clickEffect,enterEffect;
    private SettingsThread settings;
    private  int score;

    private int frameWidth = 1366, frameHeight = 768;
    private int gameoverBadgeWidth = 709, gameoverBadgeHeight = 128;
    private int missionReportWidth = 828, missionReportHeight = 273;
    private int buttonWidth = 194, buttonHeight = 108;

    GameoverScreen(SettingsThread set, int score) {
        setTitle("Game Over");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        settings = set;
        this.score = score;

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

    private void Addcomponents() {
        gameOver_bg = new MyImageIcon("resources/background/Gameover screen.png").resize(frameWidth, frameHeight);
        gameoverBadgeImg = new MyImageIcon("resources/components/badge/gameover_badge.png").resize(gameoverBadgeWidth,gameoverBadgeHeight);

        clickEffect = new MySoundEffect("resources/sound/click.wav");
        enterEffect = new MySoundEffect("resources/sound/select.wav");

        drawpane = new JLabel();
        drawpane.setIcon(gameOver_bg);
        drawpane.setLayout(null);

        gameoverBadge = new JLabel(gameoverBadgeImg);
        gameoverBadge.setBounds((frameWidth - gameoverBadgeWidth) / 2, gameoverBadgeHeight/2, gameoverBadgeWidth, gameoverBadgeHeight);

        restartButtonImg = new MyImageIcon("resources/components/buttons/RestartButton.png").resize(buttonWidth,buttonHeight);
        restartButton = new JButton();
        restartButton.setIcon(restartButtonImg);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setBounds((frameWidth-2*buttonWidth)/2 -40,frameHeight-2*buttonHeight,buttonWidth,buttonHeight);restartButtonImg = new MyImageIcon("resources/components/buttons/PlayButton.png");
        restartButton.addMouseListener(new MouseAdapter() {
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
               restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!settings.isMuteEffect()) {
                    enterEffect.playOnce();
                }
            }
        });
        homeButtonImg = new MyImageIcon("resources/components/buttons/HomeButton.png").resize(buttonWidth,buttonHeight);
        homeButton = new JButton();
        homeButton.setIcon(homeButtonImg);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setBounds((frameWidth)/2 +40,frameHeight-2*buttonHeight,buttonWidth,buttonHeight);
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect()) {
                    clickEffect.playOnce();
                }
                new MainApplication(settings);
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!settings.isMuteEffect()) {
                    enterEffect.playOnce();
                }
            }
        });

        missionReportImg = new MyImageIcon("resources/components/badge/Mission Report.png").resize(missionReportWidth, missionReportHeight);
        missionReportLabel = new JLabel(missionReportImg);
        missionReportLabel.setBounds((frameWidth - missionReportWidth) / 2, 236, missionReportWidth, missionReportHeight);

        String playerName = settings.getPlayerName();
        String detail = "Captain: " + playerName + "\r\n\n" + "Crews: Wayu Bangkamed            ID:6180141\r\n            Norrawit Prasertsuwan    ID:6180234\r\n            Pattara Kuntarodjanjun    ID:6180229 \r\n            Buratsakorn petchdee     ID:6080091";
        JTextArea report = new JTextArea(detail);
        report.setFont(new Font("SanSerif", Font.BOLD, 20));
        report.setBounds(20, 80, 600, 300);
        report.setForeground(Color.WHITE);
        report.setOpaque(false);
        report.setBorder(null);
        report.setEditable(false);
        report.setHighlighter(null);

        JTextArea point = new JTextArea("      Well done!\n Your score is  " + score );
        point.setFont(new Font("SanSerif", Font.BOLD, 35));
        point.setBounds(490, 90, 600, 400);
        point.setForeground(Color.WHITE);
        point.setOpaque(false);
        point.setBorder(null);
        point.setEditable(false);
        point.setHighlighter(null);

        missionReportLabel.add(report);
        missionReportLabel.add(point);

        drawpane.add(gameoverBadge);
        drawpane.add(missionReportLabel);
        drawpane.add(restartButton);
        drawpane.add(homeButton);
        contentpane.add(drawpane,BorderLayout.CENTER);
        validate();
    }

    public static void main(String[] args) {
        new GameoverScreen(new SettingsThread(), 0);
    }
}
