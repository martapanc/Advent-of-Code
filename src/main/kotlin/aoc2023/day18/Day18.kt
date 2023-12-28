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

fun part1(segments: List<Segment>, start: Coord = Coord(3, 5)): Long {
    val map = drawRing(segments)

    return map.size + floodFill(completeMap(map), start, mutableSetOf())
}

fun part2(segments: List<Segment>, start: Coord = Coord(3, 5)): Long {
    val map = drawRing(segments, part2 = true)

    return map.size + floodFill(completeMap(map), start, mutableSetOf())
}

private fun drawRing(segments: List<Segment>, part2: Boolean = false): MutableMap<Coord, Char> {
    var currentCoord1 = Coord(0, 0)
    val map = mutableMapOf(currentCoord1 to '#')
    for (segment in segments) {
        val move = when {
            !part2 -> when (segment.direction) {
                Direction.UP -> Coord::north
                Direction.DOWN -> Coord::south
                Direction.LEFT -> Coord::west
                Direction.RIGHT -> Coord::east
            }
            else -> when (segment.direction2) {
                Direction.UP -> Coord::north
                Direction.DOWN -> Coord::south
                Direction.LEFT -> Coord::west
                Direction.RIGHT -> Coord::east
            }
        }

        val length = if (!part2) segment.length else segment.length2
        (1..length).forEach { i ->
            currentCoord1 = move(currentCoord1)
            map[currentCoord1] = '#'
        }
    }
    return map
}

fun floodFill(ringMap: Map<Coord, Char>, start: Coord, visited: MutableSet<Coord>): Long {
    val queue = mutableListOf(start)
    visited.add(start)
    var count = 0L

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

data class Segment(val input: List<String>) {
    val direction: Direction by lazy { parseDir() }
    val length: Int by lazy { parseLength() }

    private val color: String by lazy { parseColor() }
    val length2: Int by lazy { parseLength2() }
    val direction2: Direction by lazy { parseDir2() }

    private fun parseDir(): Direction = Direction.parse(input[0])!!
    private fun parseDir2(): Direction {
        val char = color.substring(5, 6).toInt()
        return when (char) {
            0 -> Direction.RIGHT
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.UP
            else -> throw IllegalArgumentException()
        }
    }

    private fun parseLength(): Int = input[1].toInt()
    private fun parseLength2(): Int = color.substring(0, 5).toInt(16)

    private fun parseColor(): String = input[2]
        .replace("(#", "")
        .replace(")", "")
}
