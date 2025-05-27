package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FrameGUI extends JFrame {
    public FrameGUI(){
        super("PANG");
    }

    public void showFrame(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenu menuOption = new JMenu("Options");
        JMenu menuHelp = new JMenu("Help");
        JMenuItem registerItem = new JMenuItem("Register");
        JMenuItem quitItem = new JMenuItem("Quit");
        JMenuItem difficultyItem = new JMenuItem("Difficulty");
        JMenuItem historyItem = new JMenuItem("History");
        JMenuItem hScoreItem = new JMenuItem("High Score");
        JMenuItem aboutItem = new JMenuItem("About");

        menuBar.add(menuGame);
        menuBar.add(menuOption);
        menuBar.add(menuHelp);

        menuGame.add(registerItem);
        menuGame.add(quitItem);

        menuOption.add(difficultyItem);
        menuOption.add(historyItem);
        menuOption.add(hScoreItem);

        menuHelp.add(aboutItem);

        this.setJMenuBar(menuBar);
        
        this.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(120, 50));

        this.add(startButton, new GridBagConstraints());
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(384 * Main.ratioConsotant, 208 * Main.ratioConsotant));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

}
