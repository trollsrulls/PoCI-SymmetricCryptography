package org.maxim.symmetriccryptography.service.impl;

import org.maxim.symmetriccryptography.service.Encoder;

public class RotaryGrilleEncoder implements Encoder {

    private static final boolean[][] DEFAULT_MASK = {
            {false, true, false, false, false, false, false, false, false, false},
            {true, false, false, false, true, false, true, true, false, false},
            {false, true, false, false, false, true, false, false, false, true},
            {false, false, false, true, false, false, false, true, false, false},
            {false, true, false, false, false, false, false, false, false, false},
            {false, false, true, false, false, true, true, false, false, true}
    };

    private boolean[][] mask;

    public RotaryGrilleEncoder() {
        this(DEFAULT_MASK);
    }

    public RotaryGrilleEncoder(boolean[][] mask) {
        this.mask = mask;
    }

    private boolean[][] turnMask() {
        return null;
    }

    public String encrypt(String msg) {
        return null;
    }

    public String decrypt(String msg) {
        return null;
    }

    @Override
    public String toString() {
        return "Rotary grille encoder";
    }

}
