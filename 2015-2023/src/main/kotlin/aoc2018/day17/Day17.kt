package aoc2018.day17

import util.Coord
import util.readInputLineByLine

fun readInputToMap(path: String): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    for (line in readInputLineByLine(path)) {
        val split = line.split(", ")
        if (line.startsWith("x")) {
            val x = split[0].replace("x=", "").toInt()
            val ySplit = split[1].replace("y=", "").split("..")
            val loY = ySplit[0].toInt()
            val hiY = ySplit[1].toInt()
            for (y in loY..hiY) {
                map[Coord(x, y)] = '#'
            }
        } else {
            val y = split[0].replace("y=", "").toInt()
            val xSplit = split[1].replace("x=", "").split("..")
            val loX = xSplit[0].toInt()
            val hiX = xSplit[1].toInt()
            for (x in loX..hiX) {
                map[Coord(x, y)] = '#'
            }
        }
    }
    return map
}

fun printTileMap(grid: Map<Coord, Char>) {
    val loX = grid.keys.minByOrNull { it.x }!!.x - 1
    val hiX = grid.keys.maxByOrNull { it.x }!!.x + 1
    val loY = grid.keys.minByOrNull { it.y }!!.y - 1
    val hiY = grid.keys.maxByOrNull { it.y }!!.y + 1
    for (y in loY..hiY) {
        for (x in loX..hiX) {
            if (grid[Coord(x, y)] != null) {
                print(grid[Coord(x, y)])
            } else {
                print('.')
            }
        }
        println()
    }
}

fun countFlowingAndStillWater(gridPath: String): Pair<Int, Int> {
    val input = readInputLineByLine(gridPath)
    val stillWaterCount = input.sumBy { line -> line.count { it == '~' } }
    val flowingWaterCount = stillWaterCount + input.sumBy { line -> line.count { it == '|' } }
    return Pair(flowingWaterCount, stillWaterCount)
}
