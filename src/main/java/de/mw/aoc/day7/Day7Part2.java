package de.mw.aoc.day7;

import java.io.IOException;
import java.util.Arrays;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

public class Day7Part2 extends Day7Part1 {

    public static void main(String[] args) throws IOException {
        Day7Part2 day = new Day7Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(7, InputType.PART2)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        char[][] input = InputUtils.stringToCharMatrix(inputStr);
        int len = input.length;
        // Set the beams in Input
        compute(input);

        long[] current = new long[len];
        long[] prev = new long[len];

        for (int y = 1; y < len; y++) {
            for (int x = 0; x < input[y].length; x++) {
                char above = input[y - 1][x];
                char me = input[y][x];
                if (above == START) {
                    current[x] = 1;
                    break;
                } else if (above == BEAM) {
                    if (me == BEAM) {
                        current[x] += prev[x];
                    } else if (me == SPLITTER) {
                        if (x > 0) {
                            current[x - 1] += prev[x];
                        }
                        if (x < input.length) {
                            current[x + 1] += prev[x];
                        }
                    }
                }
            }
            long[] temp = prev;
            prev = current;
            current = temp;
            Arrays.setAll(current, x -> 0);

        }

        return Long.toString(Arrays.stream(prev).sum());
    }

}
