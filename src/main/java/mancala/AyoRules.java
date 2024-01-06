package mancala;
import java.io.Serializable;

public class AyoRules extends GameRules implements Serializable {
    private static final long serialVersionUID = 1L;
    private int skipPit;

    public AyoRules() {
        resetBoard();
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        skipPit = startPit;
        final MancalaDataStructure data = getDataStructure();
        final int storeStart = data.getStoreCount(playerNum);
        final int valueHolder = distributeStones(startPit);
        final int storeMid = data.getStoreCount(playerNum);
        int valueHolder2;
        int tempValue = startPit + valueHolder + (storeStart - storeMid);
        if (tempValue > 12) {
            tempValue = tempValue - 12;
        }
        if (1 <= tempValue && tempValue <= 6 && playerNum == 1) {
            valueHolder2 = captureStones(tempValue);
        } else if (7 <= tempValue && tempValue <= 12 && playerNum == 2) {
            valueHolder2 = captureStones(tempValue);
        } else {
            valueHolder2 = 0;
        }
        data.addToStore(playerNum, valueHolder2);
        final int storeEnd = data.getStoreCount(playerNum);
        return storeEnd - storeStart;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    int distributeStones(final int startPit) {
        final MancalaDataStructure data = getDataStructure();
        final int stonesFromPit = data.removeStones(startPit);
        int forLoopLimit = startPit + stonesFromPit;
        int ifPass = 0;
        final int currentPlayer = giveCurrentPlayer();
        for (int i = startPit + 1; i < forLoopLimit + 1; i++) {
            if (i == skipPit) {
                forLoopLimit++;
            } else if (i == 13) {
                if (currentPlayer == 2 && ifPass == 0) {
                    data.addToStore(currentPlayer, 1);
                    i--;
                    ifPass = 1;
                    forLoopLimit = forLoopLimit - 1;
                } else {
                    i = 1;
                    forLoopLimit = forLoopLimit - 12;
                    data.addStones(i, 1);
                }
            } else if (i == 7) {
                if (currentPlayer == 1 && ifPass == 0) {
                    data.addToStore(currentPlayer, 1);
                    i--;
                    ifPass = 1;
                    forLoopLimit = forLoopLimit - 1;
                } else {
                    data.addStones(i, 1);
                }
            } else {
                data.addStones(i, 1);
            }
        }
        return stonesFromPit;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    int captureStones(final int stoppingPoint) {
        final MancalaDataStructure data = getDataStructure();
        int amountToCapture;
        if (data.getNumStones(stoppingPoint) == 1) {
            amountToCapture = data.getNumStones(stoppingPoint) + data.getNumStones(13 - stoppingPoint) - 1;
            data.removeStones(stoppingPoint);
            data.removeStones(13 - stoppingPoint);
            data.addStones(stoppingPoint, 1);
        } else {
            amountToCapture = 0;
        }
        return amountToCapture;
    }

}