package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameGUI extends JFrame{
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
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                remove(startButton);
                setLayout(new BorderLayout(0, 0));

                GameGUI gameScreen = new GameGUI();

                add(gameScreen, BorderLayout.CENTER);

                revalidate();
                repaint();
            }
        });

        this.add(startButton, new GridBagConstraints());
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(384 * Main.ratioConsotant, 270 * Main.ratioConsotant));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
