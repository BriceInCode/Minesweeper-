package model;

/**
 * La classe Field représente un champ individuel dans le jeu Minesweeper.
 * Chaque champ peut contenir une mine, être ouvert, avoir un nombre de mines voisines et une possibilité de drapeau.
 */
public class Field {

    // Coordonnée du champ sur le plateau
    private Coordinate coordinate;

    // Indicateur si le champ contient une mine ou non
    private boolean hasMine;

    // Indicateur si le champ a été ouvert (révélé) ou non
    private boolean isOpened;

    // Le nombre de mines voisines autour de ce champ
    private int neighbourMineCount;

    // Indicateur si le champ contient un drapeau
    private boolean hasFlag;

    /**
     * Constructeur pour initialiser un champ avec une coordonnée donnée.
     * Le champ est par défaut sans mine, fermé, avec un nombre de mines voisines à 0 et sans drapeau.
     *
     * @param coord La coordonnée de ce champ sur le plateau
     */
    public Field(Coordinate coord) {
        this.coordinate = coord;  // Initialisation de la coordonnée
        this.hasMine = false;     // Le champ n'a pas de mine par défaut
        this.isOpened = false;    // Le champ est fermé par défaut
        this.neighbourMineCount = 0;  // Le nombre de mines voisines est 0 au départ
        this.hasFlag = false;     // Aucun drapeau par défaut
    }

    /**
     * Récupère la coordonnée de ce champ.
     *
     * @return La coordonnée du champ
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Vérifie si ce champ contient un drapeau.
     *
     * @return true si le champ a un drapeau, sinon false
     */
    public boolean hasFlag() {
        return hasFlag;
    }

    /**
     * Définit si ce champ doit avoir un drapeau.
     *
     * @param flag true pour ajouter un drapeau, false pour l'enlever
     */
    public void setHasFlag(boolean flag) {
        this.hasFlag = flag;
    }

    /**
     * Vérifie si ce champ contient une mine.
     *
     * @return true si le champ a une mine, sinon false
     */
    public boolean hasMine() {
        return hasMine;
    }

    /**
     * Définit si ce champ contient une mine.
     *
     * @param mine true pour ajouter une mine, false pour la retirer
     */
    public void setHasMine(boolean mine) {
        this.hasMine = mine;
    }

    /**
     * Vérifie si ce champ a été ouvert (révélé).
     *
     * @return true si le champ est ouvert, sinon false
     */
    public boolean isOpened() {
        return isOpened;
    }

    /**
     * Définit si ce champ a été ouvert (révélé).
     *
     * @param opened true pour ouvrir le champ, false pour le fermer
     */
    public void setOpened(boolean opened) {
        this.isOpened = opened;
    }

    /**
     * Récupère le nombre de mines voisines autour de ce champ.
     *
     * @return Le nombre de mines voisines
     */
    public int getNeighbourMineCount() {
        return neighbourMineCount;
    }

    /**
     * Définit le nombre de mines voisines autour de ce champ.
     * Une exception est lancée si le nombre est négatif.
     *
     * @param count Le nombre de mines voisines
     * @throws IllegalArgumentException Si le nombre de mines voisines est inférieur à 0
     */
    public void setNeighbourMineCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Le nombre de mines voisines ne peut pas être négatif");
        }
        this.neighbourMineCount = count;
    }
}

