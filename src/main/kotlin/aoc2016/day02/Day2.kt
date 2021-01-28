package aoc2016.day02

import aoc2020.day20.Coord
import util.readInputLineByLine

fun readInputAndDial(path: String): String {
    var code = ""
    for (line in readInputLineByLine(path)) {
        code += findNumber(line)
    }
    return code
}

fun findNumber(input: String): Int {
    var coord = Coord(0, 0)
    val deltas = mapOf('D' to Coord(0, 1), 'U' to Coord(0, -1), 'R' to Coord(1, 0), 'L' to Coord(-1, 0))
    val keypad = mapOf(
        Coord(-1, -1) to 1, Coord(0, -1) to 2, Coord(1, -1) to 3,
        Coord(-1, 0) to 4, Coord(0, 0) to 5, Coord(1, 0) to 6,
        Coord(-1, 1) to 7, Coord(0, 1) to 8, Coord(1, 1) to 9
    )
    for (char in input) {
        val delta = deltas[char]!!
        val newX = coord.x + delta.x
        val newY = coord.y + delta.y
        coord = Coord(
            if (newX == 2) 1 else if (newX == -2) -1 else newX,
            if (newY == 2) 1 else if (newY == -2) -1 else newY
        )
    }
    return keypad[coord]!!
}