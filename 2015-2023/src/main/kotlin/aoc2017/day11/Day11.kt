package aoc2017.day11

import kotlin.math.absoluteValue

//   \ n  /
// nw +--+ ne
//   /    \
// -+      +-
//   \    /
// sw +--+ se
//   / s  \

fun findShortestDistance(list: List<String>): Int {
    val source = HexaCoord(0, 0, 0)
    return list
        .fold(source) { coord, direction -> coord.travelTowards(direction) }
        .distanceTo(source)
}

fun findFurthestPosition(list: List<String>): Int? {
    val source = HexaCoord(0, 0, 0)
    return list
        .fold(listOf(source)) { path, direction -> path + (path.last().travelTowards(direction)) }
        .map { it.distanceTo(source) }.maxOrNull()
}

class HexaCoord(val x: Int, val y: Int, val z: Int) {

    fun travelTowards(direction: String): HexaCoord {
        return when (direction) {
            "n" -> HexaCoord(x, y + 1, z - 1)
            "s" -> HexaCoord(x, y - 1, z + 1)
            "ne" -> HexaCoord(x + 1, y, z - 1)
            "nw" -> HexaCoord(x - 1, y + 1, z)
            "se" -> HexaCoord(x + 1, y - 1, z)
            "sw" -> HexaCoord(x - 1, y, z + 1)
            else -> throw IllegalArgumentException("Invalid direction: $direction")
        }
    }

    fun distanceTo(target: HexaCoord): Int {
        return maxOf(
            (this.x - target.x).absoluteValue,
            (this.y - target.y).absoluteValue,
            (this.z - target.z).absoluteValue
        )
    }
}