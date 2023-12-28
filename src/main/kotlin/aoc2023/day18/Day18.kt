package aoc2023.day18

import aoc2022.day09.Direction
import aoc2022.day23.move
import aoc2022.day23.printMap
import util.Coord

fun parse(lines: List<String>): List<Segment> {
    val segments = mutableListOf<Segment>()
    lines.forEach { line ->
        segments.add(Segment(line.split(" ")))
    }
    return segments
}

fun part1(segments: List<Segment>): Long {
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
    return 0
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
