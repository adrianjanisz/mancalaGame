package mancala;
import java.io.Serializable;

public class Store implements Serializable, Countable {
    private static final long serialVersionUID = 1L;

    private int stonesInStore;
    private Player storeOwner = null;

    public Store() {
        stonesInStore = 0;
    }

    @Override
    public void addStones(final int amount) {
        stonesInStore = stonesInStore + amount;
    }

    public int emptyStore() {
        final int stonesFromStore = stonesInStore;
        stonesInStore = 0;
        return stonesFromStore;
    }

    public Player getOwner() {
        return storeOwner;
    }

    public int getTotalStones() {
        return stonesInStore;
    }

    public void setOwner(final Player player) {
        storeOwner = player;
    }

    @Override
    public int getStoneCount() {
        return stonesInStore;
    }

    @Override
    public void addStone() {
        stonesInStore++;
    }

    @Override
    public int removeStones() {
        final int stonesFromStore = stonesInStore;
        stonesInStore = 0;
        return stonesFromStore;
    }

    @Override
    public String toString() {
        String finalString;
        if (storeOwner != null) {
            finalString = "Number of stones in " + storeOwner + "'s store: " + stonesInStore;
        } else {
            finalString = "Stones in Store: " + stonesInStore;
        }
        return finalString;
    }
}