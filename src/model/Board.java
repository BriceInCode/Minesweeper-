package model;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente la grille de jeu dans le jeu Démineur.
 * Cette classe gère les champs, les mines, les révélations de champs, le comptage des mines voisines, ainsi que la gestion des drapeaux.
 */
public class Board {
    private int width;              // Largeur de la grille
    private int height;             // Hauteur de la grille
    private Field[][] fields;       // Matrice des champs du jeu
    private Set<Field> mines;       // Ensemble des champs contenant des mines

    /**
     * Constructeur pour initialiser la grille de jeu avec les dimensions et les positions des mines.
     *
     * @param width   Largeur de la grille.
     * @param height  Hauteur de la grille.
     * @param mines   Collection des coordonnées des mines.
     */
    public Board(int width, int height, Collection<Coordinate> mines) {
        this.width = width;
        this.height = height;
        this.fields = new Field[height][width];
        this.mines = new HashSet<>();

        // Initialisation des champs sans mine
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                fields[y][x] = new Field(new Coordinate(x, y));
            }
        }

        // Placement des mines dans la grille
        for (Coordinate mine : mines) {
            Field field = fields[mine.getY()][mine.getX()];
            field.setHasMine(true);
            this.mines.add(field);
        }

        // Calcul des mines voisines pour chaque champ
        calculateNeighbourMineCounts();
    }

    /**
     * Calcule le nombre de mines voisines pour chaque champ de la grille.
     */
    private void calculateNeighbourMineCounts() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Field field = fields[y][x];
                if (!field.hasMine()) {
                    int count = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (dx == 0 && dy == 0) continue;
                            int nx = x + dx;
                            int ny = y + dy;
                            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                                if (fields[ny][nx].hasMine()) {
                                    count++;
                                }
                            }
                        }
                    }
                    field.setNeighbourMineCount(count);
                }
            }
        }
    }

    /**
     * Vérifie si le joueur a gagné.
     *
     * @return true si le joueur a gagné (tous les champs sans mine sont ouverts), false sinon.
     */
    public boolean hasWon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Field field = fields[y][x];
                if (!field.hasMine() && !field.isOpened()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Pose ou enlève un drapeau sur un champ.
     *
     * @param coord Coordonnée du champ à draper.
     */
    public void flagField(Coordinate coord) {
        Field field = fields[coord.getY()][coord.getX()];
        if (!field.isOpened()) {
            field.setHasFlag(!field.hasFlag());
        }
    }

    /**
     * Retourne le nombre de mines restantes à découvrir.
     *
     * @return Nombre de mines restantes.
     */
    public int getRemainingMines() {
        int flagCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (fields[y][x].hasFlag()) {
                    flagCount++;
                }
            }
        }
        return mines.size() - flagCount;
    }

    /**
     * Retourne la collection des champs contenant des mines.
     *
     * @return Collection des champs avec mines.
     */
    public Collection<Field> getMines() {
        return mines;
    }

    /**
     * Retourne tous les champs de la grille.
     *
     * @return Collection de tous les champs de la grille.
     */
    public Collection<Field> getFields() {
        Set<Field> allFields = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                allFields.add(fields[y][x]);
            }
        }
        return Collections.unmodifiableCollection(allFields);
    }

    /**
     * Révèle un champ spécifié par sa coordonnée.
     *
     * @param coord Coordonnée du champ à révéler.
     * @return Résultat de la révélation des champs.
     */
    public RevealFieldsResult revealFields(Coordinate coord) {
        Field field = fields[coord.getY()][coord.getX()];
        if (field.hasFlag() || field.isOpened()) {
            return new RevealFieldsResult();
        }

        Set<Field> revealedFields = new HashSet<>();
        if (revealField(field, revealedFields)) {
            return new RevealFieldsResult(revealedFields);
        } else {
            return new RevealFieldsResult();
        }
    }

    /**
     * Révèle un champ et ses voisins de manière récursive si nécessaire.
     *
     * @param field          Le champ à révéler.
     * @param revealedFields Collection des champs révélés.
     * @return true si une mine a été trouvée, false sinon.
     */
    private boolean revealField(Field field, Set<Field> revealedFields) {
        if (field.isOpened() || field.hasFlag()) {
            return false;
        }

        field.setOpened(true);
        revealedFields.add(field);

        if (field.hasMine()) {
            return true;
        }

        if (field.getNeighbourMineCount() > 0) {
            return false;
        }

        Coordinate coord = field.getCoordinate();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int nx = coord.getX() + dx;
                int ny = coord.getY() + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    revealField(fields[ny][nx], revealedFields);
                }
            }
        }
        return false;
    }

    /**
     * Révèle plusieurs champs en fonction du nombre de mines voisines.
     * Cette méthode est appelée après un clic multiple.
     *
     * @param coord Coordonnée du champ à révéler.
     * @return Résultat de la révélation des champs.
     */
    public RevealFieldsResult revealMultiClickFields(Coordinate coord) {
        Field field = fields[coord.getY()][coord.getX()];
        if (!field.isOpened() || field.getNeighbourMineCount() == 0) {
            return new RevealFieldsResult();
        }

        int flagCount = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int nx = coord.getX() + dx;
                int ny = coord.getY() + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    if (fields[ny][nx].hasFlag()) {
                        flagCount++;
                    }
                }
            }
        }

        if (flagCount != field.getNeighbourMineCount()) {
            return new RevealFieldsResult();
        }

        Set<Field> revealedFields = new HashSet<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int nx = coord.getX() + dx;
                int ny = coord.getY() + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    Field neighbour = fields[ny][nx];
                    if (!neighbour.hasFlag() && !neighbour.isOpened()) {
                        revealField(neighbour, revealedFields);
                    }
                }
            }
        }

        return new RevealFieldsResult(revealedFields);
    }
}