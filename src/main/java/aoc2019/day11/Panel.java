package aoc2019.day11;

import java.util.HashMap;
import java.util.Map;

public class Panel {

    private int currentPanel;
    private Direction direction;

    public Panel(int currentPanel, Direction direction) {
        this.currentPanel = currentPanel;
        this.direction = direction;
    }

    public int getCurrentPanel() {
        return currentPanel;
    }

    public void paintPanelBlack() {
        this.currentPanel = 0;
    }

    public void paintPanelWhite() {
        this.currentPanel = 1;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    enum Direction {
        UP('^'), DOWN('v'), RIGHT('>'), LEFT('<');
        private char dir;
        private static Map map = new HashMap<>();

        Direction(char dir) {
            this.dir = dir;
        }

        public char getDirChar() {
            return this.dir;
        }

        static {
            for (Direction d : Direction.values()) {
                map.put(d.dir, d);
            }
        }

        public static Direction valueOf(char direction) {
            return (Direction) map.get(direction);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "current=" + currentPanel +
                ", direction=" + direction +
                '}';
    }
}
