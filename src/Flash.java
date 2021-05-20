package com.company;

import javax.swing.*;
import java.awt.*;

public class Flash extends JFrame{
    Flash(){
        setTitle("Flash Screen");
        setSize(1366, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setBackground(Color.white);
        setOpacity(0.9f);
        setVisible(true);

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            System.out.println(e);
        }

        dispose();
    }


    public static void main(String args[]){
        new Flash();
    }
}
