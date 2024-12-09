package org.cis1200.twentyfortyeight;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    @Test void testSetBoard() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] board = new int[4][4];
        board[0][0] = 4;
        game.setBoard(board);
        assertArrayEquals(board, game.getBoard());
    }

    @Test
    public void testStartGame() {
        TwentyFortyEight start = new TwentyFortyEight();
        start.startGame();
        boolean valid = (start.getTileSum() == 4 || start.getTileSum() == 6
                || start.getTileSum() == 8);
        assertTrue(valid);
    }

    @Test
    public void testMergeLeft() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start = new int[4][4];
        start[0][0] = 2;
        start[1][0] = 2;
        game.setBoard(start);
        assertTrue(game.left());
        game.left();
        int[][] end = new int[4][4];
        end[0][0] = 4;
        assertArrayEquals(end, game.getBoard());
    }

    @Test
    public void testMergeDown() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start = new int[4][4];
        start[0][0] = 16;
        start[0][1] = 16;
        game.setBoard(start);
        assertTrue(game.down());
        game.down();
        int[][] end = new int[4][4];
        end[0][0] = 32;
        assertArrayEquals(end, game.getBoard());
    }

    @Test
    public void testMoveRight() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start = new int[4][4];
        start[0][0] = 2;
        start[0][1] = 16;
        game.setBoard(start);
        System.out.println("\n");
        game.printGameState();
        assertTrue(game.right());
        System.out.println("\n");
        game.printGameState();
        int[][] end = new int[4][4];
        end[3][0] = 2;
        end[3][1] = 16;
        assertArrayEquals(end, game.getBoard());
    }

    @Test
    public void testThreeInARow() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start = new int[4][4];
        start[0][0] = 2;
        start[0][1] = 2;
        start[0][2] = 2;
        game.setBoard(start);
        assertTrue(game.down());
        int[][] end = new int[4][4];
        end[0][0] = 4;
        end[0][1] = 2;
        assertArrayEquals(end, game.getBoard());
    }

    @Test
    public void testFourInARow() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start = new int[4][4];
        start[0][0] = 2;
        start[0][1] = 2;
        start[0][2] = 2;
        start[0][3] = 2;
        game.setBoard(start);
        assertTrue(game.down());
        int[][] end = new int[4][4];
        end[0][0] = 4;
        end[0][1] = 4;
        assertArrayEquals(end, game.getBoard());
    }

    @Test
    public void testCannotMove() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start = new int[4][4];
        start[0][0] = 2;
        game.setBoard(start);
        assertFalse(game.down());
    }

    @Test
    public void testLostGame() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start =     {{2, 4, 16, 4},
                             {4, 8, 2, 8},
                             {32, 2, 4, 64},
                             {8, 16, 8, 2}};
        game.setBoard(start);
        assertFalse(game.down());
        assertFalse(game.up());
        assertFalse(game.left());
        assertFalse(game.right());
        assertTrue(game.isLost());
    }

    @Test
    public void testWinGame() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start =     {{0, 0, 16, 4},
                             {4, 2048, 2, 8},
                             {32, 2, 1024, 0},
                             {8, 16, 8, 2}};
        game.setBoard(start);
        assertTrue(game.isWon());
    }

    @Test
    public void testAddRandomTile() {
        TwentyFortyEight game = new TwentyFortyEight();
        game.startGame();
        TwentyFortyEight game2 = new TwentyFortyEight();
        game2.startGame();
        game2.addRandomTile();
        game2.addRandomTile();

        game.printGameState();
        System.out.println("\n");

        game2.printGameState();

        boolean valid = game2.getTileSum() >= game.getTileSum();
        assertTrue(valid);
    }

    @Test
    public void testAlmostFail() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start =     {{2, 4, 16, 4},
                             {4, 8, 2, 8},
                             {32, 2, 4, 64},
                             {16, 16, 8, 2}};
        game.setBoard(start);
        assertFalse(game.isLost());
    }

    @Test
    public void testMoveMergeMultiple() {
        TwentyFortyEight game = new TwentyFortyEight();
        int[][] start =     {{2, 2, 4, 4},
                             {0, 8, 2, 0},
                             {32, 4, 4, 64},
                             {16, 0, 8, 0}};
        game.setBoard(start);
        assertTrue(game.up());
        game.printGameState();
        int[][] end =       {{0, 0, 4, 8},
                             {0, 0, 8, 2},
                             {0, 32, 8, 64},
                             {0, 0, 16, 8}};

        assertArrayEquals(end, game.getBoard());
    }

}
