package aoc2016.day13

import aoc2020.day20.Coord

fun computeCoordinate(coord: Coord, favouriteNumber: Int): Char {
    val x = coord.x
    val y = coord.y
    val result = x * x + 3 * x + 2 * x * y + y + y * y + favouriteNumber
    val oneCount = Integer.toBinaryString(result).count { it == '1' }
    if (oneCount % 2 == 0)
        return '.'
    return '#'
}

fun computeGrid(favouriteNumber: Int, targetLocation: Coord): Int {
    val hiX = targetLocation.x + 10
    val hiY = targetLocation.y + 10
    val grid = mutableMapOf<Coord, Char>()

    for (y in 0..hiY)
        for (x in 0..hiX) {
            grid[Coord(x, y)] = computeCoordinate(Coord(x, y), favouriteNumber)
        }

    printGrid(grid, hiX, hiY, Coord(1, 1), targetLocation)
    return -1
}

private fun printGrid(grid: Map<Coord, Char>, hiX: Int, hiY: Int, start: Coord, target: Coord) {
    for (y in 0..hiY) {
        for (x in 0..hiX) {
            if (Coord(x, y) == start || Coord(x, y) == target) {
                print("X")
            } else print(grid[Coord(x, y)])
        }
        println()
    }
}