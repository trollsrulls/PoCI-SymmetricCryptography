package org.maxim.symmetriccryptography.service.impl;

import org.maxim.symmetriccryptography.domain.RotaryGrille;
import org.maxim.symmetriccryptography.service.Encoder;

public class RotaryGrilleEncoder implements Encoder {

    private RotaryGrille mask;

    public RotaryGrilleEncoder() {
        this(new RotaryGrille());
    }

    public RotaryGrilleEncoder(RotaryGrille mask) {
        this.mask = mask;
    }

    private int fillForPattern(char[][] bundle, RotaryGrille mask, String msg, int pos) {
        for (int i = 0; i < mask.getRows(); i++) {
            for (int j = 0; j < mask.getCols(); j++) {
                if (mask.isSet(i, j)) {
                    try {
                        bundle[i][j] = msg.charAt(pos++);
                    } catch (IndexOutOfBoundsException e) {
                        return pos;
                    }
                }
            }
        }
        return pos;
    }

    private void getForPattern(char[][] bundle, RotaryGrille mask, StringBuilder msgBuilder) {
        for (int i = 0; i < mask.getRows(); i++) {
            for (int j = 0; j < mask.getCols(); j++) {
                if (mask.isSet(i, j)) {
                    msgBuilder.append(bundle[i][j]);
                }
            }
        }
    }

    public String encrypt(String msg) {
        int maxSize = mask.getRows() * mask.getCols();
        if (msg.length() > maxSize) {
            throw new IllegalArgumentException("Rotary grille is too small for this message.");
        }

        char[][] tempBundle = new char[mask.getRows()][mask.getCols()];
        int currentCharPos = 0;

        currentCharPos = fillForPattern(tempBundle, mask, msg, currentCharPos);

        RotaryGrille rotatedOriginGrille = RotaryGrille.rotate180(mask);
        currentCharPos = fillForPattern(tempBundle, rotatedOriginGrille, msg, currentCharPos);

        RotaryGrille flippedGrille = RotaryGrille.flipGrille(mask);
        currentCharPos = fillForPattern(tempBundle, flippedGrille, msg, currentCharPos);

        RotaryGrille rotatedFlippedGrille = RotaryGrille.rotate180(flippedGrille);
        fillForPattern(tempBundle, rotatedFlippedGrille, msg, currentCharPos);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mask.getRows(); i++) {
            for (int j = 0; j < mask.getCols(); j++) {
                builder.append(tempBundle[i][j]);
            }
        }
        return builder.toString();
    }

    public String decrypt(String msg) {
        int maxSize = mask.getRows() * mask.getCols();
        if (msg.length() > maxSize) {
            throw new IllegalArgumentException("Rotary grille is too small for this message.");
        }

        char[][] tempBundle = new char[mask.getRows()][mask.getCols()];
        int symbolPos = 0;
        for (int i = 0; i < mask.getRows(); i++) {
            for (int j = 0; j < mask.getCols(); j++) {
                tempBundle[i][j] = msg.charAt(symbolPos++);
            }
        }

        StringBuilder builder = new StringBuilder();

        getForPattern(tempBundle, mask, builder);

        RotaryGrille rotatedOriginGrille = RotaryGrille.rotate180(mask);
        getForPattern(tempBundle, rotatedOriginGrille, builder);

        RotaryGrille flippedGrille = RotaryGrille.flipGrille(mask);
        getForPattern(tempBundle, flippedGrille, builder);

        RotaryGrille rotatedFlippedGrille = RotaryGrille.rotate180(flippedGrille);
        getForPattern(tempBundle, rotatedFlippedGrille, builder);

        return builder.toString();
    }

    @Override
    public String toString() {
        return "Rotary grille encoder";
    }

}
