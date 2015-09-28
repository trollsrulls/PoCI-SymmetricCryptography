package org.maxim.symmetriccryptography.bean;

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

}
