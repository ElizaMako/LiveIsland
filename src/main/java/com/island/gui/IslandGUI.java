package main.java.com.island.gui;

import javax.swing.*;
import java.awt.*;

public class IslandGUI {
    private JFrame frame;
    private JPanel gridPanel;
    private JLabel[][] gridLabels;
    private int rows;
    private int cols;

    public IslandGUI(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        frame = new JFrame("Island Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());

        gridPanel = new JPanel(new GridLayout(rows, cols));
        gridLabels = new JLabel[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridLabels[i][j] = new JLabel("", SwingConstants.CENTER);
                gridLabels[i][j].setFont(new Font("Serif", Font.PLAIN, 25));
                gridPanel.add(gridLabels[i][j]);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void updateCell(int row, int col, String unicode) {
        gridLabels[row][col].setText(unicode);
    }
}

