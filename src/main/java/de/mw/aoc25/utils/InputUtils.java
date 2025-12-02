package de.mw.aoc25.utils;

import de.mw.aoc25.Launcher;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InputUtils {
    public static String readDayInputAsString(int dayIndex, InputType inputType) {
        try {
            return new String(Launcher.class.getResourceAsStream("inputs/day" + dayIndex + "/" + inputType.toString().toLowerCase()).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> stringLineToList(String input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        return reader.lines().toList();
    }
}
