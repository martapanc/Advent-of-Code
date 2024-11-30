package aoc2019.day19

import aoc2019.commons.IntCodeProgram
import util.Coord
import util.readInputLineByLine

fun countPointsInTractorBeam(path: String, areaSide: Int = 50): Int {
    val tractorBeamMap = computeBeamGrid(path, areaSide)
    return tractorBeamMap.values.count { it == 1L }
}

private fun computeBeamGrid(path: String, areaSide: Int, min: Int = 0): MutableMap<Coord, Long> {
    val intCodeInput = readInputLineByLine(path)[0].split(",").map { it.toLong() }
    val tractorBeamMap = mutableMapOf<Coord, Long>()
    for (y in min until areaSide) {
        for (x in min until areaSide) {
            val program = IntCodeProgram(intCodeInput)
            program.input(x)
            program.input(y)
            program.execute()
            tractorBeamMap[Coord(x, y)] = program.output.first()
        }
    }
//    printBeamMap(tractorBeamMap, areaSide)
    return tractorBeamMap
}

fun printBeamMap(grid: Map<Coord, Long>, areaSide: Int = 50) {
    for (y in 0 until areaSide) {
        for (x in 0 until areaSide) {
            val value = grid[Coord(x, y)]
            if (value == 1L)
                print('#')
            else print('.')
        }
        println()
    }
}

fun findTopLeftCoordOfSquareOfSide(path: String, side: Int = 100, areaSide: Int = 300, filterFirst: Int = 0): Int {
    val grid = computeBeamGrid(path, areaSide, filterFirst).filter {it.value == 1L}.filter { it.key.x > filterFirst }

    for (point in grid.keys) {
        var fits = true
        for (i in 1 until side) {
            if (grid[Coord(point.x + i, point.y)] == 1L && grid[Coord(point.x, point.y + i)] == 1L) {
                continue
            } else {
                fits = false
                break
            }
        }
        if (fits) {
            return point.x * 10000 + point.y
        }
    }
    return -1
}
