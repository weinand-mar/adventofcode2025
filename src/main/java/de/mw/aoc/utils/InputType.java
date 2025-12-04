package de.mw.aoc.utils;

public enum InputType {
    EXAMPLE(0),
    PART1(1),
    PART2(2);

    private final int i;

    InputType(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
