package org.maxim.symmetriccryptography.service.impl;

import org.maxim.symmetriccryptography.service.Encoder;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayfairEncoder implements Encoder {

    private static final String DEFAULT_KEY = "what about you?)";
    private static final String EN_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String EN_ALPHABET_FOR_ENCODING = "abcdefghijklmnoprstuvwxyz"; // without Q

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
        for (int i = 0; i < EN_ALPHABET_FOR_ENCODING.length(); i++) {
            alphabet.add(EN_ALPHABET_FOR_ENCODING.charAt(i));
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

    private List<String> createBigramms(final String message) {
        String msg = message.replace(" ", "");
        List<String> bigramms = new ArrayList<String>();
        for (int i = 0; i < msg.length() - 1; i++) {
            char left = Character.toLowerCase(msg.charAt(i));
            if (EN_ALPHABET.indexOf(left) == -1) {
                continue;
            }
            char right = Character.toLowerCase(msg.charAt(i + 1));

            final char addableSymbol = 'x';
            if (left == right) {
                bigramms.add(String.valueOf(new char[]{left, addableSymbol}));
            } else {
                if (EN_ALPHABET.indexOf(right) == -1) {
                    bigramms.add(String.valueOf(new char[]{left, addableSymbol}));
                } else {
                    bigramms.add(String.valueOf(new char[]{left, right}));
                }
                i++;
            }
        }
        return bigramms;
    }

    private Point findPos(final char letter) {
        for (int i = 0; i < codeTable.length; i++) {
            for (int j = 0; j < codeTable.length; j++) {
                if (codeTable[i][j] == letter) {
                    return new Point(j, i);
                }
            }
        }
        throw new IllegalArgumentException("Wrong symbol '" + letter + "'.");
    }

    private String getBigrammForOneRow(int row, int colFirst, int colSecond) {
        char firstLetter = codeTable[row][(colFirst + 1) % codeTable.length];
        char secondLetter = codeTable[row][(colSecond + 1) % codeTable.length];
        return "" + firstLetter + secondLetter;
    }

    private String decryptBigrammForOneRow(int row, int colFirst, int colSecond) {
        char firstLetter = codeTable[row][(colFirst - 1 + codeTable.length) % codeTable.length];
        char secondLetter = codeTable[row][(colSecond - 1 + codeTable.length) % codeTable.length];
        return "" + firstLetter + secondLetter;
    }

    private String getBigrammForOneColumn(int col, int rowFirst, int rowSecond) {
        char firstLetter = codeTable[(rowFirst + 1) % codeTable.length][col];
        char secondLetter = codeTable[(rowSecond + 1) % codeTable.length][col];
        return "" + firstLetter + secondLetter;
    }

    private String decryptBigrammForOneColumn(int col, int rowFirst, int rowSecond) {
        char firstLetter = codeTable[(rowFirst - 1 + codeTable.length) % codeTable.length][col];
        char secondLetter = codeTable[(rowSecond - 1 + codeTable.length) % codeTable.length][col];
        return "" + firstLetter + secondLetter;
    }

    private String getBigrammForRectangle(int rowFirst, int rowSecond, int colFirst, int colSecond) {
        char firstLetter = codeTable[rowFirst][colSecond];
        char secondLetter = codeTable[rowSecond][colFirst];
        return "" + firstLetter + secondLetter;
    }

    private String encryptBigramm(final String bigramm) {
        Point first = findPos(bigramm.charAt(0));
        Point second = findPos(bigramm.charAt(1));

        if (first.y == second.y) {
            return getBigrammForOneRow(first.y, first.x, second.x);
        }
        if (first.x == second.x) {
            return getBigrammForOneColumn(first.x, first.y, second.y);
        }
        return getBigrammForRectangle(first.y, second.y, first.x, second.x);
    }

    private String decryptBigramm(final String bigramm) {
        Point first = findPos(bigramm.charAt(0));
        Point second = findPos(bigramm.charAt(1));

        if (first.y == second.y) {
            return decryptBigrammForOneRow(first.y, first.x, second.x);
        }
        if (first.x == second.x) {
            return decryptBigrammForOneColumn(first.x, first.y, second.y);
        }
        return getBigrammForRectangle(first.y, second.y, first.x, second.x);
    }

    public String encrypt(String msg) {
        List<String> bigramms = createBigramms(msg);
        List<String> encryptedBundle = new ArrayList<String>();

        for (String bigramm : bigramms) {
            String invertedBigramm = encryptBigramm(bigramm);
            encryptedBundle.add(invertedBigramm);
        }

        StringBuilder builder = new StringBuilder();
        for (String s : encryptedBundle) {
            builder.append(s);
        }
        return builder.toString();
    }

    public String decrypt(String msg) {
        List<String> bigramms = createBigramms(msg);
        List<String> encryptedBundle = new ArrayList<String>();

        for (String bigramm : bigramms) {
            String invertedBigramm = decryptBigramm(bigramm);
            encryptedBundle.add(invertedBigramm.charAt(1) == 'x' ? "" + invertedBigramm.charAt(0) : invertedBigramm);
        }

        StringBuilder builder = new StringBuilder();
        for (String s : encryptedBundle) {
            builder.append(s);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Playfair encoder: ";
    }
}
