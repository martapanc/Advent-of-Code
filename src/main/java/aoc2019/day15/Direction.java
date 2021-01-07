package aoc2019.day15;

import java.util.HashMap;
import java.util.Map;

public enum Direction {

    NORTH(1), SOUTH(2), WEST(3), EAST(4);

    private int id;
    private static Map<Integer, Direction> map = new HashMap<>();

    Direction(int id) {
        this.id = id;
    }

    static {
        for (Direction d : Direction.values()) {
            map.put(d.id, d);
        }
    }

    public int getDirectionId() {
        return this.id;
    }

    public static Direction getValueOf(int id) {
        return map.get(id);
    }
}
