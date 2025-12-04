package de.mw.aoc.day4;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day4Part1 implements DayPart {
    private StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day4Part1 day = new Day4Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(4, InputType.PART1)));
    }

    @Override
    public String compute(String input) {
        sideEffect = new StringBuilder();
        char[][] g = createG(input);
        Result count = getCount(g);
        return Integer.toString(count.count());
    }

    public static char[][] createG(String input) {
        String[] split = input.split("\n");
        return Arrays.stream(split).map(String::toCharArray).toArray(char[][]::new);
    }

    public Result getCount(char[][] g) {
        char[][] gout = copyArray(g);

        int count = 0;
        for (int y = 0; y < g.length; y++) {
            for (int x = 0; x < g.length; x++) {
                count += getCountForNode(g, y, x, gout);
            }
        }
        return new Result(gout, count);
    }

    private static int getCountForNode(char[][] g, int y, int x, char[][] gout) {
        int count = 0;
        if (isPaperRole(x, g[y])) {
            int countNeighbor = 0;
            for (int i = y - 1; i <= y + 1; i++) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (notSelfAndInbound(i, j, g, y, x) && isPaperRole(j, g[i])) {
                        countNeighbor++;
                    }
                }
            }
            if (countNeighbor < 4) {
                count++;
                gout[y][x] = 'x';
            }
        }
        return count;
    }

    private static boolean notSelfAndInbound(int i, int j, char[][] g, int y, int x) {
        return (i != y || j != x) && i >= 0 && j >= 0 && i < g.length && j < g.length;
    }

    private static boolean isPaperRole(int x, char[] g) {
        return g[x] == '@';
    }

    private static char[][] copyArray(char[][] g) {
        return Arrays.stream(g).map(x -> Arrays.copyOf(x, x.length)).toArray(char[][]::new);
    }

    public record Result(char[][] g, int count){}

    public void print(char[][] g) {
        for (char[] chars : g) {
            System.out.println(new String(chars));
        }
    }

    @Override
    public String sideEffect() {
        return sideEffect.toString();
    }
}
