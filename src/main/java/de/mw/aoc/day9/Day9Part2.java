package de.mw.aoc.day9;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import de.mw.aoc.utils.PrintUtils;

import java.io.IOException;
import java.util.*;

public class Day9Part2 extends Day9Part1 {

    public static void main(String[] args) throws IOException {
        Day9Part2 day = new Day9Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(9, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        Item[] input = readInput(inputStr);

        int[] sortedX = Arrays.stream(input).mapToInt(Item::x).sorted().toArray();
        int[] sortedY = Arrays.stream(input).mapToInt(Item::y).sorted().toArray();

        HashMap<Integer, Integer> orgXIndex = new HashMap<>();
        int xNew = 0;
        for (int xOrg : sortedX) {
            if (!orgXIndex.containsKey(xOrg)) {
                orgXIndex.put(xOrg, xNew);
                xNew++;
            }
        }

        HashMap<Integer, Integer> orgYIndex = new HashMap<>();
        int yNew = 0;
        for (int yOrg : sortedY) {
            if (!orgYIndex.containsKey(yOrg)) {
                orgYIndex.put(yOrg, yNew);
                yNew++;
            }
        }

        int ylen = yNew;
        int xlen = xNew;

        boolean[][] field = new boolean[ylen][xlen];
        List<List<Item>> edges = new ArrayList<>();

        Item prev = input[0];
        for (int i = 1; i < input.length; i++) {
            Item cur = input[i];
            drawEdge(prev, cur, field, orgXIndex, orgYIndex);
            edges.add(List.of(prev, cur));
            prev = cur;
        }
        drawEdge(prev, input[0], field, orgXIndex, orgYIndex);
        edges.add(List.of(prev, input[0]));
        fillField(field);
        System.out.println(PrintUtils.charMatrixToString(field));

        long maxSize = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                Item r1 = input[i];
                Item r2 = input[j];

                long size = getSize(r1, r2);
                if (size > maxSize && isInField(r1, r2, field, orgXIndex, orgYIndex)) {
                    maxSize = size;
                }
            }
        }

        return Long.toString(maxSize);
    }

    private static void drawEdge(Item prev, Item cur, boolean[][] field, HashMap<Integer, Integer> orgXIndex, HashMap<Integer, Integer> orgYIndex) {
        for (int x = Math.min(orgXIndex.get(prev.x()), orgXIndex.get(cur.x())); x <= Math.max(orgXIndex.get(prev.x()), orgXIndex.get(cur.x())); x++) {
            field[orgYIndex.get(cur.y())][x] = true;
        }
        for (int y = Math.min(orgYIndex.get(prev.y()), orgYIndex.get(cur.y())); y <= Math.max(orgYIndex.get(prev.y()), orgYIndex.get(cur.y())); y++) {
            field[y][orgXIndex.get(prev.x())] = true;
        }
    }

    private static void fillField(boolean[][] field) {
        Item start = findStartPoint(field);

        Stack<Item> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Item pop = stack.pop();
            field[pop.y()][pop.x()] = true;

            for (int y = Math.max(0, pop.y() - 1); y < Math.min(field.length, pop.y() + 2); y++) {
                for (int x = Math.max(0, pop.x() - 1); x < Math.min(field[y].length, pop.x() + 2); x++) {
                    if (!field[y][x]) {
                        stack.push(new Item(x, y));
                    }
                }
            }
        }
    }

    private static boolean isInField(Item r1, Item r2, boolean[][] field, HashMap<Integer, Integer> orgXIndex, HashMap<Integer, Integer> orgYIndex) {
        for (int y = Math.min(orgYIndex.get(r1.y()), orgYIndex.get(r2.y())); y <= Math.max(orgYIndex.get(r1.y()), orgYIndex.get(r2.y())); y++) {
            for (int x = Math.min(orgXIndex.get(r1.x()), orgXIndex.get(r2.x())); x <= Math.max(orgXIndex.get(r1.x()), orgXIndex.get(r2.x())); x++) {
                if (!field[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Item findStartPoint(boolean[][] field) {
//        for (int y = 0; y < field.length; y++) {
//            for (int x = 0; x < field[y].length; x++) {
//                if (field[y][x] && (x == field[y].length - 1 || !field[y][x + 1]) && (x == 0 || !field[y][x - 1])) {
//                    return new Item(x + 1, y);
//                }
//            }
//        }
        return new Item(126, 211);
    }


}
