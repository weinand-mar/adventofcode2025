package de.mw.aoc.day1;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;

public class Day1Part2 extends Day1Part1{
    public static void main(String[] args) throws IOException {
        Day1Part2 day = new Day1Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(1, InputType.PART2)));
    }
    @Override
    public HandleResult handleInput(Input i, int current) {
        int currentPrev = current;
        int crossZero = i.getAmount() / 100;
        int rest = i.getAmount() % 100;
        if (i.getDirection() == Direction.L) {
            current -= rest;
            if (current < 0) {
                current += 100;
                crossZero++;
            }
            if (current == 0) {
                crossZero++;
            }
        } else {
            current += rest;
            if (current >= 100) {
                current -= 100;
                crossZero++;
            }
        }
        if (crossZero > 0 && currentPrev == 0 && i.getDirection() == Direction.L) {
            crossZero--;
        }
        return new HandleResult(crossZero, current);
    }
}
