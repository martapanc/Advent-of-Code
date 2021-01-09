package aoc2019.day17;

import aoc2019.day09.Output;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static aoc2019.day09.Day9Kt.processParameterMode;

public class Day17 {

    static Map<Point, String> processInput(ArrayList<Long> numbers) {
        int i = 0;
        int relativeBase = 0;
        Map<Point, String> map = new HashMap<>();
        int x = 0, y = 0;

        while (i < numbers.size()) {
            int opCode = Math.toIntExact(numbers.get(i));
            if (opCode == 99) {
                break;
            }
            Output output = processParameterMode(numbers, i, opCode, 0, relativeBase);

            if (output.getCode().length() > 0) {
                switch (output.getCode()) {
                    case "35":
                        map.put(new Point(x++, y), "#");
                        break;
                    case "46":
                        map.put(new Point(x++, y), ".");
                        break;
                    case "10":
                        x = 0;
                        y++;
                }
            }

            if (output.getRelativeBase() != 0) {
                relativeBase = output.getRelativeBase();
            }

            i += output.getIndex();
        }
        return map;
    }

    static void printMap(Map<Point, String> map) {
        int maxX = map.keySet().stream().mapToInt(p -> p.x).max().orElse(-1);
        int maxY = map.keySet().stream().mapToInt(p -> p.y).max().orElse(-1);

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                String p = map.get(new Point(x, y));
                System.out.print(p != null ? p : " ");
            }
            System.out.println();
        }
    }

    static java.util.List<Point> getIntersections(Map<Point, String> map) {
        return map.entrySet().stream().filter(entry -> entry.getValue().equals("#"))
                .map(Map.Entry::getKey)
                .filter(current -> "#".equals(map.get(new Point(current.x, current.y - 1))) &&
                        "#".equals(map.get(new Point(current.x, current.y + 1))) &&
                        "#".equals(map.get(new Point(current.x - 1, current.y))) &&
                        "#".equals(map.get(new Point(current.x + 1, current.y)))).collect(Collectors.toList());
    }

    static int multiplyCoordinates(List<Point> list) {
        return list.stream().mapToInt(p -> p.x * p.y).sum();
    }

}
