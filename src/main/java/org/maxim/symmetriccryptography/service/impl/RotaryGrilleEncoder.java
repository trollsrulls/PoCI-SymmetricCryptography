package org.maxim.symmetriccryptography.service.impl;

import org.maxim.symmetriccryptography.bean.RotaryGrille;
import org.maxim.symmetriccryptography.service.Encoder;

public class RotaryGrilleEncoder implements Encoder {

    private RotaryGrille mask;

    public RotaryGrilleEncoder() {
        this(new RotaryGrille());
    }

    public RotaryGrilleEncoder(RotaryGrille mask) {
        this.mask = mask;
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
