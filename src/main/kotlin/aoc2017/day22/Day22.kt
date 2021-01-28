package aoc2017.day22

import aoc2020.day20.Coord
import util.readInputLineByLine

private val deltas = listOf(Coord(0, -1), Coord(1, 0), Coord(0, 1), Coord(-1, 0))

fun readInputToMap(path: String): MutableMap<Coord, NodeStatus> {
    val map = mutableMapOf<Coord, NodeStatus>()
    for ((y, line) in readInputLineByLine(path).withIndex())
        for ((x, char) in line.withIndex())
            map[Coord(x, y)] = if (char == '.') NodeStatus.Clean else NodeStatus.Infected
    return map
}

fun countInfectionBurst(initial: Coord, grid: MutableMap<Coord, NodeStatus>, times: Int = 10_000): Int {
    var current = initial
    var facing = 0
    var infectionsCaused = 0
    repeat(times) {
        if (grid.getOrDefault(current, NodeStatus.Clean) == NodeStatus.Clean) {
            infectionsCaused++
            grid[current] = NodeStatus.Infected
            facing = facing.left()
        } else {
            grid[current] = NodeStatus.Clean
            facing = facing.right()
        }
        current += deltas[facing]
    }
    return infectionsCaused
}

private fun Int.left(): Int = if (this == 0) 3 else this - 1

private fun Int.right(): Int = this.inc() % 4

enum class NodeStatus { Clean, Infected }