package de.mw.aoc.ui.daylist;

import de.mw.aoc.ui.MainPresenter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayListPresenter {
    private final MainPresenter main;
    private DayListView view = new DayListView(this);

    public DayListPresenter(MainPresenter main) {
        this.main = main;
        for(int i = 0; i < 12; i++) {
            view.add("Day " + (i + 1));
        }
    }

    public void dayChange(int n) {
        main.dayChange(n);
    }
}
