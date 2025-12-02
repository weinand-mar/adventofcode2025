module de.kvrlp.aoc25 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jdk.jshell;
    requires javafx.graphics;
    requires atlantafx.base;


    opens de.mw.aoc25 to javafx.fxml;
    exports de.mw.aoc25;
    exports de.mw.aoc25.model;
    exports de.mw.aoc25.ui;
    exports de.mw.aoc25.ui.daylist;
    exports de.mw.aoc25.ui.day;
    exports de.mw.aoc25.ui.part;
    opens de.mw.aoc25.model to javafx.fxml;
    opens de.mw.aoc25.ui to javafx.fxml;
    opens de.mw.aoc25.ui.daylist to javafx.fxml;
    opens de.mw.aoc25.ui.day to javafx.fxml;
    opens de.mw.aoc25.ui.part to javafx.fxml;
}