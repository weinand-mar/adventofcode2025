package de.mw.aoc.utils;

public class PrintUtils {

    public static String charMatrixToString(char[][] m) {
        return charMatrixToString(m, m.length);
    }

    public static String charMatrixToString(char[][] m, int upToyY) {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < Math.min(m.length, upToyY); y++) {
            for (int x = 0; x < m[y].length; x++) {
                builder.append(m[y][x]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static String charMatrixToString(boolean[][] m) {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                builder.append(m[y][x] ? '#' : '.');
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
