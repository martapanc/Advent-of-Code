package aoc2023.day18

import aoc2022.day09.Direction
import aoc2022.day23.fillMap
import aoc2022.day23.move
import aoc2022.day23.printMap
import aoc2023.day15.hash
import util.Coord

fun parse(lines: List<String>): List<Segment> {
    val segments = mutableListOf<Segment>()
    lines.forEach { line ->
        segments.add(Segment(line.split(" ")))
    }
    return segments
}

fun part1(segments: List<Segment>): Int {
    var currentCoord = Coord(0, 0)
    val map = mutableMapOf(currentCoord to '#')
    for (segment in segments) {
        val move = when (segment.direction) {
            Direction.UP -> Coord::north
            Direction.DOWN -> Coord::south
            Direction.LEFT -> Coord::west
            Direction.RIGHT -> Coord::east
        }
        (1 .. segment.length).forEach { i ->
            currentCoord = move(currentCoord)
            map[currentCoord] = '#'
        }
    }

    printMap(map)

    return countCoordinatesInsideRing(fillMap(map))
}

fun countCoordinatesInsideRing(ringMap: Map<Coord, Char>): Int {
    val visited = mutableSetOf<Coord>()
    var count = 0

    for (coord in ringMap.keys) {
        if (coord !in visited && ringMap[coord] == '.') {
            count += floodFill(ringMap, coord, visited)
        }
    }

    return count
}

fun floodFill(ringMap: Map<Coord, Char>, start: Coord, visited: MutableSet<Coord>): Int {
    val queue = mutableListOf(start)
    visited.add(start)
    var count = 0

    while (queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        count++

        val neighbors = listOf(
            Coord(current.x + 1, current.y),
            Coord(current.x - 1, current.y),
            Coord(current.x, current.y + 1),
            Coord(current.x, current.y - 1)
        )

        for (neighbor in neighbors) {
            if (neighbor !in visited && ringMap[neighbor] == '.') {
                queue.add(neighbor)
                visited.add(neighbor)
            }
        }
    }

    return count
}

fun part2(input: List<Segment>): Long {
    return 0
}

fun findBlocks(row: List<Coord>): List<List<Int>> {
    if (!areXCoordsContinuous(row)) {
        var currentBlock = mutableListOf(row[0].x)
        var jumpsFound = 0
        val blocks = mutableListOf<List<Int>>()
        for (i in 0 until row.size - 1) {
            if (row[i].x != row[i + 1].x - 1) {
                jumpsFound++
            }
            if (jumpsFound > 0 && jumpsFound % 2 == 0) {
                blocks.add(currentBlock)
                currentBlock = mutableListOf()
                jumpsFound = 0
            }
            currentBlock.add(row[i + 1].x)
        }
        blocks.add(currentBlock)

        return blocks
    }
    return listOf(row.map { it.x }.sorted())
}

fun areXCoordsContinuous(coords: List<Coord>): Boolean {
    val sorted = coords.sortedBy { it.x }
    for (i in 0 until sorted.size - 1) {
        if (sorted[i].x != sorted[i + 1].x - 1) {
            return false
        }
    }
    return true
}

data class Segment(val input: List<String>) {
    val direction: Direction by lazy { parseDir() }
    val length: Int by lazy { parseLength() }
    val color: String by lazy { parseColor() }

    private fun parseDir(): Direction = Direction.parse(input[0])!!

    private fun parseLength(): Int = input[1].toInt()

    private fun parseColor(): String = input[2]
        .replace("(", "")
        .replace(")", "")
}
