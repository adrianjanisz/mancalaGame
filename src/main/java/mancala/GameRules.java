package mancala;
import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private static final long serialVersionUID = 1L;
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    public MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(final int pitNum) {
        // This method can be implemented in the abstract class.
        boolean returnValue;
        int counter = 0;
        if (1 <= pitNum && pitNum <= 6) {
            for (int i = 1; i < 7; i++) {
                if (gameBoard.getNumStones(i) != 0) {
                    counter = counter + 1;
                }
            }
        } else if (7 <= pitNum && pitNum <= 12) {
            for (int i = 7; i < 13; i++) {
                if (gameBoard.getNumStones(i) != 0) {
                    counter = counter + 1;
                }
            }
        }
        if (counter == 0) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.
        final Store storeOne = new Store();
        final Store storeTwo = new Store();

        one.setStore(storeOne);
        two.setStore(storeTwo);

        gameBoard.setStore(storeOne, 1);
        gameBoard.setStore(storeTwo, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    /**
     * Provides the current player. 
     * 
     * @return The current player. 
     */
    public int giveCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Provides the current player. 
     * 
     * @param playerNum Player number being passed.
     * @return Current player's store count. 
     */
    public int getStoreCountStruct2 (int playerNum) {
        return gameBoard.getStoreCount(playerNum);
    }

    @Override
    public String toString() {
        // Implement toString() method logic here.
        String finalString = "";
        for (int i = 1; i < 7; i++) {
            finalString = finalString + "Pit " + (i) + ": " + gameBoard.getNumStones(i) + "\n";
        }
        finalString = finalString + "Player 1 Store: " + gameBoard.getStoreCount(1) + "\n";
        for (int i = 7; i < 13; i++) {
            finalString = finalString + "Pit " + (i) + ": " + gameBoard.getNumStones(i) + "\n";
        }
        finalString = finalString + "Player 2 Store: " + gameBoard.getStoreCount(2) + "\n";
        return finalString;
    }
}
