package de.mw.aoc25.ui.part;

import de.mw.aoc25.Launcher;
import de.mw.aoc25.day1.Day1Part1;
import de.mw.aoc25.day1.Day1Part2;
import de.mw.aoc25.day2.Day2Part1;
import de.mw.aoc25.day2.Day2Part2;
import de.mw.aoc25.model.Day;
import de.mw.aoc25.model.DayPart;
import de.mw.aoc25.utils.InputType;
import de.mw.aoc25.utils.InputUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class PartPresenter {
    private PartView view;
    private int dayIndex;
    private InputType part;
    private DayPart daypart;
    private String input;
    private String output;

    public PartPresenter(int dayIndex, InputType part) throws IOException {
        this.dayIndex = dayIndex;
        this.part = part;

        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("part.fxml"));
        fxmlLoader.load();
        view = fxmlLoader.getController();
        view.setPresenter(this);

        daypart = switch (dayIndex) {
            case 1 -> switch (part) {
                case EXAMPLE, PART1 -> new Day1Part1();
                case PART2 -> new Day1Part2();
            };
            case 2 -> switch (part) {
                case EXAMPLE, PART1 -> new Day2Part1();
                case PART2 -> new Day2Part2();
            };
            default -> null;
        };
    }

    public Node getView() {
        return view.getRoot();
    }

    public void loadInput() {
        input = InputUtils.readDayInputAsString(dayIndex, part);
        this.view.setInputText(input);
    }

    public void compute() {
        input = view.getInput();
        output = daypart.compute(input);
        view.setOutputText(output);
    }
}
