package aoc2023.day18

import aoc2022.day09.Direction
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

    val filledMap = map.toMutableMap()

    val minY = map.keys.minBy { it.y }.y
    val maxY = map.keys.maxBy { it.y }.y
    (minY .. maxY).forEach { y ->
        val row = map.keys.filter { it.y == y}.sortedBy { it.x }
        for (block in findBlocks(row)) {
            (block[0] .. block[block.lastIndex]).forEach { x ->
                filledMap[Coord(x, y)] = '#'
            }
        }
    }
    printMap(filledMap)

    return filledMap.count { it.value == '#' }
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

fun part2(input: List<Segment>): Long {
    return 0
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
