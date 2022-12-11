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

fun part1(movements: List<Movement>): Int = solve(movements, 1)

fun part2(movements: List<Movement>): Int = solve(movements, 9)

fun solve(movements: List<Movement>, noOfSegments: Int): Int {
    val tailSegments = mutableMapOf<Int, Coord>()
    (0 .. noOfSegments).forEach { tailSegments[it] = Coord(0, 0) }
    val explored = mutableSetOf(tailSegments[noOfSegments]!!)

    movements.forEach { movement ->
        repeat(movement.value) {
            tailSegments[0] = moveByOne(tailSegments[0]!!, movement.direction)
            (1..noOfSegments).forEach { i ->
                tailSegments[i] = tailFollowsHead(tailSegments[i]!!, tailSegments[i - 1]!!)
            }
            explored.add(tailSegments[noOfSegments]!!)
        }
    }

    printRopeTrace(explored)
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

        fun parse(direction: String): Direction? = map[direction]

        init { Direction.values().forEach { map[it.input] = it } }
    }
}

fun printRopeTrace(input: Set<Coord>) {
    val set = input.map { Coord(it.x, -it.y) }.toSet()
    (set.minBy { it.y }.y .. set.maxBy { it.y }.y).forEach { y ->
        (set.minBy { it.x }.x .. set.maxBy { it.x }.x).forEach { x ->
            if (x == 0 && y == 0) {
                print("S")
            } else if (set.contains(Coord(x, y))) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
    println()
}
