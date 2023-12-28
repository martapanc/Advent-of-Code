package aoc2023.day18

import aoc2022.day09.Direction
import aoc2022.day23.completeMap
import util.Coord

fun parse(lines: List<String>): List<Segment> {
    val segments = mutableListOf<Segment>()
    lines.forEach { line ->
        segments.add(Segment(line.split(" ")))
    }
    return segments
}

fun part1(segments: List<Segment>, start: Coord = Coord(3, 5)): Int {
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

    return map.size + floodFill(completeMap(map), start, mutableSetOf())
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
