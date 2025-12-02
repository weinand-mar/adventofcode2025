package de.mw.aoc25.ui.daylist;

import de.mw.aoc25.Launcher;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class DayListView extends ListView<String> {
    private DayListPresenter presenter;

    public DayListView(DayListPresenter presenter) {
        super();
        this.presenter = presenter;

        this.getSelectionModel().selectedIndexProperty().addListener((obs, o, n) -> {
            this.presenter.dayChange(n.intValue());
        });
        this.setCellFactory(param -> new ListCell<String>() {
            {
                setHeight(50);
            }
            @Override
            public void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) {
                    setText(null);
                } else {
                    String name = "icons/" + (getIndex() + 1) + ".png";
                    InputStream resourceAsStream = Launcher.class.getResourceAsStream(name);
                    if (resourceAsStream != null) {
                        ImageView imageView = new ImageView(new Image(resourceAsStream));
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        setGraphic(imageView);
                    }
                    setText(s);
                }
            }
        });
    }

    public void add(String s) {
        this.getItems().add(s);
    }
}
