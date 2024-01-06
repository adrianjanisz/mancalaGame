package ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import mancala.MancalaGame;
import mancala.Player;
import mancala.PitNotFoundException;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
import mancala.NoSuchPlayerException;
import mancala.Saver;

public class GUI extends JFrame {

    private JPanel gameContainer;
    private JPanel titleContainer;
    private JPanel store1Container;
    private JPanel store2Container;
    private JLabel messageLabel;
    private JMenuBar menuBar;
    private PositionAwareButton[][] buttons;
    private MancalaGame game;
    private Saver saver;
    private Player player1;
    private Player player2;
    private JLabel player1Label;
    private JLabel player2Label;

    public GUI(String title) {
        super();
        game = new MancalaGame();
        saver = new Saver();
        player1 = new Player();
        player2 = new Player();
        player1.setName("A");
        player2.setName("B");
        basicSetUp(title);
        setupGameContainer();
        setupTitleContainer();
        setupStore1Container();
        setupStore2Container();
        add(titleContainer, BorderLayout.NORTH);
        add(gameContainer, BorderLayout.CENTER);
        add(store1Container, BorderLayout.EAST);
        add(store2Container, BorderLayout.WEST);
        makeMenu();
        setJMenuBar(menuBar);
        newGame();
        pack();
    }

    private void basicSetUp(String title) {
        this.setTitle(title);
        gameContainer = new JPanel();
        titleContainer = new JPanel();
        store1Container = new JPanel();
        store2Container = new JPanel();
        game.setPlayers(player1, player2);
        player1Label = new JLabel("Player " + player1.getName() + " Store: " + game.getStoreCountStruct(1));
        player2Label = new JLabel("Player " + player2.getName() + " Store: " + game.getStoreCountStruct(2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private JPanel startupMessage() {
        JPanel temp = new JPanel();
        JLabel gameTitle = new JLabel("Mancala Game");
        Font customFont = new Font(gameTitle.getFont().getName(), Font.BOLD, 40);
        Color customColor = new Color(154, 192, 252);

        gameTitle.setFont(customFont);
        temp.add(gameTitle);
        temp.setBackground(customColor);

        return temp;
    }

    public void setupTitleContainer() {
        Color customColor = new Color(154, 192, 252);

        titleContainer.add(startupMessage());
        titleContainer.setBackground(customColor);
    }

    public void setupGameContainer() {
        Color customColor = new Color(154, 192, 252);

        gameContainer.add(makeButtonGrid(6, 2));
        gameContainer.setBackground(customColor);
    }

    public void setupStore1Container() {
        Color customColor = new Color(154, 192, 252);

        store1Container.add(player1Label);
        store1Container.setBackground(customColor);
    }

    public void setupStore2Container() {
        Color customColor = new Color(154, 192, 252);

        store2Container.add(player2Label);
        store2Container.setBackground(customColor);
    }

    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[wide][tall]; 
        panel.setLayout(new GridLayout(wide, tall)); 
        int top = 12;
        int bottom = 1;

        for (int y = 0; y < wide; y++) {
            for (int x = 0; x < tall; x++) {
                final int finalY = y;
                final int finalX = x;
                int pitNum;
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1); // 1-based array
                buttons[y][x].setPreferredSize(new Dimension(100, 100));
                if (y == 0) {
                        buttons[y][x].setDown(top);
                        pitNum = top;
                        top--;
                    } else {
                        buttons[y][x].setDown(bottom);
                        pitNum = bottom;
                        bottom++;
                    }
                final int pitNumberFinal = pitNum;
                buttons[y][x].addActionListener(e -> {
                    moveStonesGUI((pitNumberFinal));
                    checkIfGameIsOver();
                });
                panel.add(buttons[y][x]);
            }
        }
        
        return panel;
    }

    private void makeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game Menu");
        JMenuItem item1 = new JMenuItem("New Game");
        JMenuItem item2 = new JMenuItem("Load Game");
        JMenuItem item3 = new JMenuItem("Save Game");

        item1.addActionListener(e -> newGame());
        item2.addActionListener(e -> loadGame());
        item3.addActionListener(e -> saveGame());

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);
    }

    protected void newGame() {
        game.startNewGame();
        game.setCurrentPlayer(player1);
        updateView(2, 6);
    }

    protected void updateView(int tall, int wide) {
        try {
            buttons[0][0].setText(String.valueOf(game.getNumStones(12)));
            buttons[0][1].setText(String.valueOf(game.getNumStones(11)));
            buttons[0][2].setText(String.valueOf(game.getNumStones(10)));
            buttons[0][3].setText(String.valueOf(game.getNumStones(9)));
            buttons[0][4].setText(String.valueOf(game.getNumStones(8)));
            buttons[0][5].setText(String.valueOf(game.getNumStones(7)));
            buttons[1][0].setText(String.valueOf(game.getNumStones(1)));
            buttons[1][1].setText(String.valueOf(game.getNumStones(2)));
            buttons[1][2].setText(String.valueOf(game.getNumStones(3)));
            buttons[1][3].setText(String.valueOf(game.getNumStones(4)));
            buttons[1][4].setText(String.valueOf(game.getNumStones(5)));
            buttons[1][5].setText(String.valueOf(game.getNumStones(6)));
            player1Label.setText("Player " + player1.getName() + " Store: " + game.getStoreCountStruct(1));
            player2Label.setText("Player " + player2.getName() + " Store: " + game.getStoreCountStruct(2));
        } catch (PitNotFoundException e) {
            buttons[0][0].setText("Error");
        } 
    }

    protected void loadGame() {
        try {
            game = (MancalaGame) saver.loadObject("mancalaGame.ser");
            updateView(2, 6);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"No game to load. Closing program.");
            System.exit(0);
        }
    }

    protected void saveGame() {
        saver.saveObject(game, "mancalaGame.ser");
        JOptionPane.showMessageDialog(this, "Game Saved. Closing program.");
        System.exit(0);
    }

    protected void moveStonesGUI(int pit) {
        try {
            game.move(pit);
            updateView(2, 6);
        } catch (InvalidMoveException e) {
            JOptionPane.showMessageDialog(this, "Program error. Closing program.");
            System.exit(0);
        } catch (PitNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Program error. Closing program.");
            System.exit(0);
        }
    }

    protected void checkIfGameIsOver() {
        try {
            boolean temp = false;
            temp = game.isGameOver();
            if (temp) {
                Player winner = game.getWinner();
                JOptionPane.showMessageDialog(this, winner.getName() + " won the game. Closing program.");
                System.exit(0);
            }
        } catch (PitNotFoundException e) {
        } catch (GameNotOverException e) {
        }
    }

    public static void main(String[] args) {
        GUI example = new GUI("Mancala Game");
        example.setVisible(true);
    }

}