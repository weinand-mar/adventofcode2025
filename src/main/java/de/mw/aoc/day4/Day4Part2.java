package de.mw.aoc.day4;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;

public class Day4Part2 extends Day4Part1 {
    public static void main(String[] args) throws IOException {
        Day4Part2 day = new Day4Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(4, InputType.PART2)));
    }

    @Override
    public String compute(String input) {
        char[][] g = createG(input);
        int count = 0;
        Result result;
        do {
            result = getCount(g);
            g = result.g();
            count += result.count();

        } while (result.count() > 0);

        return Integer.toString(count);
    }
}
