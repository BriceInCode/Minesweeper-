package model;

import java.util.Objects;

/** * Représente une coordonnée dans la grille du jeu. ** */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Constructeur de la classe Coordinate.
     *
     * @param x Coordonnée en abscisse.
     * @param y Coordonnée en ordonnée.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur permettant de créer une nouvelle coordonnée à partir d'une autre et d'un décalage.
     *
     * @param old Coordonnée de référence.
     * @param x   Décalage en abscisse.
     * @param y   Décalage en ordonnée.
     */
    public Coordinate(Coordinate old, int x, int y) {
        this(old.x + x, old.y + y);
    }

    /** * Retourne la coordonnée en abscisse.  */
    public int getX() {
        return x;
    }

    /** * Retourne la coordonnée en abscisse.  */
    public int getY() {
        return y;
    }

    /** * Retourne la coordonnée en ordonnée.  */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Vérifie si deux coordonnées sont égales.
     *
     * @param other Objet à comparer.
     * @return true si les coordonnées sont identiques, sinon false.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Coordinate that = (Coordinate) other;
        return x == that.x && y == that.y;
    }

    /** * Retourne une représentation textuelle de la coordonnée.  */
    @Override
    public String toString() {
        return String.format("(%d/%d)", x, y);
    }
}
