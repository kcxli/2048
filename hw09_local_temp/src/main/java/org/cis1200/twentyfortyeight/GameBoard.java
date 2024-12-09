package org.cis1200.twentyfortyeight;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;


@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private TwentyFortyEight game;
    private boolean playing = false;
    private LinkedList<TwentyFortyEight> moves;
    private JLabel status;


    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        moves = new LinkedList<>();
        game = new TwentyFortyEight();
        this.status = statusInit;
        boolean moved = false;
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("Key Pressed: " + KeyEvent.getKeyText(e.getKeyCode()));

                if (!playing) {
                    return;
                }

                boolean moved = false;

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (game.left()) {
                        moved = true;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (game.right()) {
                        moved = true;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (game.down()) {
                        moved = true;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (game.up()) {
                        moved = true;
                    }
                }
                repaint();

                if (moved) {
                    moves.add(copyOf(game));
                    setStatus("Running...");
                    game.addRandomTile();

                    if (game.isWon()) {
                        setStatus("You Win!");
                    }

                    if (game.isLost()) {
                        setStatus("You Lost!");
                    }

                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
    }

    public void undo() {
        if (moves.size() < 2) {
            status.setText("No moves to undo");
        } else {
            moves.removeLast();
            game = copyOf(moves.getLast());
            status.setText("Move undone.");
        }
        focus();
        repaint();
    }



    private TwentyFortyEight copyOf(TwentyFortyEight original) {
        int[][] originalBoard = original.getBoard();
        int[][] newBoard = new int[4][4];

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                newBoard[i][j] = originalBoard[i][j];
            }
        }
        TwentyFortyEight copy = new TwentyFortyEight(newBoard);

        return copy;
    }

    public void saveGame(Writer writer) throws IOException {
        try {
            int[][] currentBoard = game.getBoard();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    writer.write(currentBoard[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.flush();
        } catch (IOException e) {
            throw new IOException("Error while saving game: " + e.getMessage());
        }
    }

    public void loadGame(Reader reader) {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            int[][] newBoard = new int[4][4];
            for (int i = 0; i < 4; i++) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    throw new IOException("File is not formatted correctly.");
                }
                String[] values = line.trim().split("\\s+");
                if (values.length != 4) {
                    throw new IOException("File is not formatted correctly.");
                }
                for (int j = 0; j < 4; j++) {
                    newBoard[i][j] = Integer.parseInt(values[j]);
                }
            }
            game = new TwentyFortyEight(newBoard);
            moves = new LinkedList<>();
            moves.add(copyOf(game));
            repaint();
            status.setText("Game loaded successfully.");
        } catch (IOException e) {
            status.setText("Failed to load game: " + e.getMessage());
        } catch (NumberFormatException e) {
            status.setText("File contains invalid numbers.");
        }
    }


    public void setStatus(String s) {

        boolean won = game.isWon();
        boolean lost = game.isLost();

        if (won) {
            status.setText("You win!");
        } else if (lost) {
            status.setText("You lost!");
        } else {
            status.setText(s);
        }
    }

    public void focus() {
        requestFocusInWindow();
    }

    public void startGame() {
        playing = true;
        game.startGame();
        setStatus("Running...");
        moves = new LinkedList<>();
        moves.add(copyOf(game));
        repaint();
        focus();
    }

    public void reset() {
        playing = true;
        startGame();
        focus();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.drawBoard(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
}
