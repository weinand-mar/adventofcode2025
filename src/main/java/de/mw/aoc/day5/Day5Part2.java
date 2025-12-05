package de.mw.aoc.day5;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Day5Part2 extends Day5Part1 {
    public static void main(String[] args) throws IOException {
        Day5Part2 day = new Day5Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(5, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        Input input = readInput(inputStr);
        List<Range> ranges = input.ranges();

        long count = 0;
        for (int i = 0; i < ranges.size(); i++) {
            List<Range> rangeList = List.of(ranges.get(i));
            for (int j = 0; j < i; j++) {
                rangeList = getNonOverlappingRange(ranges.get(j), rangeList);
                if (rangeList.isEmpty()) {
                    break;
                }
            }
            for (Range range : rangeList) {
                long c = range.to() - range.from() + 1;
                count += c;
                String msg = printRange(ranges.get(i)) +
                        "    " +
                        printRange(range) +
                        " = " + c;
                System.out.println(msg);
                sideEffect.append(msg);
                sideEffect.append("\n");
            }
        }

        return Long.toString(count);
    }

    private List<Range> getNonOverlappingRange(Range r1, List<Range> r2) {
        return r2.stream().flatMap(x -> getNonOverlappingRange(r1, x).stream()).toList();
    }

    private String printRange(Range range) {
        return "[" + range.from() + "..." + range.to() + "]";
    }

    private List<Range> getNonOverlappingRange(Range r1, Range r2) {
        long newFrom = r2.from();
        long newTo = r2.to();
        if (fullOverlap(r1, r2)) {
            return Collections.emptyList();
        } else if (inverseFullOverlap(r1, r2)) {
            return List.of(new Range(r2.from(), r1.from() - 1), new Range(r1.to() + 1, r2.to()));
        } else if (rightOverlap(r1, r2)) {
            newFrom = r1.to() + 1;
        } else if (leftOverlap(r1, r2)) {
            newTo = r1.from() - 1;
        }
        return List.of(new Range(newFrom, newTo));
    }

    private static boolean leftOverlap(Range r1, Range r2) {
        return r1.from() <= r2.to() && r1.from() > r2.from();
    }

    private static boolean rightOverlap(Range r1, Range r2) {
        return r1.to() >= r2.from() && r1.to() < r2.to();
    }

    private static boolean inverseFullOverlap(Range r1, Range r2) {
        return r1.from() > r2.from() && r1.to() < r2.to();
    }

    private static boolean fullOverlap(Range r1, Range r2) {
        return r1.from() <= r2.from() && r1.to() >= r2.to();
    }
}
