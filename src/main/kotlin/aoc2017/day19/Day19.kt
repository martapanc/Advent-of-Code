package aoc2017.day19

import aoc2020.day20.Coord
import util.readInputLineByLine


class Day19(val path: String) {

    private val deltas = listOf(Coord(0, 1), Coord(0, -1), Coord(1, 0), Coord(-1, 0))
    val input = readInputLineByLine(path)
    private val grid = input.map { it.toCharArray() }

    private val computedPath = computePath(Coord(input[0].indexOf("|"), 0), Coord(0, 1))

    fun computeLettersVisited(): String = computedPath.second

    fun computeTotalSteps(): Int = computedPath.first

    private tailrec fun computePath(
        location: Coord,
        direction: Coord,
        visited: List<Char> = emptyList(),
        steps: Int = 0
    ): Pair<Int, String> =
        if (grid.at(location) == ' ')
            Pair(steps, visited.joinToString(""))
        else {
            when (grid.at(location)) {
                in 'A'..'Z' -> computePath(location + direction, direction, visited + grid.at(location), steps.inc())
                '+' -> {
                    val turn = turn(location, direction)
                    computePath(location + turn, turn, visited, steps.inc())
                }
                else -> computePath(location + direction, direction, visited, steps.inc())
            }
        }

    private fun turn(location: Coord, direction: Coord) = deltas
        .filter { it != direction }
        .filter { it != !direction }
        .first { grid.at(location + it) != ' ' }

    private fun List<CharArray>.at(coord: Coord): Char =
        this[coord.y][coord.x]
}
