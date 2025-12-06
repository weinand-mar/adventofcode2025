package de.mw.aoc.day6;

import java.io.IOException;
import java.util.Arrays;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

public class Day6Part2 extends Day6Part1 {

    public static void main(String[] args) throws IOException {
        Day6Part2 day = new Day6Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(6, InputType.PART2)));

    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        String[] lines = inputStr.split("\n");

        char[][] numbers = Arrays.stream(lines, 0, lines.length - 1).map(l -> l.toCharArray()).toArray(char[][]::new);
        String result = lines[lines.length - 1].replaceAll(" ", "");
        char[] operators = result.toCharArray();

        int countOp = 0;
        long countTotal = 0;
        long count = 0;
        for (int x = 0; x < numbers[0].length; x++) {
            StringBuilder numString = new StringBuilder();
            for (int y = 0; y < numbers.length; y++) {
                if (numbers[y][x] != ' ') {
                    numString.append(numbers[y][x]);
                }
            }

            if (numString.isEmpty()) {
                System.out.println(" = " + count);
                sideEffect.append(" = ").append(count).append("\n");
                System.out.println();
                countOp++;
                countTotal += count;
                count = 0;
            } else {
                String s = numString.toString() + " " + operators[countOp];
                sideEffect.append(s);
                System.out.print(s);
                long curNum = Long.parseLong(numString.toString());
                if (operators[countOp] == '*') {
                    if (count == 0) {
                        count = 1;
                    }
                    count *= curNum;
                } else {
                    count += curNum;
                }

            }
        }

        System.out.println(" = " + count);
        System.out.println();
        sideEffect.append(" = ").append(count).append("\n");

        countTotal += count;

        return Long.toString(countTotal);
    }
}
