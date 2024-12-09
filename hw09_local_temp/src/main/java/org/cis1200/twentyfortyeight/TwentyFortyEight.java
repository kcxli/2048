package org.cis1200.twentyfortyeight;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class TwentyFortyEight {

    private int[][] board;
    public static final Random RANDOM = new Random();
    private boolean won;

    /**
     * Constructor sets up game state.
     */
    public TwentyFortyEight() {
        board = new int[4][4];
    }

    public TwentyFortyEight(int[][] initialBoard) {
        if (initialBoard == null || initialBoard.length != 4 || initialBoard[0].length != 4) {
            throw new IllegalArgumentException("Invalid board state: Must be a 4x4 array.");
        }
        board = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = initialBoard[i][j];
            }
        }
    }

    public void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 400, 400);
        BufferedImage tile2 = null;
        BufferedImage tile4 = null;
        BufferedImage tile8 = null;
        BufferedImage tile16 = null;
        BufferedImage tile32 = null;
        BufferedImage tile64 = null;
        BufferedImage tile128 = null;
        BufferedImage tile256 = null;
        BufferedImage tile512 = null;
        BufferedImage tile1024 = null;
        BufferedImage tile2048 = null;
        BufferedImage tileGrid = null;

        try {
            tileGrid = ImageIO.read(new File("files/2048-grid.png"));
            tile2 = ImageIO.read(new File("files/2_tiles.jpg"));
            tile4 = ImageIO.read(new File("files/4_tiles.jpg"));
            tile8 = ImageIO.read(new File("files/8_tiles.jpg"));
            tile16 = ImageIO.read(new File("files/16_tiles.jpg"));
            tile32 = ImageIO.read(new File("files/32_tiles.jpg"));
            tile64 = ImageIO.read(new File("files/64_tiles.jpg"));
            tile128 = ImageIO.read(new File("files/128_tiles.jpg"));
            tile256 = ImageIO.read(new File("files/256_tiles.jpg"));
            tile512 = ImageIO.read(new File("files/512_tiles.jpg"));
            tile1024 = ImageIO.read(new File("files/1024_tiles.jpg"));
            tile2048 = ImageIO.read(new File("files/2048_tiles.jpg"));
        } catch (IOException e) {
            System.out.println("Error reading tile file");
            e.printStackTrace();
        }
        g.drawImage(tileGrid, 0, 0, 400, 400, null);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2) {
                    g.drawImage(tile2, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 4) {
                    g.drawImage(tile4, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 8) {
                    g.drawImage(tile8, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 16) {
                    g.drawImage(tile16, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 32) {
                    g.drawImage(tile32, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 64) {
                    g.drawImage(tile64, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 128) {
                    g.drawImage(tile128, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 256) {
                    g.drawImage(tile256, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 512) {
                    g.drawImage(tile512, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 1024) {
                    g.drawImage(tile1024, i * 100, j * 100, 100, 100, null);
                } else if (board[i][j] == 2048) {
                    g.drawImage(tile2048, i * 100, j * 100, 100, 100, null);
                }
            }
        }
    }

    public void startGame() {
        board = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }

        addRandomTile();
        addRandomTile();

    }

    public boolean up() {
        boolean movedOrMerged = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >= 0; j--) {
                if (board[i][j] != 0 && board[i][j + 1] == 0) {
                    int k = j + 1;
                    while (k < 4 && board[i][k] == 0) {
                        k++;
                    }
                    board[i][k - 1] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int j = 2; j >= 0; j--) {
                if (board[i][j] != 0 && board[i][j + 1] == board[i][j]) {
                    board[i][j + 1] *= 2;
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int j = 2; j >= 0; j--) {
                if (board[i][j] != 0 && board[i][j + 1] == 0) {
                    int k = j + 1;
                    while (k < 4 && board[i][k] == 0) {
                        k++;
                    }
                    board[i][k - 1] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }
        }

        if (isWon()) {
            System.out.println("You won!");
        } else if (isLost()) {
            System.out.println("Game over. You lost.");
        }

        return movedOrMerged;
    }

    public boolean down() {
        boolean movedOrMerged = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j - 1] == 0) {
                    int k = j - 1;
                    while (k >= 0 && board[i][k] == 0) {
                        k--;
                    }
                    board[i][k + 1] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int j = 1; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j - 1] == board[i][j]) {
                    board[i][j - 1] *= 2;
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int j = 1; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j - 1] == 0) {
                    int k = j - 1;
                    while (k >= 0 && board[i][k] == 0) {
                        k--;
                    }
                    board[i][k + 1] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }
        }

        if (isWon()) {
            System.out.println("You won!");
        } else if (isLost()) {
            System.out.println("Game over. You lost.");
        }

        return movedOrMerged;
    }

    public boolean right() {
        boolean movedOrMerged = false;

        for (int j = 0; j < 4; j++) {
            for (int i = 2; i >= 0; i--) {
                if (board[i][j] != 0 && board[i + 1][j] == 0) {
                    int k = i + 1;
                    while (k < 4 && board[k][j] == 0) {
                        k++;
                    }
                    board[k - 1][j] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int i = 2; i >= 0; i--) {
                if (board[i][j] != 0 && board[i + 1][j] == board[i][j]) {
                    board[i + 1][j] *= 2;
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int i = 2; i >= 0; i--) {
                if (board[i][j] != 0 && board[i + 1][j] == 0) {
                    int k = i + 1;
                    while (k < 4 && board[k][j] == 0) {
                        k++;
                    }
                    board[k - 1][j] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }
        }

        if (isWon()) {
            System.out.println("You won!");
        } else if (isLost()) {
            System.out.println("Game over. You lost.");
        }

        return movedOrMerged;
    }

    public boolean left() {
        boolean movedOrMerged = false;

        for (int j = 0; j < 4; j++) {
            for (int i = 1; i < 4; i++) {
                if (board[i][j] != 0 && board[i - 1][j] == 0) {
                    int k = i - 1;
                    while (k >= 0 && board[k][j] == 0) {
                        k--;
                    }
                    board[k + 1][j] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int i = 1; i < 4; i++) {
                if (board[i][j] != 0 && board[i - 1][j] == board[i][j]) {
                    board[i - 1][j] *= 2;
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }

            for (int i = 1; i < 4; i++) {
                if (board[i][j] != 0 && board[i - 1][j] == 0) {
                    int k = i - 1;
                    while (k >= 0 && board[k][j] == 0) {
                        k--;
                    }
                    board[k + 1][j] = board[i][j];
                    board[i][j] = 0;
                    movedOrMerged = true;
                }
            }
        }

        if (isWon()) {
            System.out.println("You won!");
        } else if (isLost()) {
            System.out.println("Game over. You lost.");
        }

        return movedOrMerged;
    }

    public void addRandomTile() {
        int x = (int) (Math.random() * 10 + 1);
        if (x >= 9) {
            addRandomTile(4);
        } else {
            addRandomTile(2);
        }
    }

    public void addRandomTile(int num) {
        int i = RANDOM.nextInt(4);
        int j = RANDOM.nextInt(4);

        while (board[i][j] != 0) {
            i = RANDOM.nextInt(4);
            j = RANDOM.nextInt(4);
        }

        board[i][j] = num;
    }

    public boolean isWon() {
        if (won) {
            return true;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2048) {
                    won = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLost() {
        if (won) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == board[i][j + 1]) {
                    return false;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == board[i + 1][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getTileSum() {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sum += board[i][j];
            }
        }
        return sum;
    }


    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }

            System.out.println("\n----------------");

        }
    }


    public void setBoard(int[][] state) {
        this.board = state;
    }

    public int[][] getBoard() {
        return board;
    }

}
