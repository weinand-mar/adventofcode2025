package de.mw.aoc25.ui.daylist;

import javafx.scene.control.ListView;

public class DayListView extends ListView<String> {
    private DayListPresenter presenter;

    public DayListView(DayListPresenter presenter) {
        super();
        this.presenter = presenter;

        this.getSelectionModel().selectedIndexProperty().addListener((obs, o, n) -> {
            this.presenter.dayChange(n.intValue());
        });
    }

    public void add(String s) {
        this.getItems().add(s);
    }
}
