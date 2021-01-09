package aoc2019.day11;

import aoc2019.day09.Output;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static aoc2019.day09.Day9Kt.processParameterMode;

public class Day11 {

    // INPUT
    // 0 = black cell   |   1 = white cell

    // OUTPUT
    // 0 = paint black  |   1 = paint white
    // 0 = left         |   1 = right

    static long processInput(ArrayList<Long> numbers, int initial) {
        Map<Point, Panel> panelMap = new HashMap<>();
        panelMap.put(new Point(0, 0), new Panel(initial, Panel.Direction.UP));

        int i = 0;
        int relativeBase = 0;
        StringBuilder outputBuilder = new StringBuilder();
        Point currentPos = new Point(0, 0);

        int paintedPanels = 1;

        while (i < numbers.size()) {
            int opCode = Math.toIntExact(numbers.get(i));
            if (opCode == 99) {
                break;
            }

            int input = panelMap.get(currentPos).getCurrentPanel();

            Output output = processParameterMode(numbers, i, opCode, input, relativeBase);
            outputBuilder.append(output.getCode());

            if (outputBuilder.length() == 2) {
                String outputVals = outputBuilder.toString();

                if (outputVals.charAt(0) == '0') {
                    panelMap.get(currentPos).paintPanelBlack();
                } else {
                    panelMap.get(currentPos).paintPanelWhite();
                }

                if (outputVals.charAt(1) == '0') {
                    switch (panelMap.get(currentPos).getDirection()) {
                        case UP:
                            currentPos = new Point(currentPos.x - 1, currentPos.y);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.LEFT));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.LEFT);
                            }
                            break;
                        case DOWN:
                            currentPos = new Point(currentPos.x + 1, currentPos.y);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.RIGHT));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.RIGHT);
                            }
                            break;
                        case LEFT:
                            currentPos = new Point(currentPos.x, currentPos.y - 1);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.DOWN));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.DOWN);
                            }
                            break;
                        case RIGHT:
                            currentPos = new Point(currentPos.x, currentPos.y + 1);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.UP));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.UP);
                            }
                            break;
                    }
                } else {
                    switch (panelMap.get(currentPos).getDirection()) {
                        case UP:
                            currentPos = new Point(currentPos.x + 1, currentPos.y);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.RIGHT));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.RIGHT);
                            }
                            break;
                        case DOWN:
                            currentPos = new Point(currentPos.x - 1, currentPos.y);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.LEFT));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.LEFT);
                            }
                            break;
                        case LEFT:
                            currentPos = new Point(currentPos.x, currentPos.y + 1);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.UP));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.UP);
                            }
                            break;
                        case RIGHT:
                            currentPos = new Point(currentPos.x, currentPos.y - 1);

                            if (panelMap.get(currentPos) == null) {
                                panelMap.put(currentPos, new Panel(0, Panel.Direction.DOWN));
                                paintedPanels++;
                            } else {
                                panelMap.get(currentPos).setDirection(Panel.Direction.DOWN);
                            }
                            break;
                    }
                }

                outputBuilder = new StringBuilder();
            }

            if (output.getRelativeBase() != 0) {
                relativeBase = output.getRelativeBase();
            }
            i += output.getIndex();
        }

        printPanels(panelMap);

        return paintedPanels;
    }

    private static void printPanels(Map<Point, Panel> panelMap) {
        for (int y = 1; y > -7; y--) {
            for (int x = -2; x < 43; x++) {
                if (panelMap.containsKey(new Point(x, y))) {
                    System.out.print(panelMap.get(new Point(x, y)).getCurrentPanel() == 0 ? " " : "█");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
