package de.mw.aoc.ui;

import de.mw.aoc.ui.daylist.DayListView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class MainView {
    @FXML
    public AnchorPane mainPane;
    @FXML
    public TitledPane dayPane;

    public void setDayList(DayListView view) {
        dayPane.setContent(view);
    }

    public void setMainPain(Node view) {
        mainPane.getChildren().clear();
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        mainPane.getChildren().add(view);
    }
}
