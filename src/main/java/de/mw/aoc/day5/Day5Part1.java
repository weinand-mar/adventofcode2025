package de.mw.aoc.day5;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5Part1 implements DayPart {
    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day5Part1 day = new Day5Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(5, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();

        Input input = readInput(inputStr);

        long count = 0;
        for (long id : input.ids()) {
            if (idInAnyRange(id, input.ranges())) {
                count++;
            }
        }

        return Long.toString(count);
    }

    public static Input readInput(String input) {
        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        boolean readRanges = true;
        for (String in : input.split("\n")) {
            if (in.isBlank()) {
                readRanges = false;
                continue;
            }
            if (readRanges) {
                long from = Long.parseLong(in.substring(0, in.indexOf("-")));
                long to = Long.parseLong(in.substring(in.indexOf("-") + 1));
                ranges.add(new Range(from, to));
            } else {
                ids.add(Long.parseLong(in));
            }
        }
        return new Input(ranges, ids);
    }

    public record Input(List<Range> ranges, List<Long> ids) {
    }

    private boolean idInAnyRange(long id, List<Range> ranges) {
        for (Range range : ranges) {
            if (range.from <= id && id <= range.to) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String sideEffect() {
        return sideEffect.toString();
    }

    public record Range(long from, long to) {
    }
}
