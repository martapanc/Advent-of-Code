package aoc2019.day17

import aoc2019.commons.IntCodeProgram
import util.Coord
import util.readInputLineByLine

fun readInput(path: String): List<Long> {
    return readInputLineByLine(path).map { it.toLong() }
}

fun computeIntersections(input: List<Long>): Int {
    val output = IntCodeProgram(input)
    output.execute()
    val outputMap = asciiListToMap(output)
    return multiplyCoordinatesAndSum(getIntersections(outputMap))
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

fun processInputPart2(input: List<Long>): Long {
    val output = IntCodeProgram(input)
    output.mem[0] = 2
    output.execute()

    val routinesAndFunctions = readInputLineByLine("src/main/kotlin/aoc2019/day17/directionString")

    val (mainRoutine, functions) = Pair(routinesAndFunctions[4], routinesAndFunctions[5])
    output.inputAscii(mainRoutine)
    output.inputAscii(functions.split("; "))
    output.inputAscii("n")
    output.execute()
    return output.output.removeAt(output.output.size - 1)
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
