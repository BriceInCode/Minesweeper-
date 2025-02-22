package views;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * La classe MinesweeperGame gère l'interface graphique du jeu Démineur en utilisant Swing.
 * Elle permet de créer la fenêtre, afficher la grille, gérer les interactions de l'utilisateur
 * (clic gauche pour révéler un champ, clic droit pour poser un drapeau) et afficher l'état du jeu.
 */
public class MinesweeperGame extends JFrame {
    private Board board;                  // La grille de jeu contenant les champs et les mines
    private JPanel boardPanel;            // Le panneau pour afficher la grille
    private JLabel statusLabel;           // Étiquette pour afficher l'état du jeu (victoire, défaite)

    /**
     * Constructeur de la classe MinesweeperGame.
     * Crée une fenêtre avec un titre, un panneau de jeu, et un statut.
     * Initialise également la grille avec des mines placées au hasard en fonction du niveau.
     *
     * @param level Niveau de difficulté du jeu (définit la taille de la grille et le nombre de mines).
     */
    public MinesweeperGame(Level level) {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Minesweeper Game");
        add(statusLabel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(level.getHeight(), level.getWidth()));
        add(boardPanel, BorderLayout.CENTER);

        // Générer les mines de manière aléatoire
        Set<Coordinate> mines = new HashSet<>();
        Random random = new Random();
        while (mines.size() < level.getMineCount()) {
            int x = random.nextInt(level.getWidth());
            int y = random.nextInt(level.getHeight());
            mines.add(new Coordinate(x, y));
        }

        // Créer l'instance du Board avec les mines générées
        board = new Board(level.getWidth(), level.getHeight(), mines);
        initializeBoardPanel();
    }

    /**
     * Initialise le panneau de jeu en ajoutant des boutons pour chaque champ de la grille.
     * Chaque bouton écoute les clics de souris pour interagir avec le jeu.
     */
    private void initializeBoardPanel() {
        boardPanel.removeAll();
        for (int y = 0; y < board.getFields().size() / board.getWidth(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.addMouseListener((MouseListener) new FieldMouseListener(new Coordinate(x, y)));
                boardPanel.add(button);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Classe interne qui écoute les clics de souris sur les champs du jeu.
     * Elle gère les clics gauche (révéler un champ) et droit (poser un drapeau).
     */
    private class FieldMouseListener extends MouseAdapter {
        private Coordinate coordinate;

        public FieldMouseListener(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                // Clic gauche : révéler le champ
                RevealFieldsResult result = board.revealFields(coordinate);
                updateBoard(result);
                if (result.getState() == RevealFieldsResult.RevealFieldState.FOUND_MINE) {
                    statusLabel.setText("Game Over!"); // Si une mine est trouvée
                } else if (board.hasWon()) {
                    statusLabel.setText("You Won!"); // Si l'utilisateur a gagné
                }
            } else if (SwingUtilities.isRightMouseButton(e)) {
                // Clic droit : poser un drapeau
                board.flagField(coordinate);
                updateBoard(new RevealFieldsResult()); // Mise à jour de l'affichage
            }
        }
    }

    /**
     * Met à jour l'affichage de la grille après chaque action (révélation de champ ou ajout de drapeau).
     *
     * @param result Résultat de la révélation des champs (champ ouvert, mine trouvée, etc.).
     */
    private void updateBoard(RevealFieldsResult result) {
        for (Field field : result.getRevealedFields()) {
            Coordinate coord = field.getCoordinate();
            Component component = boardPanel.getComponent(coord.getY() * board.getWidth() + coord.getX());
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (field.isOpened()) {
                    if (field.hasMine()) {
                        button.setText("M"); // Afficher 'M' pour mine
                    } else {
                        int count = field.getNeighbourMineCount();
                        button.setText(count > 0 ? String.valueOf(count) : ""); // Afficher le nombre de mines voisines
                    }
                    button.setEnabled(false); // Désactiver le bouton une fois ouvert
                } else if (field.hasFlag()) {
                    button.setText("F"); // Afficher 'F' pour drapeau
                } else {
                    button.setText(""); // Laisser le bouton vide s'il n'est ni ouvert ni flaggé
                }
            }
        }
    }

    /**
     * Méthode principale pour lancer le jeu.
     * Crée une instance du jeu avec un niveau par défaut et la rend visible.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Level level = Level.getBeginner(); // Par défaut, niveau débutant
            MinesweeperGame game = new MinesweeperGame(level);
            game.setVisible(true); // Afficher la fenêtre
        });
    }
}
