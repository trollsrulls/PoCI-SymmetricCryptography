package org.maxim.symmetriccryptography.service.impl;

import org.maxim.symmetriccryptography.service.Encoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayfairEncoder implements Encoder {

    private static final String DEFAULT_KEY = "what about you?)";
    private static final String EN_ALPHABET = "abcdefghiklmnopqrstuvwxyz"; // without J

    private String key;
    private char[][] codeTable;

    public PlayfairEncoder() {
        this(DEFAULT_KEY);
    }

    public PlayfairEncoder(String key) {
        this.key = key;

        String cleanKey = clearKey(this.key);
        createCodeTable(cleanKey);
    }

    private String clearKey(final String key) {
        List<Character> charBundle = new ArrayList<Character>();
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            int alphabetPos = EN_ALPHABET.indexOf(c);
            if (alphabetPos != -1 && !charBundle.contains(c)) {
                charBundle.add(c);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char c : charBundle) {
            builder.append(c);
        }
        return builder.toString();
    }

    private void createCodeTable(final String cleanKey) {
        final int dimension = 5;

        Set<Character> alphabet = new HashSet<Character>();
        for (int i = 0; i < EN_ALPHABET.length(); i++) {
            alphabet.add(EN_ALPHABET.charAt(i));
        }
        codeTable = new char[dimension][dimension];

        int alphabetPos = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int keyPos = i * dimension + j;
                if (keyPos < cleanKey.length()) {
                    char c = cleanKey.charAt(keyPos);
                    codeTable[i][j] = c;
                    alphabet.remove(c);
                } else {
                    codeTable[i][j] = (Character) alphabet.toArray()[alphabetPos++];
                }
            }
        }
    }

    private List<String> createBigramms(String msg) {
        List<String> bigramms = new ArrayList<String>();
        for (int i = 0; i < msg.length() - 1; i++) {
            char left = msg.charAt(i);
            char right = msg.charAt(i + 1);

            if (left == right) {
                bigramms.add(String.valueOf(new char[] {left, 'x'}));
            } else {
                bigramms.add(String.valueOf(new char[] {left, right}));
                i++;
            }
        }
        return bigramms;
    }

    public String encrypt(String msg) {

        return null;
    }

    public String decrypt(String msg) {
        return null;
    }

}
