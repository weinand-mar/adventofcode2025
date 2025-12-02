package de.mw.aoc25.day2;

import de.mw.aoc25.utils.InputType;
import de.mw.aoc25.utils.InputUtils;

import java.io.IOException;

public class Day2Part2 extends Day2Part1 {
    public static void main(String[] args) throws IOException {
        Day2Part2 day = new Day2Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(2, InputType.PART2)));
    }

    @Override
    public boolean invalidId(long number) {
        String s = Long.toString(number);
        for (int i = 1; i <= s.length() / 2; i++) {
            if (invalidIdForSubStringLength(s, i)) return true;
        }
        return false;
    }

    private static boolean invalidIdForSubStringLength(String s, int i) {
        if (s.length() % i != 0) {
            return false;
        }
        String before = null;
        boolean allEqual = true;
        for (int j = 0; j < s.length(); j += i) {
            String substring = s.substring(j, j + i);
            if (before != null) {
                allEqual = allEqual && before.equals(substring);
            }
            before = substring;
        }
        if (allEqual) {
            System.out.println(s + " " + before);
            return true;
        }
        return false;
    }
}
