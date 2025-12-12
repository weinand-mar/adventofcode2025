package de.mw.aoc.day10;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

import java.io.IOException;
import java.util.*;

public class Day10Part1 implements DayPart {

    protected StringBuilder sideEffect;

    public static void main(String[] args) throws IOException {
        Day10Part1 day = new Day10Part1();
        System.out.println(day.compute(InputUtils.readDayInputAsString(10, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        sideEffect = new StringBuilder();
        List<Machine> machines = readInput(inputStr);
        return Long.toString(machines.stream().mapToLong(this::countMinButtonPress).sum());
    }

    public List<Machine> readInput(String inputStr) {
        return Arrays.stream(inputStr.split("\n")).map(this::lineToMachine).toList();
    }

    private Machine lineToMachine(String line) {
        List<List<Integer>> buttons = new ArrayList<>();
        List<Boolean> lights = null;
        List<Integer> joltages = null;
        for (String part : line.split(" ")) {
            char firstChar = part.charAt(0);
            if (firstChar == '[') {
                lights = part.substring(1, part.length() - 1).chars().mapToObj(x -> x == '#').toList();
            } else if (firstChar == '(') {
                buttons.add(Arrays.stream(part.substring(1, part.length() - 1).split(",")).map(Integer::parseInt).toList());
            }else if(firstChar == '{') {
                joltages = Arrays.stream(part.substring(1, part.length() - 1).split(",")).map(Integer::parseInt).toList();
            }
        }
//        buttons.sort(Comparator.comparing(x -> ((List)x).size()).reversed());
        return new Machine(lights, buttons, joltages);
    }

    private long countMinButtonPress(Machine machine) {
        System.out.println(machine);
        LinkedList<QueueItem> Q = new LinkedList<>();
        Q.addLast(new QueueItem(machine.lights().stream().map(x -> false).toList(), Collections.emptyList()));
        while (!Q.isEmpty()) {
            QueueItem i = Q.removeFirst();

            if (machine.lights().equals(i.state)) {
                System.out.println(i.buttonPressed);
                return i.buttonPressed.size();
            }

            for (int b = 0; b < machine.buttons().size(); b++) {
                if (i.buttonPressed().isEmpty() || b != i.buttonPressed().get(i.buttonPressed().size() -1)) {
                    List<Integer> button = machine.buttons().get(b);
                    List<Boolean> newState = new ArrayList<>(i.state());
                    List<Integer> newButtonPressed = new ArrayList<>(i.buttonPressed());
                    for (int li : button) {
                        newState.set(li, !newState.get(li));
                    }
                    newButtonPressed.add(b);
                    Q.addLast(new QueueItem(newState, newButtonPressed));
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String sideEffect() {
        return "";
    }

    public record QueueItem(List<Boolean> state, List<Integer> buttonPressed) {
    }

    public record Machine(List<Boolean> lights, List<List<Integer>> buttons, List<Integer> joltages) {
    }
}
