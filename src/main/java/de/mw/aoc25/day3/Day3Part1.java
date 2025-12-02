package de.mw.aoc25.day3;

import de.mw.aoc25.model.DayPart;
import de.mw.aoc25.utils.InputType;
import de.mw.aoc25.utils.InputUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

public class Day3Part1 implements DayPart {
    public static void main(String[] args) throws IOException {
        Day3Part1 day = new Day3Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(3, InputType.PART1)));
    }

    @Override
    public String compute(String input) {
        return "";
    }

    private long countInvalidIdInRange(Range range) {
        long count = 0;
        for (long i = range.getFrom(); i <= range.getTo(); i++) {
            if (invalidId(i)) {
                count += i;
            }
        }
        return count;
    }

    public boolean invalidId(long i) {
        String s = Long.toString(i);
        if (s.length() % 2 != 0) {
            return false;
        }

        String s1 = s.substring(0, s.length() / 2);
        String s2 = s.substring(s.length() / 2);
        return s1.equals(s2);
    }

    private Range mapToRange(String rangeString) {
        String[] split = rangeString.split("-");
        if( split.length != 2) {
            throw new IllegalArgumentException();
        }
        return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    @Data
    @AllArgsConstructor
    public static class Range {
        private long from;
        private long to;
    }
}
