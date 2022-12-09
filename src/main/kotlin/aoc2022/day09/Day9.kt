package aoc2022.day09

import aoc2020.day20.Coord
import util.readInputLineByLine
import kotlin.math.abs

fun readInputToMovementList(path: String): List<Movement> {
    val movements: MutableList<Movement> = mutableListOf()
    readInputLineByLine(path).forEach { line ->
        val split = line.split(" ")
        movements.add(Movement(Direction.parse(split[0])!!, split[1].toInt()))
    }
    return movements
}

fun part1(movements: List<Movement>): Int {
    var headPos = Coord(0, 0)
    var tailPos = Coord(0, 0)
    val explored = mutableSetOf(tailPos)
    movements.forEach { movement ->
        repeat(movement.value) {
            headPos = moveByOne(headPos, movement.direction)
            tailPos = tailFollowsHead(tailPos, headPos)
            explored.add(tailPos)
        }
    }
    return explored.size
}

fun part2(movements: List<Movement>): Int {
    var headPos = Coord(0, 0)
    var t1Pos = Coord(0, 0)
    var t2Pos = Coord(0, 0)
    var t3Pos = Coord(0, 0)
    var t4Pos = Coord(0, 0)
    var t5Pos = Coord(0, 0)
    var t6Pos = Coord(0, 0)
    var t7Pos = Coord(0, 0)
    var t8Pos = Coord(0, 0)
    var t9Pos = Coord(0, 0)
    val explored = mutableSetOf(t9Pos)
    movements.forEach { movement ->
        repeat(movement.value) {
            headPos = moveByOne(headPos, movement.direction)
            t1Pos = tailFollowsHead(t1Pos, headPos)
            t2Pos = tailFollowsHead(t2Pos, t1Pos)
            t3Pos = tailFollowsHead(t3Pos, t2Pos)
            t4Pos = tailFollowsHead(t4Pos, t3Pos)
            t5Pos = tailFollowsHead(t5Pos, t4Pos)
            t6Pos = tailFollowsHead(t6Pos, t5Pos)
            t7Pos = tailFollowsHead(t7Pos, t6Pos)
            t8Pos = tailFollowsHead(t8Pos, t7Pos)
            t9Pos = tailFollowsHead(t9Pos, t8Pos)
            explored.add(t9Pos)
        }
    }
    return explored.size
}

fun moveByOne(initial: Coord, direction: Direction): Coord {
    return when (direction) {
        Direction.UP -> Coord(initial.x, initial.y + 1)
        Direction.DOWN -> Coord(initial.x, initial.y - 1)
        Direction.LEFT -> Coord(initial.x - 1, initial.y)
        Direction.RIGHT -> Coord(initial.x + 1, initial.y)
    }
}

fun moveDiagonally(tailPos: Coord, headPos: Coord): Coord {
    if (headPos.x > tailPos.x && headPos.y > tailPos.y)
        return Coord(tailPos.x + 1, tailPos.y + 1)
    if (headPos.x < tailPos.x && headPos.y < tailPos.y)
        return Coord(tailPos.x - 1, tailPos.y - 1)
    if (headPos.x > tailPos.x && headPos.y < tailPos.y)
        return Coord(tailPos.x + 1, tailPos.y - 1)
    return Coord(tailPos.x - 1, tailPos.y + 1)
}

fun tailFollowsHead(tailPos: Coord, headPos: Coord): Coord {
    // H covers T
    if (tailPos == headPos) return tailPos

    // HT distance is 1 (horizontally, vertically or diagonally)
    if ((abs(tailPos.x - headPos.x) == 1 && abs(tailPos.y - headPos.y) == 0) ||
        (abs(tailPos.x - headPos.x) == 0 && abs(tailPos.y - headPos.y) == 1) ||
        (abs(tailPos.x - headPos.x) == 1 && abs(tailPos.y - headPos.y) == 1)
    ) return tailPos

    // H.T are aligned horizontally or vertically
    if (tailPos.x == headPos.x) {
        if (tailPos.y < headPos.y)
            return moveByOne(tailPos, Direction.UP)
        return moveByOne(tailPos, Direction.DOWN)
    }
    if (tailPos.y == headPos.y) {
        if (tailPos.x < headPos.x)
            return moveByOne(tailPos, Direction.RIGHT)
        return moveByOne(tailPos, Direction.LEFT)
    }

    // H.T are not touching and positioned diagonally
    return moveDiagonally(tailPos, headPos)
}

data class Movement(val direction: Direction, val value: Int)

enum class Direction(val input: String) {
    UP("U"), DOWN("D"), LEFT("L"), RIGHT("R");

    companion object {
        private val map: MutableMap<String, Direction> = HashMap()

        fun parse(direction: String): Direction? {
            return map[direction]
        }

        init {
            Direction.values().forEach { map[it.input] = it }
        }
    }
}
