package models;

public class Field {
    private Coordinate coordinate;
    private boolean hasMine;
    private boolean isOpened;
    private int neighbourMineCount;
    private boolean hasFlag;

    public Field(Coordinate coord) {
        this.coordinate = coord;
        this.hasMine = false;
        this.isOpened = false;
        this.neighbourMineCount = 0;
        this.hasFlag = false;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean flag) {
        this.hasFlag = flag;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setHasMine(boolean mine) {
        this.hasMine = mine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        this.isOpened = opened;
    }

    public int getNeighbourMineCount() {
        return neighbourMineCount;
    }

    public void setNeighbourMineCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        this.neighbourMineCount = count;
    }
}
