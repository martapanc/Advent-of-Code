package aoc2023.day11

import aoc2021.day25.printMap
import util.Coord

fun part1(map: Map<Coord, Char>): Long {
    val expanded = expand(map)
    var sum = 0L
    val galaxies = expanded.filter { it.value == '#' }
    galaxies.forEach { g1 ->
        galaxies.forEach {g2 ->
            if (g1.key != g2.key) {
                sum += g1.key.manhattanDistance(g2.key)
            }
        }
    }
    return sum / 2
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