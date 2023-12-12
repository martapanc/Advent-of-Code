package aoc2023.day11

import util.Coord

fun part1(map: Map<Coord, Char>): Long {
    val expanded = expand(map)
    var sum = 0L
    val galaxiesCoords = expanded.filter { it.value == '#' }.map { it.key }
    for ((i1, g1) in galaxiesCoords.withIndex()) {
        (i1 until galaxiesCoords.size).forEach {i2 ->
            sum += g1.manhattanDistance(galaxiesCoords[i2])
        }
    }
    return sum
}

fun part2(galaxies: Map<Coord, Char>): Long {
    return 0
}

fun expand(map: Map<Coord, Char>): Map<Coord, Char> {
    val maxX = map.keys.maxBy { it.x }.x
    val columns = MutableList(0) { listOf<Map.Entry<Coord, Char>>() }
    (0 .. maxX).forEach { x ->
        val currentCol = map.entries.filter { it.key.x == x }
        columns.add(currentCol)
        if (currentCol.all { it.value == '.' }) {
            columns.add(currentCol)
        }
    }

    val expandedMap = mutableMapOf<Coord, Char>()
    columns.forEachIndexed { colId, col ->
        col.forEach {
            expandedMap[Coord(colId, it.key.y)] = it.value
        }
    }

//    printMap(expandedMap)
    return expandedMap
}