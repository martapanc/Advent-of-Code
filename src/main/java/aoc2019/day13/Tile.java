package aoc2019.day13;

import java.util.HashMap;
import java.util.Map;

public enum Tile {
    EMPTY(0, " "),
    WALL(1, "|"),
    BLOCK(2, "■"),
    HORIZONTAL_PADDLE(3, "_"),
    BALL(4, "o");

    private static Map map = new HashMap<>();

    static {
        for (Tile t : Tile.values()) {
            map.put(t.id, t);
        }
    }

    private int id;
    private String symbol;

    Tile(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public static Tile valueOf(int tileId) {
        return (Tile) map.get(tileId);
    }

    public String getSymbol() {
        return symbol;
    }
}
