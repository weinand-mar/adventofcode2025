package de.mw.aoc.day12;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12Part2 extends Day12Part1 {

    public static void main(String[] args) throws IOException {
        Day12Part2 day = new Day12Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(11, InputType.PART1)));
    }


}
