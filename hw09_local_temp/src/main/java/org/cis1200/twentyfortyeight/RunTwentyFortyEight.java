package org.cis1200.twentyfortyeight;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;


public class RunTwentyFortyEight implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("TicTacToe");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Currently Running");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> {
            board.undo();
            board.focus();
        });
        control_panel.add(undo);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "2048 Instructions:\n" +
                            "2048 is a is a single-player sliding tile puzzle video game \n" +
                            "written by Italian web developer Gabriele Cirulli in 2014.\n" +
                            "Your goal in 2048 is to combine tiles to add up to the 2048 tile.\n" +
                            "Be careful to not fill up the entire grid... or you will lose!\n" +
                            "Move and merge the tiles using the up, down, left, and right arrow\n" +
                            "keys. If two of the same number tile are merged together, they \n" +
                            "will combine to create a new tile that is the sum of the two.\n" +
                            "Press reset to start your game over, undo to undo your most recent\n" +
                            "move, save to save a file of the current game board, and load game\n" +
                            "to upload a previously saved game file onto the board. Best of luck!",
                    "Instructions", JOptionPane.INFORMATION_MESSAGE);
            board.focus();
        });

        control_panel.add(instructions);

        final JButton saveGame = new JButton("Save");
        saveGame.addActionListener(e -> {
            try {
                JFileChooser chooser =
                        new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setDialogTitle("Save Game");
                int returnVal = chooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    board.saveGame(new FileWriter(file));
                }
            } catch (IOException ex) {
                board.setStatus("File not valid.");
            } finally {
                board.focus();
            }
        });

        control_panel.add(saveGame);

        final JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(e -> {
            try {
                JFileChooser chooser =
                        new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setDialogTitle("Load Game");
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    board.loadGame(new FileReader(file)); // Pass the file reader
                }
            } catch (FileNotFoundException ex) {
                board.setStatus("File not found.");
            }
        });

        control_panel.add(loadGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.startGame();
    }
}