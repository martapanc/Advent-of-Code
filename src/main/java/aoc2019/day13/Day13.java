package aoc2019.day13;


import aoc2019.day09.Output;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static aoc2019.day09.Day9Kt.processParameterMode;


public class Day13 {

    static Map<Point, Tile> processInput(ArrayList<Long> numbers) {

        Map<Point, Tile> tileMap = new HashMap<>();
        int i = 0;
        int relativeBase = 0;
        StringBuilder outputBuilder = new StringBuilder();

        while (i < numbers.size()) {
            int opCode = Math.toIntExact(numbers.get(i));
            if (opCode == 99) {
                break;
            }

            Output output = processParameterMode(numbers, i, opCode, 0, relativeBase);
            if (!output.getCode().equals("")) {
                outputBuilder.append(output.getCode()).append(",");
            }

            if (output.getRelativeBase() != 0) {
                relativeBase = output.getRelativeBase();
            }
            i += output.getIndex();
        }

        String[] outputs = outputBuilder.toString().split(",");

        for (i = 0; i < outputs.length; i += 3) {
            int x = Integer.parseInt(outputs[i]);
            int y = Integer.parseInt(outputs[i + 1]);
            int tileId = Integer.parseInt(outputs[i + 2]);
            tileMap.put(new Point(x, y), Tile.valueOf(tileId));
        }

        return tileMap;
    }

    static void printTileMap(Map<Point, Tile> tileMap) {
        int minX = tileMap.keySet().stream().mapToInt(p -> p.x).min().orElse(-1);
        int maxX = tileMap.keySet().stream().mapToInt(p -> p.x).max().orElse(-1);
        int minY = tileMap.keySet().stream().mapToInt(p -> p.y).min().orElse(-1);
        int maxY = tileMap.keySet().stream().mapToInt(p -> p.y).max().orElse(-1);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point key = new Point(x, y);
                if (tileMap.containsKey(key)) {
                    System.out.print(tileMap.get(key).getSymbol());
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    static int countTilesOfType(Tile tileType, Map<Point, Tile> processInput) {
        return (int) processInput.values().stream().filter(tile -> tile.equals(tileType)).count();
    }
}
