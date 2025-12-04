package de.mw.aoc.ui.part;

import de.mw.aoc.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class PartView extends HBox {
    private PartPresenter presenter;
    @FXML
    private Button loadButton;
    @FXML
    private TextArea inputText;
    @FXML
    private Button computeButton;
    @FXML
    private TextArea outputText;
    @FXML
    private TextArea sideEffect;

    public PartView() {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("part.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadClick() {
        this.presenter.loadInput();
    }

    @FXML
    public void loadExampleClick() {
        this.presenter.loadExample();
    }

    @FXML
    public void compute() {
        this.presenter.compute();
    }

    public void setInputText(String s) {
        this.inputText.setText(s);
    }

    public String getInput() {
        return this.inputText.getText();
    }

    public void setOutputText(String output) {
        this.outputText.setText(output);
    }

    public void setSideEffectText(String text) {
        this.sideEffect.setText(text);
    }
}
