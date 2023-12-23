package aoc2023.day13

import util.Coord
import kotlin.math.abs

fun parse(lines: List<String>): List<Map<Coord, Char>> {
    val output = mutableListOf<Map<Coord, Char>>()
    var x = 0
    var y = 0
    var map = mutableMapOf<Coord, Char>()
    lines.forEach { line ->
        if (line.isEmpty()) {
            y = 0
            output.add(map)
            map = mutableMapOf()
        } else {
            line.forEach { char ->
                map[Coord(x, y)] = char
                x++
            }
            y++
        }
        x = 0
    }
    return output
}

fun part1(input: List<Map<Coord, Char>>): Long {
    return 0
}

fun part2(input: List<Map<Coord, Char>>): Long {
    return 0
}

fun findMirror(map: Map<Coord, Char>): Int {
    val maxX = map.keys.maxBy { it.x }.x
    val maxY = map.keys.maxBy { it.y }.y

    val groupedByY = map.keys.groupBy { it.y }
    val sortedYKeys = groupedByY.keys.sorted()
    val rows = sortedYKeys.map { y ->
        groupedByY[y]?.sortedBy { it.x }?.map { map[it] }?.joinToString("") ?: ""
    }

    val rowIndices = rows
        .withIndex()
        .groupBy { it.value }
        .filter { it.value.size > 1 }
        .flatMap { entry ->
            entry.value.map { it.index }.combinations(2)
        }

    val fullMirror = mutableSetOf<Pair<Int, Int>>()
    val consecutiveRows = rowIndices.filter { abs(it.first - it.second) == 1 }
    if (consecutiveRows.isNotEmpty()) {
        var a = consecutiveRows[0].first
        var b = consecutiveRows[0].second
        while (a >= 0 && b <= maxY) {
            fullMirror.add(Pair(a, b))
            a--
            b++
        }
    }

    val groupedByX = map.keys.groupBy { it.x }
    val sortedKeys = groupedByY.keys.sorted()
    val columns = sortedKeys.map { x ->
        groupedByX[x]?.sortedBy { it.y }?.map { map[it] }?.joinToString("") ?: ""
    }
    val colIndices = columns
        .withIndex()
        .groupBy { it.value }
        .filter { it.value.size > 1 }
        .mapValues { entry -> entry.value.map { it.index } }
        .values

    return 0
}

fun List<Int>.combinations(size: Int): List<Pair<Int, Int>> {
    return flatMapIndexed { i, first ->
        subList(i + 1, size).map { second -> first to second }
    }
}