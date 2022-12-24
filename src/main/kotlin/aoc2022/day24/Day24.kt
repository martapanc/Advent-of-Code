package aoc2022.day24

import util.Coord

class Day24(val grid: Map<Coord, Char>) {

    val blizzards = listOf('>', '<', 'v', '^')
    val deltas = mapOf('>' to Coord(1, 0), '<' to Coord(-1, 0), 'v' to Coord(0, 1), '^' to Coord(0, -1))
    val lo = 1
    val hiX = grid.keys.maxOf { it.x } - 1
    val hiY = grid.keys.maxOf { it.y } - 1
    val blizzardMinAndMax = mapOf('>' to hiX, '<' to lo, 'v' to hiY, '^' to lo)

    fun part1(): Int {
        val blizzardMap = mutableMapOf<Char, List<Coord>>()
        blizzards.forEach { blizzard ->
            blizzardMap[blizzard] = grid.entries.filter { it.value == blizzard }.map { it.key }
        }
        moveBlizzards(grid, blizzardMap)
        return 0
    }

    fun part2(map: Map<Coord, Char>): Int {
        return 0
    }

    fun moveBlizzards(inputGrid: Map<Coord, Char>, blizzardMap: Map<Char, List<Coord>>): Map<Coord, Char> {
        val grid = inputGrid.toMutableMap()
        val newBlizzards = mutableListOf<Coord>()
        blizzardMap.forEach { blizzards ->
            blizzards.value.forEach { blizzard ->
                val newCoordDelta = deltas[blizzards.key]!!
                val newBlizzard = Coord(blizzard.x + newCoordDelta.x, blizzard.y + newCoordDelta.y)
                when {
                    blizzards.key == '>' && newBlizzard.x > hiX -> newBlizzard.x = lo
                    blizzards.key == '<' && newBlizzard.x < lo -> newBlizzard.x = hiX
                    blizzards.key == 'v' && newBlizzard.y > hiY -> newBlizzard.y = lo
                    blizzards.key == '^' && newBlizzard.y < lo -> newBlizzard.y = hiY
                }
                newBlizzards.add(newBlizzard)
            }
        }
        return grid
    }

}
