package de.mw.aoc.ui.part;

import de.mw.aoc.model.DayPart;
import de.mw.aoc.utils.InputType;
import de.mw.aoc.utils.InputUtils;
import javafx.scene.Node;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PartPresenter {
    private final PartView view = new PartView();
    private final int dayIndex;
    private final InputType part;
    private DayPart daypart;
    private String input;

    public PartPresenter(int dayIndex, InputType part) throws IOException {
        this.dayIndex = dayIndex;
        this.part = part;
        this.view.setPresenter(this);

        try {
            daypart = (DayPart) Class.forName("de.mw.aoc.day" + dayIndex + ".Day" + dayIndex + "Part" + part.getI()).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Node getView() {
        return view;
    }

    public void loadInput() {
        input = InputUtils.readDayInputAsString(dayIndex, part);
        this.view.setInputText(input);
    }

    public void loadExample() {
        input = InputUtils.readDayInputAsString(dayIndex, InputType.EXAMPLE);
        this.view.setInputText(input);
    }

    public void compute() {
        input = view.getInput();
        String output = daypart.compute(input);
        view.setSideEffectText(daypart.sideEffect());
        view.setOutputText(output);
    }
}
