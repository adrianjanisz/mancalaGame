package mancala;
import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;

    private GameRules gameBoard;
    final private ArrayList<Player> playerList;
    private Player currentPlayer;
    private int currentPlayerNum;

    public MancalaGame() {  
        playerList = new ArrayList<>();
        gameBoard = new KalahRules();
    }

    public MancalaGame(final GameRules rules, final Player player1, final Player player2) {  
        playerList = new ArrayList<>();
        chooseGameMode(rules);
        setPlayers(player1, player2);
    }

    public GameRules getBoard() {
        return gameBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumStones(final int pitNum) throws PitNotFoundException {
        if (1 <= pitNum && pitNum <= 12) {
            return gameBoard.getNumStones(pitNum);
        } else {
            throw new PitNotFoundException();
        }
    }

    public ArrayList<Player> getPlayers() {
        return playerList;
    }

    public int getStoreCount(final Player player) throws NoSuchPlayerException {
        return player.getStoreCount();
    }

    public Player getWinner() throws GameNotOverException, PitNotFoundException {
        if (isGameOver()) {
            final int player1Store = playerList.get(0).getStoreCount();
            final int player2Store = playerList.get(1).getStoreCount();
            int gameType = 0;
            if (gameBoard instanceof AyoRules){
                gameType = 2;
            } else {
                gameType = 1;
            }
            int returnValue;
            if (player1Store > player2Store) {
                returnValue = 0;
                playerList.get(0).incrementWins(gameType);
                playerList.get(1).incrementGames(gameType);
            } else if (player1Store < player2Store) {
                returnValue = 1;
                playerList.get(1).incrementWins(gameType);
                playerList.get(0).incrementGames(gameType);
            } else {
                returnValue = 0;
                playerList.get(0).incrementWins(gameType);
                playerList.get(1).incrementGames(gameType);
            }
            return playerList.get(returnValue);
        } else {
            throw new GameNotOverException();
        }
    }

    public boolean isGameOver() throws PitNotFoundException {
        boolean valueHolder;
        valueHolder = gameBoard.isSideEmpty(1);
        if (!valueHolder) {
            valueHolder = gameBoard.isSideEmpty(7);
        }
        return valueHolder;
    }

    public int move(final int startPit) throws InvalidMoveException, PitNotFoundException {
        return gameBoard.moveStones(startPit, currentPlayerNum);
    }

    public void setBoard(final GameRules theBoard) {
        theBoard.resetBoard();
        gameBoard = theBoard;
    }

    public void setCurrentPlayer(final Player player) {
        currentPlayer = player;
        if (player.equals(playerList.get(0))) {
            currentPlayerNum = 1;
        } else {
            currentPlayerNum = 2;
        }
    }

    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        playerList.add(onePlayer);
        playerList.add(twoPlayer);
        gameBoard.registerPlayers(playerList.get(0), playerList.get(1));
        setCurrentPlayer(onePlayer);
    }

    public void startNewGame() {
        gameBoard.resetBoard();
    }

    private void chooseGameMode(final GameRules input) {
        if ("Ayo".equals(input)) {
            gameBoard = new AyoRules();
        } else {
            gameBoard = new KalahRules();
        }
    }

    public int getStoreCountStruct(final int playerNum) {
        return gameBoard.getStoreCountStruct2(playerNum);
    }

    @Override
    public String toString() {
        return "Temp";
    }
}