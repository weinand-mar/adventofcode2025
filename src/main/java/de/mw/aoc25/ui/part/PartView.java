package de.mw.aoc25.ui.part;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartView {
    private PartPresenter presenter;
    @FXML
    private HBox root;
    @FXML
    private Button loadButton;
    @FXML
    private TextArea inputText;
    @FXML
    private Button computeButton;
    @FXML
    private TextArea outputText;

    @FXML
    public void loadClick() {
        this.presenter.loadInput();
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
}
