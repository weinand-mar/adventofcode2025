package de.mw.aoc.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.mw.aoc.Launcher;

public class InputUtils {

    public static String readDayInputAsString(int dayIndex, InputType inputType) {
        try {
            return new String(Launcher.class.getResourceAsStream("inputs/day" + dayIndex + "/" + inputType.toString().toLowerCase()).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readDayInputAsStringLines(int dayIndex, InputType inputType) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Launcher.class.getResourceAsStream("inputs/day" + dayIndex + "/" + inputType.toString().toLowerCase())))) {
            String line = null;
            while((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static List<String> stringLineToList(String input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        return reader.lines().toList();
    }

    public static char[][] stringToCharMatrix(String input) {
        return Arrays.stream(input.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }
}
