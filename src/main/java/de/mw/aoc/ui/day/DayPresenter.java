package de.mw.aoc.ui.day;

import de.mw.aoc.Launcher;
import de.mw.aoc.ui.MainPresenter;
import de.mw.aoc.ui.part.PartPresenter;
import de.mw.aoc.utils.InputType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class DayPresenter {
    private final MainPresenter main;
    private final int day;
    private DayView view;
    private PartPresenter part1;
    private PartPresenter part2;

    public DayPresenter(MainPresenter main, int day) throws IOException {
        this.main = main;
        this.day = day;
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("day.fxml"));
        fxmlLoader.load();
        view = fxmlLoader.getController();

        part1 = new PartPresenter(day, InputType.PART1);
        part2 = new PartPresenter(day, InputType.PART2);

        view.setPartViews(part1.getView(), part2.getView());
        view.setTitle("Day " + day);
    }

    public Node getView() {
        return view.getRoot();
    }
}
