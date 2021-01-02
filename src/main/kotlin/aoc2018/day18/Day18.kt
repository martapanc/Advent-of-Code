package aoc2018.day18

import aoc2020.day20.Coord
import util.readInputLineByLine

fun readInputToMap(path: String): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    var x = 0
    for ((y, line) in readInputLineByLine(path).withIndex()) {
        for (char in line) {
            map[Coord(x, y)] = char
            x++
        }
        x = 0
    }
    return map
}

fun playGameOfLumberyards(inputMap: Map<Coord, Char>, minutes: Int): Int {
    var fieldMap = inputMap.toMutableMap()
    repeat(10) {
        val mapCopy = fieldMap.toMutableMap()
        for (acre in fieldMap.entries) {
            val neighborAcres = getNeighborAcres(acre.key, fieldMap)
            when (acre.value) {
                '.' -> mapCopy[acre.key] = if (neighborAcres.treeCount >= 3) '|' else '.'
                '|' -> mapCopy[acre.key] = if (neighborAcres.lumberYardCount >= 3) '#' else '|'
                '#' -> mapCopy[acre.key] =
                    if (neighborAcres.lumberYardCount >= 1 && neighborAcres.treeCount >= 1) '#' else '.'
            }
        }
        fieldMap = mapCopy
    }
    return fieldMap.values.count { it == '|' } * fieldMap.values.count { it == '#' }
}

fun getNeighborAcres(acre: Coord, map: Map<Coord, Char>): NeighborAcres {
    val deltaCoords = listOf(
        Coord(-1, -1), Coord(0, -1), Coord(1, -1),
        Coord(-1, 0), Coord(1, 0),
        Coord(-1, 1), Coord(0, 1), Coord(1, 1)
    )
    var openCount = 0
    var treeCount = 0
    var lumberYardCount = 0
    for (delta in deltaCoords) {
        val neighbor = Coord(acre.x + delta.x, acre.y + delta.y)
        if (map[neighbor] != null) {
            when (map[neighbor]) {
                '.' -> openCount++
                '|' -> treeCount++
                '#' -> lumberYardCount++
            }
        }
    }
    return NeighborAcres(openCount, treeCount, lumberYardCount)
}

data class NeighborAcres(val openCount: Int, val treeCount: Int, val lumberYardCount: Int)

fun printGrid(map: Map<Coord, Char>) {
    val side = 10
    for (y in 0 until side) {
        for (x in 0 until side) {
            print(map[Coord(x, y)])
        }
        println()
    }
    println()
}