package de.mw.aoc25.day1;

import de.mw.aoc25.day2.Day2Part1;
import de.mw.aoc25.model.DayPart;
import de.mw.aoc25.utils.InputType;
import de.mw.aoc25.utils.InputUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.List;

public class Day1Part1 implements DayPart {
    public static void main(String[] args) throws IOException {
        Day1Part1 day = new Day1Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(1, InputType.PART1)));
    }

    @Override
    public String compute(String input) {
        List<String> inList = InputUtils.stringLineToList(input);
        List<Input> list = inList.stream().map(this::mapToInput).toList();

        int current = 50;
        int count = 0;
        System.out.println(current);
        for (Input i : list) {
            HandleResult result = handleInput(i, current);
            count += result.getCount();
            current = result.getNewCurrent();
            System.out.println(i.direction + " " + i.amount + " = " + current + " / " + count + " / " + result.getCount());
        }
        return Integer.toString(count);
    }

    private Input mapToInput(String s) {
        char dirString = s.charAt(0);
        Direction dir = switch (dirString) {
            case 'L' -> Direction.L;
            case 'R' -> Direction.R;
            default -> throw new IllegalStateException("Unexpected value: " + dirString);
        };

        int amount = Integer.parseInt(s.substring(1));
        return new Input(dir, amount);
    }

    public HandleResult handleInput(Input i, int current) {
        int rest = i.getAmount() % 100;
        if (i.getDirection() == Direction.L) {
            current -= rest;
            if (current < 0) {
                current += 100;
            }
        } else {
            current += rest;
            if (current >= 100) {
                current -= 100;
            }
        }
        if (current == 0) {
            return new HandleResult(1, current);
        }
        return new HandleResult(0, current);
    }

    public enum Direction {
        L, R
    }

    @AllArgsConstructor
    @Data
    public static class Input {
        private Direction direction;
        private int amount;
    }

    @AllArgsConstructor
    @Data
    public static class HandleResult {
        private int count;
        private int newCurrent;
    }
}
