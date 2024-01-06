package ui;

import java.util.Scanner;
import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.KalahRules;
import mancala.GameRules;
import mancala.MancalaDataStructure;
import mancala.PitNotFoundException;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;

public class TextUI {

    private static KalahRules rules;
    private static Player player1;
    private static Player player2;
    private static MancalaDataStructure data;

    public static void main(String[] args) throws PitNotFoundException, InvalidMoveException, GameNotOverException {

        Scanner userIn = new Scanner(System.in);
        MancalaGame game = new MancalaGame();
        Player player1 = new Player();
        Player player2 = new Player();
        String player1Name;
        String player2Name;
        GameRules gameBoard = new KalahRules();
        int playerMove = 1;
        int valueHolder = 0;
        boolean continueGame = true;
        Saver saver = new Saver();
        String filename = "mancalaGame.ser";
        String loadingAnswer;

        // Load game 
        System.out.println("Would you like to load an existing game? Y - Yes, N - No:");
        loadingAnswer = userIn.nextLine();
        if (loadingAnswer.equals("Y")) {
            game = (MancalaGame) saver.loadObject(filename);
            // Start game 
            System.out.println("Game is continuing with the following players: ");
            System.out.println(game.getPlayers());
            System.out.println("Printed below is the board: \n");
            System.out.println(game.getBoard());
        } else {
            // Set board and players 
            System.out.println("Enter first player's name: ");
            player1Name = userIn.nextLine();
            System.out.println("Enter second player's name: ");
            player2Name = userIn.nextLine();
            player1.setName(player1Name);
            player2.setName(player2Name);
            game.setPlayers(player1, player2);
            // Start game 
            System.out.println("Game is starting with the following players: ");
            System.out.println(game.getPlayers());
            System.out.println("Printed below is the board: \n");
            System.out.println(game.getBoard());
        }

        while (continueGame) {
            game.setCurrentPlayer(player1);
            System.out.println("Player going now: ");
            System.out.println(game.getCurrentPlayer());
            System.out.println("Enter pit to move (Options: 0 exits the users turn, 100 saves the game): ");
            playerMove = userIn.nextInt();
            while (playerMove != 0) {
                if (playerMove == 100) {
                    saver.saveObject(game, filename);
                    System.out.println("Saved.");
                    return;
                }
                try {
                    game.move(playerMove);
                } catch (InvalidMoveException ex) {
                }
                System.out.println("Board after turn: ");
                System.out.println(game.getBoard());
                System.out.println("Enter pit to move (Options: 0 exits the users turn, 100 saves the game): ");
                playerMove = userIn.nextInt();
            }
            
            if (game.isGameOver()) {
                valueHolder = 1;
                continueGame = false;
            }

            if (valueHolder == 0) {
                game.setCurrentPlayer(player2);
                System.out.println("Player going now: ");
                System.out.println(game.getCurrentPlayer()); // Ask for user input pit next line 
                System.out.println("Enter pit to move (Options: 0 exits the users turn, 100 saves the game): ");
                playerMove = userIn.nextInt();
                while (playerMove != 0) {
                    if (playerMove == 100) {
                        saver.saveObject(game, filename);
                        System.out.println("Saved.");
                        return;
                    }
                    try {
                        game.move(playerMove);
                    } catch (InvalidMoveException ex) {
                    }
                    System.out.println("Board after turn: ");
                    System.out.println(game.getBoard());
                    System.out.println("Enter pit to move (Options: 0 exits the users turn, 100 saves the game): ");
                    playerMove = userIn.nextInt();
                }
            }

            if (game.isGameOver()) {
                continueGame = false;
            }
        }
        System.out.println("Game is over. The winner is: ");
        System.out.println(game.getWinner());
    }
}