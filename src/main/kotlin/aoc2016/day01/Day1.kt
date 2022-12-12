package aoc2016.day01

import util.Coord
import kotlin.math.abs

fun computeDistance(input: String): Int {
    var currentPos = Coord(0, 0)
    var facing = Facing.N
    val movements = input.split(", ")
        .map { Movement(Rotation.valueOf(it[0].toString()), it.replace(Regex("[R|L]"), "").toInt()) }

    for (mov in movements) {
        facing = facing.turn(mov.rotationDir)
        val delta = deltas[Facing.values().indexOf(facing)]
        currentPos = Coord(currentPos.x + mov.distance * delta.x, currentPos.y + mov.distance * delta.y)
    }
    return currentPos.manhattan()
}

fun computeDistancePart2(input: String): Int {
    var currentPos = Coord(0, 0)
    val visitedList = mutableListOf(currentPos)
    var facing = Facing.N
    val movements = input.split(", ")
        .map { Movement(Rotation.valueOf(it[0].toString()), it.replace(Regex("[R|L]"), "").toInt()) }

    for (mov in movements) {
        facing = facing.turn(mov.rotationDir)
        val delta = deltas[Facing.values().indexOf(facing)]
        repeat(mov.distance) {
            currentPos = Coord(currentPos.x + delta.x, currentPos.y + delta.y)
            if (visitedList.contains(currentPos)) {
                return currentPos.manhattan()
            } else {
                visitedList.add(currentPos)
            }
        }
    }
    return currentPos.manhattan()
}

data class Movement(val rotationDir: Rotation, var distance: Int)

enum class Rotation { R, L }

enum class Facing { N, E, S, W }

val deltas = listOf(Coord(0, -1), Coord(1, 0), Coord(0, 1), Coord(-1, 0))

fun Facing.turn(rotation: Rotation): Facing {
    val currentIndex = Facing.values().indexOf(this)
    var newIndex = (if (rotation == Rotation.R) currentIndex + 1 else currentIndex - 1) % 4
    if (newIndex == -1) newIndex = 3
    return Facing.values()[newIndex]
}

fun Coord.manhattan(): Int {
    return abs(0 - this.x) + abs(0 - this.y)
}
