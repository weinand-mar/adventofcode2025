package de.mw.aoc25.ui.day;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayView {
    @FXML
    private TabPane root;
    @FXML
    private Tab part1;
    @FXML
    private Tab part2;


    public void setPartViews(Node part1View, Node part2View) {
        part1.setContent(part1View);
        part2.setContent(part2View);
    }
}
