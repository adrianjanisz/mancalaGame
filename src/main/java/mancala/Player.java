package mancala;
import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private Store playerStore;
    private String playerName;
    private int kGames;
    private int aGames;
    private int kWins;
    private int aWins;

    public Player(final String inputName) {
        this.playerName = inputName;
        this.kGames = 0;
        this.aGames = 0;
        this.kWins = 0;
        this.aWins = 0;
    }

    public Player() {
        this.playerName = "Unnamed Player";
        this.kGames = 0;
        this.aGames = 0;
        this.kWins = 0;
        this.aWins = 0;
    }

    public String getName() {
        return playerName;
    }

    public Store getStore() {
        return playerStore;
    }

    public int getStoreCount() {
        return playerStore.getTotalStones();
    }

    public void setName(final String name) {
        playerName = name;
    }

    public void setStore(final Store store) {
        playerStore = store;
    }

    public void incrementWins(final int gameType) {
        if (gameType == 1) {
            kWins++;
            kGames++;
        } else {
            aWins++;
            aGames++;
        }
    }

    public void incrementGames(final int gameType) {
        if (gameType == 1) {
            kGames++;
        } else {
            aGames++;
        }
    }

    public int getKGames() {
        return kGames;
    }

    public int getAGames() {
        return aGames;
    }

    public int getKWins() {
        return kWins;
    }

    public int getAWins() {
        return aWins;
    }

    @Override
    public String toString() {
        return "Player Name: " + playerName;
    }
}