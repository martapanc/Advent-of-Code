package aoc2022.day24

import aoc2022.day23.printMap
import util.Coord

class Day24(val grid: Map<Coord, Char>) {

    private val blizzardTypes = listOf('>', '<', 'v', '^')
    val deltas = mapOf('>' to Coord(1, 0), '<' to Coord(-1, 0), 'v' to Coord(0, 1), '^' to Coord(0, -1))
    private val lo = 1
    private val hiX = grid.keys.maxOf { it.x } - 1
    private val hiY = grid.keys.maxOf { it.y } - 1

    fun part1(rounds: Int): Int {
        val blizzardMap = mutableMapOf<Char, List<Coord>>()
        blizzardTypes.forEach { blizzard ->
            blizzardMap[blizzard] = grid.entries.filter { it.value == blizzard }.map { it.key }
        }
        var moveRes = moveBlizzards(grid, blizzardMap)
        printMap(moveRes.grid)
        repeat(rounds) {
            moveRes = moveBlizzards(moveRes.grid, moveRes.blizzardMap)
            printMap(moveRes.grid)
        }
        return 0
    }

    fun part2(map: Map<Coord, Char>): Int {
        return 0
    }

    private fun moveBlizzards(inputGrid: Map<Coord, Char>, blizzardMap: Map<Char, List<Coord>>): BlizzardMove {
        val grid = inputGrid.toMutableMap()

        val newBlizzardMap = mutableMapOf<Char, List<Coord>>()
        blizzardMap.forEach { blizzards ->
            val newBlizzards = mutableListOf<Coord>()
            blizzards.value.forEach { blizzard ->
                grid[blizzard] = '.'
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
            newBlizzardMap[blizzards.key] = newBlizzards

            newBlizzardMap.forEach { newLists ->
                newLists.value.forEach { newBlizzard ->
                    grid[newBlizzard] = getBlizzardsInCell(newLists.key, newBlizzard, grid)
                }
            }
        }
        return BlizzardMove(grid, newBlizzardMap)
    }

    private fun getBlizzardsInCell(type: Char, curr: Coord, blizzards: Map<Coord, Char>): Char {
        return type
//        if (blizzards[curr] == '.')
//            return type
//        if (blizzardTypes.contains(blizzards[curr])) {
//            return '2'
//        }
//        return (blizzards[curr]!!.digitToInt() + 1).digitToChar()
    }

    class BlizzardMove(val grid: Map<Coord, Char>, val blizzardMap: Map<Char, List<Coord>>)
}
