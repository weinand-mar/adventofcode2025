package de.mw.aoc.day3;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;

public class Day3Part2 extends Day3Part1 {
    public static void main(String[] args) throws IOException {
        Day3Part2 day = new Day3Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(3, InputType.PART2)));
    }
    @Override
    public String compute(String input) {
        len = 12;
        return super.compute(input);
    }
}
