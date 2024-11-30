package aoc2022.day24

import util.Coord

class Day24(val grid: Map<Coord, Char>) {

    private val blizzardTypes = listOf('>', '<', 'v', '^')
    private val lo = 1
    private val hiX = grid.keys.maxOf { it.x } - 1
    private val hiY = grid.keys.maxOf { it.y } - 1
    private val deltas = mapOf('>' to Coord(1, 0), '<' to Coord(-1, 0), 'v' to Coord(0, 1), '^' to Coord(0, -1))
    private val neighborDeltas = deltas.values.toSet()

    fun part1(): Int {
        val blizzardMap = mutableMapOf<Char, List<Coord>>()
        val target = Coord(grid.keys.maxOf { it.x } - 1, grid.keys.maxOf { it.y })
        var pos = Coord(1, 0)
        var i = 0
        blizzardTypes.forEach { blizzard ->
            blizzardMap[blizzard] = grid.entries.filter { it.value == blizzard }.map { it.key }
        }
        var moveRes = moveBlizzards(grid, blizzardMap)
        printMap(moveRes.grid, pos)

        while(pos != target) {
            val possibleMovements = pos.getEmptyNeighbors(moveRes.grid).sortedWith(compareBy ({ it.x }, { it.y }))
            if (possibleMovements.size == 1) {
                pos = possibleMovements.first()
            } else if (possibleMovements.size > 1){
                pos = possibleMovements.last()
            }
            i++
//            printMap(moveRes.grid, pos)
            moveRes = moveBlizzards(moveRes.grid, moveRes.blizzardMap)
        }
        return i
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
                    grid[newBlizzard] = newLists.key
                }
            }
        }
        return BlizzardMove(grid, newBlizzardMap)
    }

    private fun Coord.getEmptyNeighbors(grid: Map<Coord, Char>): List<Coord> {
        val neighbors = neighborDeltas.map { Coord(this.x + it.x, this.y + it.y) }
        return neighbors.filter { grid[it] == '.' }
    }

    class BlizzardMove(val grid: Map<Coord, Char>, val blizzardMap: Map<Char, List<Coord>>)

    fun printMap(map: Map<Coord, Char>, curr: Coord) {
        val xRange: IntRange = map.keys.minOf { it.x } .. map.keys.maxOf { it.x }
        val yRange: IntRange = map.keys.minOf { it.y } .. map.keys.maxOf { it.y }
        (yRange).forEach { y ->
            (xRange).forEach { x ->
                val coord = Coord(x, y)
                if (coord == curr) {
                    print("E")
                } else {
                    print(map[Coord(x, y)])
                }
            }
            println()
        }
        println()
    }
}
