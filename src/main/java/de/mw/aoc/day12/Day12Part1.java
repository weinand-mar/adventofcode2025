package de.mw.aoc.day12;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import de.mw.aoc.utils.PrintUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Day12Part1 implements DayPart {

    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day12Part1 day = new Day12Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsStringLines(12, InputType.PART1)));
    }

    public String compute(List<String> lines) {

        Input input = getInput(lines);

        int count = 0;
        for (Tree tree : input.trees()) {
            long size = (long) tree.height * tree.width;
            long size2 = 0;
            for(int i = 0; i < tree.counts.size(); i++) {
                Integer i1 = tree.counts.get(i);
                Long i2 = input.presentSize.get(i);
                size2 += i1 * i2;
            }
            if(size2 <= size) {
                count++;
            }

        }


        return Integer.toString(count);
    }

    private static Input getInput(List<String> lines) {
        List<List<char[][]>> presentShapes = new ArrayList<>();
        List<Long> presentSize = new ArrayList<>();
        List<Tree> trees = new ArrayList<>();
        for (int i = 0, index = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (isPresent(l, index)) {
                List<char[][]> presents = readPresent(lines, i);
                presentShapes.add(presents);
                long count = Arrays.stream(presents.get(0))
                        .flatMap(x -> IntStream.range(0, x.length)
                                .mapToObj(ii -> x[ii]))
                        .filter(x -> x == '#').count();
                presentSize.add(count);
                i += 4;
                index++;
            } else {
                Tree tree = readTree(l);
                trees.add(tree);
            }
        }
        return new Input(presentShapes, presentSize, trees);
    }

    private boolean fitPresentUnderTree(Tree tree, Input input) {
        Stack<List<Integer>> s = new Stack<>();
        Map<List<Integer>, List<char[][]>> fields = new HashMap<>();
        List<Integer> target = tree.counts();
        s.push(target);

        while (!s.isEmpty()) {
            List<Integer> state = s.peek();

            if (state.stream().allMatch(x -> x == 0)) {
                s.pop();
                fields.put(state, new ArrayList<>() {{
                    add(new char[tree.height][tree.width]);
                }});
            } else {
                for (int i = 0; i < state.size(); i++) {
                    if (state.get(i) > 0) {
                        List<Integer> newState = new ArrayList<>(state);
                        newState.set(i, newState.get(i) - 1);
                        if (fields.containsKey(newState)) {
                            s.pop();
                            List<char[][]> childs = fields.get(newState);
                            List<char[][]> presents = input.presentShapes().get(i);
                            List<char[][]> solutions = new ArrayList<>();
                            for (char[][] present : presents) {
                                for (char[][] child : childs) {
                                    for (int yf = 0; yf < child.length - 2; yf++) {
                                        for (int xf = 0; xf < child[yf].length - 2; xf++) {
                                            if (fitsPresentOffset(child, present, yf, xf)) {
                                                char[][] newField = copyPresentToNewField(present, child, xf, yf);
                                                solutions.add(newField);
                                            }
                                        }
                                    }
                                }
                            }
                            fields.put(state, solutions);
                        } else {
                            s.push(newState);
                        }
                    }
                }
            }
        }
        if (fields.containsKey(target)) {
            System.out.println(PrintUtils.charMatrixToString(fields.get(target).get(0)));
        }
        return fields.containsKey(target);
    }

    private static boolean isPresent(String l, int i) {
        return l.equals(i + ":");
    }

    private static List<char[][]> readPresent(List<String> lines, int i) {
        char[][] present = new char[3][];
        for (int j = 0; j < 3; j++) {
            present[j] = lines.get(i + j + 1).toCharArray();
        }

        char[][] mirroX = new char[3][3];
        char[][] mirroY = new char[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                mirroX[y][2 - x] = present[y][x];
                mirroY[2 - y][x] = present[y][x];
            }
        }

        char[][] p90 = rotate90(present);
        char[][] p180 = rotate90(p90);
        char[][] p270 = rotate90(p180);
        char[][] mirrorX90 = rotate90(mirroX);
        char[][] mirrorX180 = rotate90(mirrorX90);
        char[][] mirrorX270 = rotate90(mirrorX180);
        char[][] mirrorY90 = rotate90(mirroY);
        char[][] mirrorY180 = rotate90(mirrorY90);
        char[][] mirrorY270 = rotate90(mirrorY180);

        return List.of(new Asdf(present),
                new Asdf(p90),
                new Asdf(p180),
                new Asdf(p270),
                new Asdf(mirroX),
                new Asdf(mirrorX90),
                new Asdf(mirrorX180),
                new Asdf(mirrorX270),
                new Asdf(mirroY),
                new Asdf(mirrorY90),
                new Asdf(mirrorY180),
                new Asdf(mirrorY270)).stream().distinct().map(x -> x.m).toList();
    }

    private static Tree readTree(String l) {
        String size = l.substring(0, l.indexOf(':'));
        String[] sizes = size.split("x");
        int width = Integer.parseInt(sizes[0]);
        int height = Integer.parseInt(sizes[1]);

        List<Integer> count = Arrays.stream(l.substring(l.indexOf(':') + 1).trim().split(" "))
                .map(Integer::parseInt).toList();

        return new Tree(width, height, count);
    }

    private static boolean fitsPresentOffset(char[][] field, char[][] present, int yf, int xf) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (present[y][x] == '#' && field[yf + y][xf + x] == '#') {
                    return false;
                }
            }
        }
        return true;
    }

    private char[][] copyPresentToNewField(char[][] present, char[][] field, int xf, int yf) {
        char[][] newField = deepCopy(field);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (present[y][x] == '#') {
                    newField[y + yf][x + xf] = present[y][x];
                }
            }
        }
        return newField;
    }

    private static char[][] rotate90(char[][] p) {
        char[][] p90 = new char[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                p90[x][2 - y] = p[y][x];
            }
        }
        return p90;
    }

    private char[][] deepCopy(char[][] field) {
        return Arrays.stream(field).map(x -> Arrays.copyOf(x, x.length)).toArray(char[][]::new);
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

    static class Asdf {
        char[][] m;

        public Asdf(char[][] m) {
            this.m = m;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            Asdf asdf = (Asdf) o;
            return Arrays.deepEquals(m, asdf.m);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(m);
        }
    }

    record StackItem(char[][] field, List<Integer> insertedPresents) {
    }

    private record Input(List<List<char[][]>> presentShapes, List<Long> presentSize, List<Tree> trees) {
    }

    record Tree(int width, int height, List<Integer> counts) {
    }

}
