package models;

public class Level {
    private int width;
    private int height;
    private int mineCount;
    private String label;

    public Level(int width, int height, int mineCount, String label) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.label = label;
    }

    public Level(int width, int height, int mineCount) {
        this(width, height, mineCount, "Custom");
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMineCount() {
        return mineCount;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return String.format("%s :(%dx%d) %d mines", label, width, height, mineCount);
    }

    public static Level getBeginner() {
        return new Level(9, 9, 10, "Beginner");
    }

    public static Level getIntermediate() {
        return new Level(16, 16, 40, "Intermediate");
    }

    public static Level getExpert() {
        return new Level(30, 16, 99, "Expert");
    }
}
