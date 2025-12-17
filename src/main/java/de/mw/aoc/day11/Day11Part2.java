package de.mw.aoc.day11;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11Part2 extends Day11Part1 {

    public static void main(String[] args) throws IOException {
        Day11Part2 day = new Day11Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(11, InputType.PART1)));
    }

    @Override
    public String compute(List<String> lines) {
        Map<String, List<String>> g = createG(lines);
        long svr_dac = countPath(g, "svr", "dac", new HashMap<>());
        long dac_fft = countPath(g, "dac", "fft", new HashMap<>());
        long fft_out = countPath(g, "fft", "out", new HashMap<>());
        long opt1 = svr_dac * dac_fft * fft_out;

        long svr_fft = countPath(g, "svr", "fft", new HashMap<>());
        long fft_dac = countPath(g, "fft", "dac", new HashMap<>());
        long dac_out = countPath(g, "dac", "out", new HashMap<>());
        long opt2 = svr_fft * fft_dac * dac_out;

        long count = opt1 + opt2;
        return Long.toString(count);
    }

    private long countPath(Map<String, List<String>> g, String from, String to, Map<String, Long> countCache) {
        if(from.equals(to)) {
            return 1L;
        }
        if(countCache.containsKey(from)) {
            return countCache.get(from);
        }
        long sum = g.getOrDefault(from, new ArrayList<>())
                .stream().mapToLong(x -> countPath(g, x, to, countCache))
                .sum();
        countCache.put(from, sum);
        return sum;
    }
}
