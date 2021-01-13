package aoc2018.day23

import aoc2020.day17.Coord3d
import util.readInputLineByLine

fun readInputToMap(path: String): Map<Coord3d, Int> {
    val map = mutableMapOf<Coord3d, Int>()
    val regex = Regex("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)")
    for (line in readInputLineByLine(path)) {
        val match = regex.matchEntire(line)
        val coord3d =
            Coord3d(match!!.groupValues[1].toInt(), match.groupValues[2].toInt(), match.groupValues[3].toInt())
        map[coord3d] = match.groupValues[4].toInt()
    }
    return map
}

fun countNanobotsInRange(map: Map<Coord3d, Int>): Int {
    val nanobotWithLargestRange = map.maxByOrNull { it.value }
    return map.entries.count {
        compute3dManhattanDistance(it.key, nanobotWithLargestRange!!.key) <= nanobotWithLargestRange.value
    }
}

fun compute3dManhattanDistance(target: Coord3d, source: Coord3d = Coord3d(0, 0, 0)): Int {
    return kotlin.math.abs(target.x - source.x) +
            kotlin.math.abs(target.y - source.y) +
            kotlin.math.abs(target.z - source.z)
}