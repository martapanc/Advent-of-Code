package aoc2019.day3;

import java.util.List;

public class Paths {

    private List<Instruction> paths1;
    private List<Instruction> paths2;

    public Paths(List<Instruction> paths1, List<Instruction> paths2) {
        this.paths1 = paths1;
        this.paths2 = paths2;
    }

    public Paths() {
    }

    public List<Instruction> getPaths1() {
        return paths1;
    }

    public List<Instruction> getPaths2() {
        return paths2;
    }

    @Override
    public String toString() {
        return "Path1 = " + paths1 +
                "\nPath2 = " + paths2;
    }
}
