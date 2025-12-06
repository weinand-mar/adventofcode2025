package de.mw.aoc.day6;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

public class Day6Part1 implements DayPart {

    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day6Part1 day = new Day6Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(6, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();

        Input input = readInput(inputStr);

        Long[][] numbers = input.numbers();
        String[] operators = input.operators();

        long countTotal = 0;
        for (int x = 0; numbers[0].length > x; x++) {
            String msg = "";
            Long count = operators[x].equals("*") ? 1L : 0L;
            for (int y = 0; numbers.length > y; y++) {
                if (operators[x].equals("+")) {
                    count += numbers[y][x];
                } else if (operators[x].equals("*")) {
                    count *= numbers[y][x];
                }
                msg += numbers[y][x] + " " + operators[x] + " ";
            }
            System.out.println(msg + " = " + count);
            sideEffect.append(msg).append(" = ").append(count).append("\n");
            countTotal += count;
        }

        return Long.toString(countTotal);
    }

    public Input readInput(String inputStr) {
        String[] lines = inputStr.split("\n");

        Long[][] numbers = Arrays.stream(lines, 0, lines.length - 1)
                .map(line -> {
                    String result = replaceWhitespacesWithSingle(line);
                    return Arrays.stream(result.split(" ")).map(Long::parseLong).toArray(Long[]::new);
                }).toArray(Long[][]::new);
        String result = replaceWhitespacesWithSingle(lines[lines.length - 1]);
        String[] operators = result.split(" ");
        return new Input(numbers, operators);
    }

    public String replaceWhitespacesWithSingle(String line) {
        Pattern p = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(line);
        String result = m.replaceAll(" ");
        return result;
    }

    record Input(Long[][] numbers, String[] operators) {

    }

    @Override
    public String sideEffect() {
        return sideEffect.toString();
    }
}
