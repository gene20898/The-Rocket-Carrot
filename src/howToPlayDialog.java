package com.company;

import javax.swing.*;

public class howToPlayDialog extends JDialog{
    private JLabel contentpane;
    private MyImageIcon howToPlayImg;
    private int howToPlayWidth = 626 , howToPlayHeight = 392;

    howToPlayDialog() {
        howToPlayImg = new MyImageIcon("resources/components/badge/howtoplay.png");
        setTitle("How to play");
        setBounds(370, 180, howToPlayWidth, howToPlayHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        contentpane = new JLabel(howToPlayImg);
        setContentPane(contentpane);
        validate();
    }

}
