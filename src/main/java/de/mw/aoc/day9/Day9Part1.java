package de.mw.aoc.day9;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day9Part1 implements DayPart {

    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day9Part1 day = new Day9Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(9, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        Item[] input = readInput(inputStr);
        long maxSize = computeMaxRectangle(input);
        return Long.toString(maxSize);
    }

    private static long computeMaxRectangle(Item[] input) {
        long maxSize = 0;
        for(int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                Item r1 = input[i];
                Item r2 = input[j];

                long size = getSize(r1, r2);
                maxSize = Math.max(size, maxSize);
            }
        }
        return maxSize;
    }

    public static long getSize(Item r1, Item r2) {
        return (long) (Math.abs(r1.x() - r2.x()) + 1) * (Math.abs(r1.y() - r2.y()) + 1);
    }

    public
    static Item[] readInput(String inputStr) {
        String[] lines = inputStr.split("\n");
        return IntStream.range(0, lines.length)
                .mapToObj(i -> Pair.with(i, lines[i].split(",")))
                .map(p -> new Item(Integer.parseInt(p.getValue1()[0]), Integer.parseInt(p.getValue1()[1])))
                .toArray(Item[]::new);
    }

    public record Item(int x, int y) {}

    @Override
    public String sideEffect() {
        return "";
    }
}
