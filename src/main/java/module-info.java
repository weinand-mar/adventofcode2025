module de.mw.aoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jdk.jshell;
    requires javafx.graphics;
    requires atlantafx.base;

    opens de.mw.aoc to javafx.fxml;
    exports de.mw.aoc;
    exports de.mw.aoc.model;
    exports de.mw.aoc.ui;
    exports de.mw.aoc.ui.daylist;
    exports de.mw.aoc.ui.day;
    exports de.mw.aoc.ui.part;
    opens de.mw.aoc.model to javafx.fxml;
    opens de.mw.aoc.ui to javafx.fxml;
    opens de.mw.aoc.ui.daylist to javafx.fxml;
    opens de.mw.aoc.ui.day to javafx.fxml;
    opens de.mw.aoc.ui.part to javafx.fxml;
}