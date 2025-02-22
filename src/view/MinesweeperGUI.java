package view;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MinesweeperGUI extends JFrame {
    private Board board;
    private JPanel mineFieldPanel;
    private JButton[][] buttons;
    private boolean debugMode = false;
    private Timer timer;
    private int timeElapsed;
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private boolean gamePaused;
    private int score;

    public MinesweeperGUI(Level level) {
        board = new Board(level.getWidth(), level.getHeight(), generateMines(level.getMineCount(), level.getWidth(), level.getHeight()));
        setTitle("Démineur - " + level.getLabel());
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mineFieldPanel = new JPanel(new GridLayout(level.getHeight(), level.getWidth()));
        buttons = new JButton[level.getWidth()][level.getHeight()];

        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.addMouseListener(new FieldMouseListener(x, y));
                buttons[x][y] = button;
                mineFieldPanel.add(button);
            }
        }

        add(mineFieldPanel, BorderLayout.CENTER);

        // Ajout du minuteur et des boutons de contrôle
        timeLabel = new JLabel("Temps écoulé: 0 s");
        scoreLabel = new JLabel("Score: 0");
        startButton = new JButton("Démarrer");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Réinitialiser");

        JPanel controlPanel = new JPanel();
        controlPanel.add(timeLabel);
        controlPanel.add(scoreLabel);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Initialisation du minuteur
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                timeLabel.setText("Temps écoulé: " + timeElapsed + " s");
            }
        });

        // Ajout des écouteurs pour les boutons
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning()) {
                    timer.start();
                    gamePaused = false;
                    enableFieldButtons(true);
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    gamePaused = true;
                    enableFieldButtons(false);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timeElapsed = 0;
                timeLabel.setText("Temps écoulé: 0 s");
                score = 0;
                scoreLabel.setText("Score: 0");
                resetGame();
                enableFieldButtons(true);
            }
        });
    }

    private Collection<Coordinate> generateMines(int mineCount, int width, int height) {
        Set<Coordinate> mines = new HashSet<>();
        while (mines.size() < mineCount) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            mines.add(new Coordinate(x, y));
        }
        return mines;
    }

    private void updateButton(JButton button, Field field) {
        if (field.isOpened()) {
            if (field.hasMine()) {
                button.setText("💣");
            } else {
                int neighbourMines = field.getNeighbourMineCount();
                button.setText(neighbourMines > 0 ? String.valueOf(neighbourMines) : "");
                score++;
                scoreLabel.setText("Score: " + score);
            }
            button.setEnabled(false);
        } else if (field.hasFlag()) {
            button.setText("🏴");
        } else {
            button.setText("");
        }

        if (debugMode && field.hasMine()) {
            button.setText("💣");
        }
    }

    private class FieldMouseListener extends MouseAdapter {
        private int x;
        private int y;

        public FieldMouseListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (gamePaused) return;

            if (e.getButton() == MouseEvent.BUTTON1) {
                RevealFieldsResult result = board.revealFields(new Coordinate(x, y));
                for (Field field : result.getRevealedFields()) {
                    updateButton(buttons[field.getCoordinate().getX()][field.getCoordinate().getY()], field);
                }
                if (result.getState() == RevealFieldsResult.RevealFieldState.FOUND_MINE) {
                    JOptionPane.showMessageDialog(MinesweeperGUI.this, "Partie terminée ! Vous avez touché une mine.\nDurée: " + timeElapsed + " s\nScore: " + score);
                    revealAllMines();
                    timer.stop();
                } else if (board.hasWon()) {
                    JOptionPane.showMessageDialog(MinesweeperGUI.this, "Félicitations ! Vous avez gagné !\nDurée: " + timeElapsed + " s\nScore: " + score);
                    timer.stop();
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                board.flagField(new Coordinate(x, y));
                updateButton(buttons[x][y], board.getFields().stream()
                        .filter(f -> f.getCoordinate().equals(new Coordinate(x, y)))
                        .findFirst()
                        .orElse(null));
            }
        }
    }

    private void revealAllMines() {
        for (Field field : board.getMines()) {
            JButton button = buttons[field.getCoordinate().getX()][field.getCoordinate().getY()];
            button.setText("💣");
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        board = new Board(board.getWidth(), board.getHeight(), generateMines(board.getMineCount(), board.getWidth(), board.getHeight()));
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                buttons[x][y].setText("");
                buttons[x][y].setEnabled(true);
            }
        }
    }

    private void enableFieldButtons(boolean enable) {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                buttons[x][y].setEnabled(enable);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Level level = Level.getBeginner();
            MinesweeperGUI gui = new MinesweeperGUI(level);
            gui.setVisible(true);
        });
    }
}
