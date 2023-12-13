package aoc2023.day11

import util.Coord

fun part1(map: Map<Coord, Char>): Long {
    return computeAllDistances(map)
}

private fun computeAllDistances(map: Map<Coord, Char>, multiplier: Int = 1): Long {
    var sum = 0L
    val galaxiesCoords = map.filter { it.value == '#' }.map { it.key }
    val emptyCols = findEmptyColumns(map)
    val emptyRows = findEmptyRows(map)

    for ((i1, g1) in galaxiesCoords.withIndex()) {
        (i1 + 1 until galaxiesCoords.size).forEach { i2 ->
            val g2 = galaxiesCoords[i2]
            val emptyCount = countEmptyRowsAndColsBetween(g1, g2, emptyCols, emptyRows)
            sum += g1.manhattanDistance(g2) + multiplier * emptyCount
        }
    }
    return sum
}

fun part2(map: Map<Coord, Char>, multiplier: Int): Long {
    return computeAllDistances(map, multiplier)
}

fun countEmptyRowsAndColsBetween(c1: Coord, c2: Coord, emptyCols: List<Int>, emptyRows: List<Int>): Int {
    var count = 0
    val x1 = c1.x
    val x2 = c2.x
    val (minX, maxX) = if (x1 < x2) x1 to x2 else x2 to x1

    repeat(emptyCols
        .filter { it in (minX + 1)..<maxX }.size
    ) { count++ }

    val y1 = c1.y
    val y2 = c2.y
    val (minY, maxY) = if (y1 < y2) y1 to y2 else y2 to y1

    repeat(emptyRows
        .filter { it in (minY + 1)..<maxY }.size
    ) { count++ }

    return count
}

fun findEmptyColumns(map: Map<Coord, Char>): List<Int> {
    val emptyCols = mutableListOf<Int>()
    (0 .. map.keys.maxBy { it.x }.x).forEach { x ->
        val currentCol = map.entries.filter { it.key.x == x }
        if (currentCol.all { it.value == '.' }) {
            emptyCols.add(x)
        }
    }
    return emptyCols
}

fun findEmptyRows(map: Map<Coord, Char>): List<Int> {
    val emptyRows = mutableListOf<Int>()
    (0 .. map.keys.maxBy { it.y }.y).forEach { y ->
        val currentRow = map.entries.filter { it.key.y == y }
        if (currentRow.all { it.value == '.' }) {
            emptyRows.add(y)
        }
    }
    return emptyRows
}
