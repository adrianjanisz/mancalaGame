package mancala;
import java.io.Serializable;

public class Pit implements Serializable, Countable {
    private static final long serialVersionUID = 1L;

    private int numOfStonesInPit;

    public Pit() {
        numOfStonesInPit = 0;
    }

    @Override
    public void addStone() {
        numOfStonesInPit++;
    }

    @Override
    public int getStoneCount() {
        return numOfStonesInPit;
    }

    @Override
    public int removeStones() {
        final int stonesFromPit = numOfStonesInPit;
        numOfStonesInPit = 0;
        return stonesFromPit;
    }

    @Override
    public void addStones(final int numToAdd) {
        numOfStonesInPit = numOfStonesInPit + numToAdd;
    }

    @Override
    public String toString() {
        return "Stones in Pit: " + numOfStonesInPit;
    }
}