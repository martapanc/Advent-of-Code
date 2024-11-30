package aoc2023.day16

import aoc2022.day08.Cardinal
import aoc2022.day08.Cardinal.NORTH
import aoc2022.day08.Cardinal.SOUTH
import aoc2022.day08.Cardinal.EAST
import aoc2022.day08.Cardinal.WEST
import util.Coord

fun part1(map: Map<Coord, Char>): Int {
    return energize(map)
}

fun part2(map: Map<Coord, Char>): Int {
    val maxX = map.keys.maxBy { it.x }.x
    val maxY = map.keys.maxBy { it.y }.y
    val startingPositions = map
        .filter { it.key.x == 0 || it.key.y == 0 || it.key.x == maxX || it.key.y == maxY }
        .map { (coord, _) ->
            when {
                coord.x == 0 -> Pos(coord, EAST)
                coord.y == 0 -> Pos(coord, SOUTH)
                coord.x == maxX -> Pos(coord, WEST)
                coord.y == maxY -> Pos(coord, NORTH)
                else -> throw IllegalStateException()
            }
        }
        .toMutableList()

    var maxEnergizedCells = 0
    startingPositions.forEach { pos ->
        val energisedCells = energize(map, initialPos = pos)
        if (energisedCells > maxEnergizedCells) {
            maxEnergizedCells = energisedCells
        }
    }
    return maxEnergizedCells
}

private fun energize(map: Map<Coord, Char>, initialPos: Pos = Pos(Coord(0, 0), EAST)): Int {
    val energizedCells = mutableSetOf<Pos>()
    var edge = listOf(initialPos)

    while (edge.isNotEmpty()) {
        val newEdge = mutableListOf<Pos>()
        loop@ for (currPos in edge) {
            if (energizedCells.contains(currPos)) {
                continue@loop
            }
            val currCell = map[currPos.coord]
            if (currCell != null) {
                energizedCells.add(currPos)
                when (currCell) {
                    '|' -> {
                        if (currPos.direction == NORTH || currPos.direction == SOUTH) {
                            newEdge.add(currPos.moveSameDirection())
                        } else {
                            newEdge.add(currPos.moveNorth())
                            newEdge.add(currPos.moveSouth())
                        }
                    }

                    '-' -> {
                        if (currPos.direction == EAST || currPos.direction == WEST) {
                            newEdge.add(currPos.moveSameDirection())
                        } else {
                            newEdge.add(currPos.moveEast())
                            newEdge.add(currPos.moveWest())
                        }
                    }

                    '/' -> {
                        when (currPos.direction) {
                            NORTH -> newEdge.add(currPos.moveEast())
                            EAST -> newEdge.add(currPos.moveNorth())
                            SOUTH -> newEdge.add(currPos.moveWest())
                            WEST -> newEdge.add(currPos.moveSouth())
                        }
                    }

                    '\\' -> {
                        when (currPos.direction) {
                            NORTH -> newEdge.add(currPos.moveWest())
                            EAST -> newEdge.add(currPos.moveSouth())
                            SOUTH -> newEdge.add(currPos.moveEast())
                            WEST -> newEdge.add(currPos.moveNorth())
                        }
                    }

                    '.' -> newEdge.add(currPos.moveSameDirection())
                }
            }
        }
        edge = newEdge.toList()
    }

    return energizedCells.map { it.coord }.toSet().size
}

data class Pos(val coord: Coord, val direction: Cardinal) {
    fun moveSameDirection(): Pos {
        val newCoord = when (direction) {
            NORTH -> Coord(coord.x, coord.y - 1)
            EAST -> Coord(coord.x + 1, coord.y)
            SOUTH -> Coord(coord.x, coord.y + 1)
            WEST -> Coord(coord.x - 1, coord.y)
        }
        return Pos(newCoord, direction)
    }

    fun moveNorth(): Pos = Pos(Coord(coord.x, coord.y - 1), NORTH)
    fun moveSouth(): Pos = Pos(Coord(coord.x, coord.y + 1), SOUTH)
    fun moveWest(): Pos = Pos(Coord(coord.x - 1, coord.y), WEST)
    fun moveEast(): Pos = Pos(Coord(coord.x + 1, coord.y), EAST)
}
