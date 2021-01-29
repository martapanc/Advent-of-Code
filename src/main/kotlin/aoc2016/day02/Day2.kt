package aoc2016.day02

import aoc2020.day20.Coord
import util.readInputLineByLine

val deltas = mapOf('D' to Coord(0, 1), 'U' to Coord(0, -1), 'R' to Coord(1, 0), 'L' to Coord(-1, 0))

fun readInputAndDial(path: String, isPart2: Boolean = false): String {
    var code = ""
    for (line in readInputLineByLine(path)) {
        code += if (isPart2) findNumberPart2(line) else findNumber(line)
    }
    return code
}

//     1
//   2 3 4
// 5 6 7 8 9
//   A B C
//     D
fun findNumberPart2(input: String): Char {
    var coord = Coord(0, 0)
    val keypad = mapOf(
                                                                Coord(0, -2) to '1',
                                    Coord(-1, -1) to '2', Coord(0, -1) to '3', Coord(1, -1) to '4',
        Coord(-2, 0) to '5', Coord(-1, 0) to '6', Coord(0, 0) to '7', Coord(1, 0) to '8', Coord(2, 0) to '9',
                                    Coord(-1, 1) to 'A', Coord(0, 1) to 'B', Coord(1, 1) to 'C',
                                                                Coord(0, 2) to 'D'
    )
    for (char in input) {
        val delta = deltas[char]!!
        val newCoord = Coord(coord.x + delta.x, coord.y + delta.y)
        if (newCoord in keypad.keys) {
            coord = newCoord
        }
    }
    return keypad[coord]!!
}

fun findNumber(input: String): Int {
    var coord = Coord(0, 0)
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
