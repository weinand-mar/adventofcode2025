package de.mw.aoc.day8;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.*;

public class Day8Part2 extends Day8Part1 {

    public static void main(String[] args) throws IOException {
        Day8Part2 day = new Day8Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(8, InputType.EXAMPLE)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        List<V3> input = readInput(inputStr);
        List<Cost> costList = computeCost(input);


        Map<Integer, Set<Integer>> map = new HashMap<>();
        Set<Set<Integer>> sets = Collections.newSetFromMap(new IdentityHashMap<>());

        for (Cost smallest : costList) {
            mapItteration(smallest, map, sets);
            Integer size = sets.stream().findFirst().map(Set::size).orElse(0);
            if (sets.size() == 1 && size == input.size()) {
                V3 a = input.get(smallest.i1());
                V3 b = input.get(smallest.i2());

                System.out.println(a);
                System.out.println(b);

                return Integer.toString(a.x() * b.x());
            }
        }
        return "Error";
    }

}
