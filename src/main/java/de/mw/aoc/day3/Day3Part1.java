package de.mw.aoc.day3;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;

public class Day3Part1 implements DayPart {
    public static void main(String[] args) throws IOException {
        Day3Part1 day = new Day3Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(3, InputType.PART1)));
    }

    protected int len = 2;
    private StringBuilder sideEffect;

    @Override
    public String compute(String input) {
        sideEffect = new StringBuilder();
        String[] lines = input.split("\n");
        long finalResult = 0;
        for (String line : lines) {
            int from = 0;
            int to = 0;
            StringBuilder result = new StringBuilder();
            for (int i = len; i > 0; i--) {
                to = line.length() - (i - 1);
                from = findIndexOfLargest(line, from, to);
                result.append(line.charAt(from));
                from++;
            }
            String print = line + " " + result;
            System.out.println(print);
            sideEffect.append(print);
            sideEffect.append("\n");
            finalResult += Long.parseLong(result.toString());
        }
        return Long.toString(finalResult);
    }

    private int findIndexOfLargest(String line, int from, int to) {
        int max = 0;
        int maxIndex = 0;
        for (int i = from; i < to; i++) {
            int n = line.charAt(i) - '0';
            if (n > max) {
                max = n;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    @Override
    public String sideEffect() {
        return sideEffect.toString();
    }
}
