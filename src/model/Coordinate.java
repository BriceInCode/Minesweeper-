package model;

import java.util.Objects;

/**
 * Représente une coordonnée dans la grille du jeu. Chaque coordonnée est composée de deux valeurs :
 * une abscisse (x) et une ordonnée (y).
 */
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

    /**
     * Retourne la coordonnée en abscisse.
     *
     * @return La coordonnée en abscisse (x).
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée en ordonnée.
     *
     * @return La coordonnée en ordonnée (y).
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne le code de hachage de la coordonnée basé sur les valeurs de x et y.
     *
     * @return Le code de hachage de la coordonnée.
     */
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

    /**
     * Retourne une représentation textuelle de la coordonnée sous la forme (x/y).
     *
     * @return La représentation sous forme de chaîne de caractères de la coordonnée.
     */
    @Override
    public String toString() {
        return String.format("(%d/%d)", x, y);
    }
}
