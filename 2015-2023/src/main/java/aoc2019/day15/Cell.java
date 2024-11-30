package aoc2019.day15;

import lombok.Data;

@Data
public class Cell {

    private CellType type;
    public Cell(CellType cellType) {
        this.type = cellType;
    }

}

enum CellType {
    WALL('░'), PATH('█'), OXIGEN_THING('*');

    private char symbol;

    CellType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
