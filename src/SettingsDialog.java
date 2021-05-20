package com.company;

import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsDialog extends JDialog {
    private JLabel contentpane;
    private JLabel soundLabel, difficultyLabel, backgroundLabel;
    private JCheckBox musicCheckBox, effectCheckBox;
    private JRadioButton diffRadio[];
    private JComboBox backgroundComboBox;
    private MyImageIcon settingsDiaogImg;
    private MySoundEffect clickEffect, enterEffect;
    private SettingsThread settings;

    private int settingsWidth = 626, settingsHeight = 443;

    SettingsDialog(SettingsThread set) {
        settingsDiaogImg = new MyImageIcon("resources/components/badge/Settings.png");
        setBounds(370, 160, settingsWidth, settingsHeight);
        setTitle("Settings");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        settings = set;

        contentpane = new JLabel(settingsDiaogImg);
        contentpane.setLayout(null);
        setContentPane(contentpane);

        Addcomponents();
    }

    public void Addcomponents() {
        clickEffect = new MySoundEffect("resources/sound/click.wav");
        enterEffect = new MySoundEffect("resources/sound/select.wav");

        difficultyLabel = new JLabel("Difficulty");
        difficultyLabel.setFont(new Font("SanSerif", Font.BOLD, 20));
        difficultyLabel.setBounds(20, 80, 200, 30);

        diffRadio = new JRadioButton[3];
        diffRadio[0] = new JRadioButton("Easy");
        diffRadio[1] = new JRadioButton("Normal");
        diffRadio[2] = new JRadioButton("Hard");
        diffRadio[0].setOpaque(false);
        diffRadio[1].setOpaque(false);
        diffRadio[2].setOpaque(false);
        diffRadio[settings.getDifficulty()].setSelected(true);

        ButtonGroup bg = new ButtonGroup();
        bg.add(diffRadio[0]);
        bg.add(diffRadio[1]);
        bg.add(diffRadio[2]);

        JPanel diffPanel = new JPanel();
        diffPanel.setBounds(20, 120, 200, 30);
        diffPanel.setOpaque(false);
        diffPanel.add(diffRadio[0]);
        diffPanel.add(diffRadio[1]);
        diffPanel.add(diffRadio[2]);

        diffRadio[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (((JRadioButton) e.getItem()).isSelected()) {
                    settings.setDifficulty(0);
                }
            }
        });

        diffRadio[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (((JRadioButton) e.getItem()).isSelected()) {
                    settings.setDifficulty(1);
                }
            }
        });

        diffRadio[2].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (((JRadioButton) e.getItem()).isSelected()) {
                    settings.setDifficulty(2);
                }
            }
        });

        for (int i = 0; i < diffRadio.length; i++) {
            diffRadio[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!settings.isMuteEffect()) {
                        clickEffect.playOnce();
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!settings.isMuteEffect()) {
                        enterEffect.playOnce();
                    }
                }
            });
        }

        backgroundLabel = new JLabel("Background");
        backgroundLabel.setFont(new Font("SanSerif", Font.BOLD, 20));
        backgroundLabel.setBounds(20, 180, 200, 30);

        String[] scene = new String[]{"ground", "sky", "space", "mystic", "sun", "moon"};
        backgroundComboBox = new JComboBox(scene);
        backgroundComboBox.setSelectedIndex(settings.getBg_index());
        backgroundComboBox.setBounds(30, 220, 200, 30);
        backgroundComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                settings.setBackground(backgroundComboBox.getSelectedIndex());
            }
        });
        backgroundComboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect()) {
                    clickEffect.playOnce();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!settings.isMuteEffect())
                enterEffect.playOnce();
            }
        });

        soundLabel = new JLabel("Sound and Effect");
        soundLabel.setFont(new Font("SanSerif", Font.BOLD, 20));
        soundLabel.setBounds(20, 280, 200, 30);

        musicCheckBox = new JCheckBox("Music");
        musicCheckBox.setBounds(20, 320, 60, 30);
        musicCheckBox.setOpaque(false);
        musicCheckBox.setSelected(!settings.isMuteMusic());
        musicCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == itemEvent.SELECTED) {
                    settings.startMusic();
                } else {
                    settings.stopMusic();
                }
            }
        });
        musicCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect())
                clickEffect.playOnce();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!settings.isMuteEffect())
                enterEffect.playOnce();
            }
        });


        effectCheckBox = new JCheckBox("Effect");
        effectCheckBox.setBounds(100, 320, 60, 30);
        effectCheckBox.setOpaque(false);
        effectCheckBox.setSelected(!settings.isMuteEffect());
        effectCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == itemEvent.SELECTED) {
                    settings.unMuteSoundEffect();
                } else {
                    settings.muteSoundEffect();
                }
            }
        });
        effectCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!settings.isMuteEffect())
                clickEffect.playOnce();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!settings.isMuteEffect())
                enterEffect.playOnce();
            }
        });

        contentpane.add(difficultyLabel);
        contentpane.add(diffPanel);
        contentpane.add(backgroundLabel);
        contentpane.add(backgroundComboBox);
        contentpane.add(soundLabel);
        contentpane.add(musicCheckBox);
        contentpane.add((effectCheckBox));

        validate();
    }

    public static void main(String[] args) {
        new SettingsDialog(new SettingsThread());
    }
}
