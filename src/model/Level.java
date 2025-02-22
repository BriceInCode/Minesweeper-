package model;

/**
 * Représente un niveau de jeu pour le Démineur. Le niveau est défini par la taille de la grille,
 * le nombre de mines présentes et un label décrivant le niveau.
 */
public class Level {
    private int width;
    private int height;
    private int mineCount;
    private String label;

    /**
     * Constructeur permettant de définir un niveau personnalisé.
     *
     * @param width     Largeur de la grille.
     * @param height    Hauteur de la grille.
     * @param mineCount Nombre de mines dans la grille.
     * @param label     Nom du niveau.
     */
    public Level(int width, int height, int mineCount, String label) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.label = label;
    }

    /**
     * Constructeur permettant de définir un niveau personnalisé avec un label par défaut "Custom".
     *
     * @param width     Largeur de la grille.
     * @param height    Hauteur de la grille.
     * @param mineCount Nombre de mines dans la grille.
     */
    public Level(int width, int height, int mineCount) {
        this(width, height, mineCount, "Custom");
    }

    /**
     * Retourne la hauteur de la grille.
     *
     * @return La hauteur de la grille.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la largeur de la grille.
     *
     * @return La largeur de la grille.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne le nombre de mines dans la grille.
     *
     * @return Le nombre de mines dans la grille.
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * Retourne le nom du niveau.
     *
     * @return Le nom du niveau.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Retourne une représentation textuelle du niveau.
     *
     * @return Une chaîne de caractères représentant le niveau sous la forme "Label :(largeur x hauteur) nombre de mines".
     */
    @Override
    public String toString() {
        return String.format("%s :(%dx%d) %d mines", label, width, height, mineCount);
    }

    /**
     * Crée un niveau prédéfini "Beginner" (9x9 avec 10 mines).
     *
     * @return Le niveau "Beginner".
     */
    public static Level getBeginner() {
        return new Level(9, 9, 10, "Beginner");
    }

    /**
     * Crée un niveau prédéfini "Intermediate" (16x16 avec 40 mines).
     *
     * @return Le niveau "Intermediate".
     */
    public static Level getIntermediate() {
        return new Level(16, 16, 40, "Intermediate");
    }

    /**
     * Crée un niveau prédéfini "Expert" (30x16 avec 99 mines).
     *
     * @return Le niveau "Expert".
     */
    public static Level getExpert() {
        return new Level(30, 16, 99, "Expert");
    }
}
