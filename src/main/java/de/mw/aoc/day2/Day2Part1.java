package de.mw.aoc.day2;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day2Part1 implements DayPart {
    public static void main(String[] args) throws IOException {
        Day2Part1 day = new Day2Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(2, InputType.PART1)));
    }

    @Override
    public String compute(String input) {
        String[] rangesStringList = input.split(",");
        List<Range> ranges = Arrays.stream(rangesStringList).map(this::mapToRange).toList();
        long count = 0;
        for (Range range : ranges) {
            count += countInvalidIdInRange(range);
        }
        return Long.toString(count);
    }

    @Override
    public String sideEffect() {
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
