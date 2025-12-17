package de.mw.aoc.day11;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Day11Part1 implements DayPart {

    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day11Part1 day = new Day11Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsStringLines(11, InputType.PART1)));
    }

    public String compute(List<String> lines) {
        Map<String, List<String>> g = createG(lines);
        Map<String, List<String>> prev = getPrevList(g, "out", "you");
        List<List<String>> paths = getAllPaths(prev, "out", "you");
        return Long.toString(paths.size());
    }

    public static Map<String, List<String>> getPrevList(Map<String, List<String>> g, String end, String start) {
        Map<String, List<String>> prev = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Stack<String> s = new Stack<>();
        s.push(start);
        while (!s.isEmpty()) {
            String v = s.pop();
            visited.add(v);
            for (String u : g.get(v)) {
                List<String> prevList = prev.getOrDefault(u, new ArrayList<>());
                prevList.add(v);
                prev.put(u, prevList);
                if (!visited.contains(u) && !u.equals(end)) {
                    s.push(u);
                }
            }
        }
        return prev;
    }

    public static List<List<String>> getAllPaths(Map<String, List<String>> prev, String end, String start) {
        List<List<String>> paths = new ArrayList<>();
        Stack<List<String>> ss = new Stack<>();
        ss.push(List.of(end));
        while (!ss.isEmpty()) {
            List<String> path = ss.pop();
            String v = path.get(path.size() - 1);
            if (prev.containsKey(v)) {
                for (String u : prev.get(v)) {
                    ArrayList<String> newPath = new ArrayList<>(path);
                    newPath.add(u);
                    ss.add(newPath);
                    if(u.equals(start)) {
                        paths.add(newPath);
                    }
                }
            }
        }
        return paths;
    }

    public static Map<String, List<String>> createG(List<String> lines) {
        Map<String, List<String>> g = new HashMap<>();
        for (String l : lines) {
            String nodeName = l.substring(0, l.indexOf(':'));
            List<String> neighbours = Arrays.asList(l.substring(l.indexOf(':') + 1).trim().split(" "));
            g.put(nodeName, neighbours);
        }
        g.put("out", Collections.emptyList());
        return g;
    }

    @Override
    public String compute(String inputStr) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputStr.getBytes())))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return compute(result);
    }


    @Override
    public String sideEffect() {
        return "";
    }

}
