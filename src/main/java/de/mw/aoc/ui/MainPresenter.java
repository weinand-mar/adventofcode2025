package de.mw.aoc.ui;

import atlantafx.base.theme.PrimerLight;
import de.mw.aoc.Launcher;
import de.mw.aoc.ui.day.DayPresenter;
import de.mw.aoc.ui.daylist.DayListPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MainPresenter extends Application {
    private MainView view;

    private DayListPresenter dayListPresenter;
    private List<DayPresenter> dayPresenter = new ArrayList<>(12);

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        dayListPresenter = new DayListPresenter(this);

        for (int i = 0; i < 12; i++) {
            dayPresenter.add(new DayPresenter(this, i + 1));
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(Launcher.class.getResource("application.css").toExternalForm());
        view = fxmlLoader.getController();

        view.setDayList(dayListPresenter.getView());
        stage.setTitle("Advent of Code 2025");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void dayChange(int n) {
        view.setMainPain(dayPresenter.get(n).getView());
    }
}
