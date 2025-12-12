package de.mw.aoc.day10;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import de.mw.aoc.utils.PrintUtils;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day10Part2 extends Day10Part1 {

    public static void main(String[] args) throws IOException {
        Day10Part2 day = new Day10Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(10, InputType.EXAMPLE)));
    }

    @Override
    public String compute(String inputStr) {
        List<Machine> machines = readInput(inputStr);
        double[][] ints = {
                new double[]{1, 1, 1, 0, 3},
                new double[]{2, 4, 0, 1, 8},
                new double[]{2, 3, 0, 0, 0}
        };
        doSimpleAlgorithmus(ints);
        return Double.toString(machines.stream().mapToDouble(this::simplexAlgorithmus).sum());
    }


    private double simplexAlgorithmus(Machine machine) {
        double[][] m = initMatrix(machine);
        return doSimpleAlgorithmus(m);
    }

    private double doSimpleAlgorithmus(double[][] m) {
        System.out.println(PrintUtils.charMatrixToString(m));
        while (true) {
            int pivotColIndex = findPivotCol(m);
            if (m[m.length - 1][pivotColIndex] <= 0) {
                System.out.println(-m[m.length - 1][m[m.length - 1].length - 1]);
                return -m[m.length - 1][m[m.length - 1].length - 1];
            }
            int pivotRowIndex = findPivotRow(m, pivotColIndex);

            System.out.println(pivotColIndex);
            System.out.println(pivotRowIndex);

            double[] pivotRow = m[pivotRowIndex];
            double pivotElement = pivotRow[pivotColIndex];
            divideVoctor(pivotRow, pivotElement);
            for (int i = 0; i < m.length; i++) {
                if (i != pivotRowIndex) {
                    substractMul(m[i], pivotRow, m[i][pivotColIndex]);
                }
            }
            System.out.println(PrintUtils.charMatrixToString(m));
        }
    }

    private static double[][] initMatrix(Machine machine) {
        double[][] m = new double[machine.buttons().size() + 1][machine.joltages().size() * 2 + 1 + machine.buttons().size()];

        for (int i = 0; i < machine.buttons().size(); i++) {
            for (int j = 0; j < machine.joltages().size(); j++) {
                if (machine.buttons().get(i).contains(j)) {
                    m[i][j] = 1;
                }
            }
            for (int j = 0; j < machine.joltages().size(); j++) {
                if (machine.buttons().get(i).contains(j)) {
                    m[i][j + machine.joltages().size()] = -1;
                }
            }
        }
        for (int i = 0; i < machine.joltages().size(); i++) {
            m[m.length - 1][i] = machine.joltages().get(i);
            m[m.length - 1][i + machine.joltages().size()] = -machine.joltages().get(i);
        }

        for (int i = 0; i < machine.buttons().size(); i++) {
            m[i][machine.joltages().size() * 2 + i] = 1;
        }

        for (int i = 0; i < machine.buttons().size(); i++) {
            m[i][m[i].length - 1] = 1;
        }
        return m;
    }

    private static int findPivotCol(double[][] m) {
        double[] lastRow = m[m.length - 1];
        return IntStream.range(0, lastRow.length - 1)
                .mapToObj(i -> Pair.with(i, lastRow[i]))
                .max(Comparator.comparing(Pair::getValue1)).get().getValue0();
    }

    private static Integer findPivotRow(double[][] m, int pivotCol) {
        return IntStream.range(0, m.length - 1)
                .mapToObj(i -> Pair.with(i, m[i][pivotCol] != 0 ? m[i][m[i].length - 1] / m[i][pivotCol] : Integer.MAX_VALUE))
                .min(Comparator.comparing(Pair::getValue1)).get().getValue0();
    }

    private static void divideVoctor(double[] v, double a) {
        for (int i = 0; i < v.length; i++) {
            v[i] /= a;
        }
    }

    private void substractMul(double[] v1, double[] v2, double mul) {
        for (int i = 0; i < v1.length; i++) {
            v1[i] -= v2[i] * mul;
        }
    }

    private long divideAndConquerCountMinButtonPress(Machine machine) {
        Stack<Machine> S = new Stack<>();
        S.push(machine);
        Map<List<Integer>, Long> map = new HashMap<>();
        System.out.println("Main " + machine);
        while (!S.isEmpty()) {
            Machine m = S.peek();
            if (m.joltages().stream().allMatch(x -> x > 1)) {
                List<Integer> l1 = m.joltages().stream().map(x -> x / 2).toList();
                List<Integer> l2 = IntStream.range(0, l1.size()).mapToObj(in -> m.joltages().get(in) - l1.get(in)).toList();
                if (map.containsKey(l1) && map.containsKey(l2)) {
                    S.pop();
                    if (map.get(l1) == -1 || map.get(l2) == -1) {
                        map.put(m.joltages(), countMinButtonPress(m));
                    } else {
                        long i = map.get(l1) + map.get(l2);
                        map.put(m.joltages(), i);
                    }
                } else {
                    if (!map.containsKey(l1)) {
                        S.push(new Machine(m.lights(), m.buttons(), l1));
                    }
                    if (!map.containsKey(l2)) {
                        S.push(new Machine(m.lights(), m.buttons(), l2));
                    }
                }
            } else {
                map.put(m.joltages(), countMinButtonPress(S.pop()));
            }
        }
        System.out.println(map.get(machine.joltages()));
        return map.get(machine.joltages());
    }

    private long countMinButtonPress(Machine machine) {
        LinkedList<QueueItem> Q = new LinkedList<>();
        Q.addLast(new QueueItem(machine.joltages(), Collections.emptyList()));
        Map<List<Integer>, Long> map = new HashMap<>();
        System.out.println("Sub " + machine);
        while (!Q.isEmpty()) {
            QueueItem i = Q.removeFirst();

            List<Integer> reverseState = IntStream.range(0, i.state.size()).mapToObj(in -> machine.joltages().get(in) - i.state.get(in)).toList();
            if (map.containsKey(reverseState)) {
                Long fastest = map.get(reverseState);
                System.out.println(i.buttonPressed.size() + fastest);
                return i.buttonPressed.size() + fastest;
            } else {
                map.put(i.state, (long) i.buttonPressed.size());
            }

            if (i.state.stream().allMatch(x -> x == 0)) {
                System.out.println(i.buttonPressed.size());
                return i.buttonPressed.size();
            } else if (i.state().stream().allMatch(integer -> integer >= 0)) {
                for (int b = 0; b < machine.buttons().size(); b++) {
                    if (i.buttonPressed().isEmpty() || i.buttonPressed().get(i.buttonPressed().size() - 1) == b || !i.buttonPressed().contains(b)) {
                        List<Integer> button = machine.buttons().get(b);
                        List<Integer> newState = new ArrayList<>(i.state());
                        List<Integer> newButtonPressed = new ArrayList<>(i.buttonPressed());
                        for (int li : button) {
                            newState.set(li, newState.get(li) - 1);
                        }
                        newButtonPressed.add(b);
                        Q.addLast(new QueueItem(newState, newButtonPressed));
                    }
                }
            }
        }
        return -1;
    }

    public record QueueItem(List<Integer> state, List<Integer> buttonPressed) {
    }
}
