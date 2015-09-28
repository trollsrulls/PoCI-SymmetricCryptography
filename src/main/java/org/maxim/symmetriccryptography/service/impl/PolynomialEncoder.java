package org.maxim.symmetriccryptography.service.impl;

import org.maxim.symmetriccryptography.service.Encoder;

public class PolynomialEncoder implements Encoder {

    private static final String EN_ALPHABET = " .-!_abcdefghijklmnopqrstuvwxyz";
    private static final String SEPARATOR = " ";

    private String alphabet;

    public PolynomialEncoder() {
        this(EN_ALPHABET);
    }

    public PolynomialEncoder(String alphabet) {
        this.alphabet = alphabet;
    }

    private int encryptFunc(int x) {
        return (x * x * x + 2 * x * x + 3 * x + 4) % 991;
    }

    private int decryptFunc(int x) {
        for (int i = 0; i < alphabet.length(); i++) {
            if (x == encryptFunc(i)) {
                return i;
            }
        }
        return -1;
    }

    public String encrypt(String msg) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char symbol = msg.charAt(i);
            int symbolPos = alphabet.indexOf(Character.toLowerCase(symbol));

            if (symbolPos != -1) {
                int encryptedLetter = encryptFunc(symbolPos);
                builder.append(encryptedLetter).append(SEPARATOR);
            } else {
                builder.append(symbol).append(SEPARATOR);
            }
        }
        return builder.toString();
    }

    public String decrypt(String msg) {
        StringBuilder builder = new StringBuilder();
        String[] codes = msg.split(SEPARATOR);
        for (String strCode : codes) {
            try {
                int letterCode = Integer.parseInt(strCode);
                int alphabetPos = decryptFunc(letterCode);
                char symbol = alphabet.charAt(alphabetPos);
                builder.append(symbol);
            } catch (RuntimeException e) {
                builder.append(strCode);
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Polynomial encoder";
    }

}
