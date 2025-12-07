package de.mw.aoc.day7;

import java.io.IOException;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import de.mw.aoc.utils.PrintUtils;

public class Day7Part1 implements DayPart {

    protected StringBuilder sideEffect;

    protected static char START = 'S';
    protected static char EMPTY = '.';
    protected static char BEAM = '|';
    protected static char SPLITTER = '^';

    public static void main(String[] args) throws IOException {
        Day7Part1 day = new Day7Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(7, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();

        char[][] input = InputUtils.stringToCharMatrix(inputStr);

        long count = compute(input);

        return Long.toString(count);
    }

    public long compute(char[][] input) {
        long count = 0;
        int len = input.length;
        for (int y = 1; y < len; y++) {
            for (int x = 0; x < input[y].length; x++) {
                char above = input[y - 1][x];
                char me = input[y][x];
                if ((above == START || above == BEAM) && me == EMPTY) {
                    input[y][x] = BEAM;
                }
            }

            for (int x = 0; x < input[y].length; x++) {
                char me = input[y][x];
                char above = input[y - 1][x];
                if (me == SPLITTER && above == BEAM) {
                    boolean didSplit = false;
                    if (x > 0 && (input[y][x - 1] == EMPTY || input[y][x - 1] == BEAM)) {
                        input[y][x - 1] = BEAM;
                        didSplit = true;
                    }
                    if (x < input.length - 1 && (input[y][x + 1] == EMPTY || input[y][x + 1] == BEAM)) {
                        input[y][x + 1] = BEAM;
                        didSplit = true;
                    }
                    if (didSplit) {
                        count++;
                    }
                }
            }

            System.out.println(PrintUtils.charMatrixToString(input));
            sideEffect.append(PrintUtils.charMatrixToString(input)).append("\n");
            System.out.println();
        }
        return count;
    }

    @Override
    public String sideEffect() {
        return sideEffect.toString();
    }
}
