package de.mw.aoc.day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;

public class Day10Part2 extends Day10Part1 {

    public static void main(String[] args) throws IOException {

        Loader.loadNativeLibraries();

        Day10Part2 day = new Day10Part2();
        System.out.println(day.compute(InputUtils.readDayInputAsString(10, InputType.PART1)));
    }

    @Override
    public String compute(String inputStr) {
        List<Machine> machines = readInput(inputStr);
        return Double.toString(machines.stream().mapToDouble(this::simplexAlgorithmus).sum());
    }

    private double simplexAlgorithmus(Machine machine) {

        // Beispiel mit OR-Tools
        MPSolver solver = new MPSolver("ILPExample", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        // Ganzzahlige Variablen
        List<MPVariable> vars = new ArrayList<>();
        for (int i = 0; i < machine.buttons().size(); i++) {
            MPVariable var = solver.makeIntVar(0.0, Integer.MAX_VALUE, "x" + i);
            vars.add(var);
        }

        // Zielfunktion: maximiere 3x + 2y
        MPObjective objective = solver.objective();
        for (MPVariable var : vars) {
            objective.setCoefficient(var, 1);
        }
        objective.setMinimization();

        // Nebenbedingung: x + y <= 7
        for (int i = 0; i < machine.joltages().size(); i++) {
            int joltage = machine.joltages().get(i);
            MPConstraint c1 = solver.makeConstraint(joltage, joltage, "joltage" + i);

            for (int b = 0; b < machine.buttons().size(); b++) {
                List<Integer> button = machine.buttons().get(b);
                if (button.contains(i)) {
                    c1.setCoefficient(vars.get(b), 1);
                }
            }
        }

        // Solver starten
        MPSolver.ResultStatus resultStatus = solver.solve();

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Optimale Lösung:");
            for (MPVariable var : vars) {
                System.out.println(var.name() + " = " + var.solutionValue());
            }
            System.out.println("Objektwert = " + objective.value());
            return objective.value();
        } else {
            System.out.println("Keine optimale Lösung gefunden.");
            throw new RuntimeException();
        }
    }
}
