package aoc2018.day25

import aoc2020.day17.Coord4d
import util.readInputLineByLine
import kotlin.math.abs

fun readInputToList(path: String): List<Coord4d> {
    return readInputLineByLine(path)
        .map { line -> line.split(",").map { it.toInt() } }
        .map { Coord4d(it[0], it[1], it[2], it[3]) }
}

fun groupConstellations(starsInput: List<Coord4d>): Int {
    var constellationId = 1
    val stars = starsInput.sortedWith(compareBy({ it.x }, { it.y }, { it.z }, { it.w }))
    val constellationMap = mutableMapOf(stars[0] to constellationId)

    for (star in stars.subList(1, stars.size)) {
        if (constellationMap.any { star.manhattanDistanceLE3(it.key) }) {
            val neighbors = constellationMap.filter { star.manhattanDistanceLE3(it.key) }.toMap()
            if (neighbors.size == 1 || neighbors.values.toSet().size == 1) {
                // Join one existing constellation
                constellationMap[star] = constellationMap[neighbors.keys.toList()[0]]!!
            } else {
                // Join and merge two constellations
                val firstId = constellationMap[neighbors.keys.toList()[0]]!!
                constellationMap[star] = firstId
                for (n in neighbors.keys) {
                    constellationMap[n] = firstId
                }
            }
        } else {
            constellationId++
            constellationMap[star] = constellationId
        }
    }
    return constellationMap.values.toSet().count()
}

fun Coord4d.manhattanDistanceLE3(target: Coord4d): Boolean =
    abs(target.x - this.x) + abs(target.y - this.y) + abs(target.z - this.z) + abs(target.w - this.w) <= 3
