package aoc2019.day18

import aoc2020.day20.Coord

//https://www.reddit.com/r/adventofcode/comments/ehu7a7/2019_day_18_part_1/

fun navigateMaze(grid: Map<Coord, Char>): Int {
    val steps = 0
    val initial = getCoordOfItem('@', grid)!!
    val (reachableDoors, reachableKeys) = findReachableItems(initial, grid)

    return steps
}

fun findRealDistanceToItem(initial: Coord, targetItem: Char, grid: Map<Coord, Char>): Int {
    val directions = listOf(Coord(0, 1), Coord(0, -1), Coord(1, 0), Coord(-1, 0))
    val explored = mutableListOf(initial)
    var edge = mutableListOf(initial)
    var distance = 1
    loop@ while (true) {
        val newEdge = mutableListOf<Coord>()
        for (point in edge)
            for (dir in directions) {
                val coord = Coord(point.x + dir.x, point.y + dir.y)
                if (grid[coord] != null && grid[coord] != '#' && coord !in explored) {
                    explored.add(coord)
                    newEdge.add(coord)
                    if (grid[coord] == targetItem) break@loop
                }
            }
        distance++
        edge = newEdge
    }
    return distance
}

fun findReachableItems(initial: Coord, grid: Map<Coord, Char>): Pair<List<Item>, List<Item>> {
    val reachableDoors = mutableListOf<Item>()
    val reachableKeys = mutableListOf<Item>()
    val directions = listOf(Coord(0, 1), Coord(0, -1), Coord(1, 0), Coord(-1, 0))
    val explored = mutableListOf(initial)
    var edge = mutableListOf(initial)
    loop@ while (edge.isNotEmpty()) {
        val newEdge = mutableListOf<Coord>()
        for (point in edge) {
            for (dir in directions) {
                val coord = Coord(point.x + dir.x, point.y + dir.y)
                val itemChar = grid[coord]
                if (itemChar != null && itemChar != '#' && coord !in explored) {
                    explored.add(coord)
                    if (isKey(itemChar))
                        reachableKeys.add(Item(coord, itemChar))
                    if (isDoor(itemChar)) {
                        reachableDoors.add(Item(coord, itemChar))
                    }
                    if (!isDoor(itemChar)) {
                        newEdge.add(coord)
                    }
                }
            }
        }
        edge = newEdge
    }
    return Pair(reachableDoors.sortedBy { it.char }, reachableKeys.sortedBy { it.char })
}

fun isDoor(item: Char): Boolean = "[A-Z]".toRegex().matches(item.toString())

fun isKey(item: Char): Boolean = "[a-z]".toRegex().matches(item.toString())

fun getCoordOfItem(item: Char, grid: Map<Coord, Char>): Coord? = grid.entries.firstOrNull { it.value == item }?.key

data class Item(val coord: Coord, val char: Char)