package de.mw.aoc.day8;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.*;

public class Day8Part1 implements DayPart {

    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day8Part1 day = new Day8Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(8, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();

        List<V3> input = readInput(inputStr);
        List<Cost> costList = computeCost(input);


        Map<Integer, Set<Integer>> map = new HashMap<>();

        int i = 0;
        for (Cost smallest : costList) {
            i++;
            mapItteration(smallest, map);
            if (i == 1000) {
                break;
            }
        }

        List<Set<Integer>> sorted = new HashSet<>(map.values())
                .stream()
                .sorted(Comparator.comparing(x -> ((Set<Integer>) x).size()).reversed())
                .toList();
        OptionalLong sum = sorted.stream()
                .mapToLong(Set::size)
                .limit(3)
                .reduce((x, y) -> x * y);

        return Long.toString(sum.getAsLong());
    }

    public static void mapItteration(Cost smallest, Map<Integer, Set<Integer>> map) {
        if (!map.containsKey(smallest.i1())
                && !map.containsKey(smallest.i2())) {
            Set<Integer> set = new HashSet<>();
            set.add(smallest.i1());
            set.add(smallest.i2());
            map.put(smallest.i1(), set);
            map.put(smallest.i2(), set);
        } else if (!map.containsKey(smallest.i2())) {
            Set<Integer> set = map.get(smallest.i1());
            set.add(smallest.i2());
            map.put(smallest.i2(), set);
        } else if (!map.containsKey(smallest.i1())) {
            Set<Integer> set = map.get(smallest.i2());
            set.add(smallest.i1());
            map.put(smallest.i1(), set);
        } else if (map.get(smallest.i1()) != map.get(smallest.i2())) {
            Set<Integer> set1 = map.get(smallest.i1());
            Set<Integer> set2 = map.get(smallest.i2());
            set1.addAll(set2);
            for (Integer x : set2) {
                map.put(x, set1);
            }
        }
    }

    public static List<Cost> computeCost(List<V3> input) {
        List<Cost> costList = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                if (i != j) {
                    double distance = Math.sqrt(Math.pow(input.get(i).x() - input.get(j).x(), 2) +
                            Math.pow(input.get(i).y() - input.get(j).y(), 2) +
                            Math.pow(input.get(i).z() - input.get(j).z(), 2));
                    costList.add(new Cost(distance, i, j));
                }
            }
        }
        costList.sort(Comparator.comparingDouble(Cost::cost));
        return costList;
    }

    public static List<V3> readInput(String inputStr) {
        List<V3> input = new ArrayList<>();
        for (String line : inputStr.split("\n")) {
            int[] vec = Arrays.stream(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            input.add(new V3(vec[0], vec[1], vec[2]));
        }
        return input;
    }

    @Override
    public String sideEffect() {
        return sideEffect.toString();
    }

    public record Cost(double cost, int i1, int i2) {
    }

    public record V3(int x, int y, int z) {
    }
}
