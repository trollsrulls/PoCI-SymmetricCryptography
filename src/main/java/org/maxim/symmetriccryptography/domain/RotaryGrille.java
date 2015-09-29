package org.maxim.symmetriccryptography.domain;

public class RotaryGrille {

    private static final int[][] DEFAULT_MASK = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 1, 1, 0, 0, 1}
    };

    private int[][] mask;

    public RotaryGrille() {
        this(DEFAULT_MASK);
    }

    public RotaryGrille(int[][] mask) {
        this.mask = mask;
    }

    public int getRows() {
        return mask.length;
    }

    public int getItem(int i, int j) {
        return mask[i][j];
    }

    public int getCols() {
        return mask.length > 0 ? mask[0].length : 0;
    }

    public boolean isSet(int i, int j) {
        return mask[i][j] == 1;
    }

    public static RotaryGrille flipGrille(RotaryGrille grille) {
        int[][] mask = new int[grille.getRows()][grille.getCols()];

        int newRow = 0;
        int newCol = 0;
        for (int i = 0; i < grille.getRows(); i++) {
            for (int j = grille.getCols() - 1; j >= 0; j--) {
                mask[newRow][newCol++] = grille.getItem(i, j);
            }
            newRow++;
            newCol = 0;
        }
        return new RotaryGrille(mask);
    }

    public static RotaryGrille rotate180(RotaryGrille grille) {
        int[][] mask = new int[grille.getRows()][grille.getCols()];

        int newRow = 0;
        int newCol = 0;
        for (int i = grille.getRows() - 1; i >= 0; i--) {
            for (int j = grille.getCols() - 1; j >= 0; j--) {
                mask[newRow][newCol++] = grille.getItem(i, j);
            }
            newRow++;
            newCol = 0;
        }
        return new RotaryGrille(mask);
    }

}
