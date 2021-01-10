package aoc2019.day17

import aoc2019.commons.IntCodeProgram
import aoc2019.day09.readInput
import aoc2020.day20.Coord
import util.readInputLineByLine

fun readInput(path: String): List<Long> {
    return readInputLineByLine(path).map { it.toLong() }
}

fun computeIntersections(input: List<Long>): Int {
    val output = IntCodeProgram(input)
    output.execute()
    val outputMap = asciiListToMap(output)
    val intersections = getIntersections(outputMap)
    return multiplyCoordinatesAndSum(intersections)
}

private fun asciiListToMap(output: IntCodeProgram): MutableMap<Coord, Char> {
    val outputMap = mutableMapOf<Coord, Char>()
    var x = 0
    var y = 0
    for (char in output.outputToAscii())
        if (char == '\n') {
            y++
            x = 0
        } else {
            outputMap[Coord(x, y)] = char
            x++
        }
    return outputMap
}


fun processInputPart2(input: List<Long>, putValAtIndex: Pair<Long, Int>): Map<Coord, Char> {
    val output = IntCodeProgram(input)
    output.execute()
    val outputMap = asciiListToMap(output)
    return outputMap
}

fun printMap(map: Map<Coord, Char>) {
    val maxX = map.keys.stream().mapToInt { p: Coord -> p.x }.max().orElse(-1)
    val maxY = map.keys.stream().mapToInt { p: Coord -> p.y }.max().orElse(-1)
    for (y in 0..maxY)
        for (x in 0..maxX) {
            val p = map[Coord(x, y)]
            print(p ?: " ")
        }
}

fun getIntersections(map: Map<Coord, Char>): List<Coord> {
    return map.entries
        .filter { it.value == '#' }
        .map { it.key }
        .filter { current: Coord ->
            '#' == map[Coord(current.x, current.y - 1)]
                    && '#' == map[Coord(current.x, current.y + 1)]
                    && '#' == map[Coord(current.x - 1, current.y)]
                    && '#' == map[Coord(current.x + 1, current.y)]
        }
}

fun multiplyCoordinatesAndSum(list: List<Coord>): Int {
    return list.map { p: Coord -> p.x * p.y }.sum()
}

fun main() {
    val input = readInput("src/main/kotlin/aoc2019/day17/input1")
    input[0] = 2
    val output = IntCodeProgram(input)
    output.execute()
    printMap(asciiListToMap(output))
}