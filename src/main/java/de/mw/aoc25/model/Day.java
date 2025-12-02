package de.mw.aoc25.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Day {
    private int dayIndex;
    private DayPart part1;
    private DayPart part2;
}
