package aoc2022.day18

import util.readInputLineByLine
import java.lang.Integer.max
import java.lang.Integer.min

fun readInputTo3dCoords(path: String): List<Coord3d> = readInputLineByLine(path).map { Coord3d.fromInputString(it) }

fun part1(cubes: List<Coord3d>): Int = findTotalSurface(cubes)

fun part2(cubes: List<Coord3d>): Int {
    var totalSurface = findTotalSurface(cubes)

    var loX = Int.MAX_VALUE; var loY = Int.MAX_VALUE; var loZ = Int.MAX_VALUE
    var hiX = 0; var hiY = 0; var hiZ = 0
    cubes.forEach { cube ->
        loX = min(cube.x, loX); hiX = max(cube.x, hiX)
        loY = min(cube.y, loY); hiY = max(cube.y, hiY)
        loZ = min(cube.z, loZ); hiZ = max(cube.z, hiZ)
    }

    val allSeen = mutableSetOf<Coord3d>()
    val xRange = loX .. hiX
    val yRange = loY .. hiY
    val zRange = loZ .. hiZ
    (zRange).forEach { z ->
        (yRange).forEach { y ->
            (xRange).forEach { x ->
                val testCube = Coord3d(x, y, z)
                if (testCube !in cubes) {
                    if (testCube !in allSeen) {
                        val touchedAndSeen = escapeBoundingCube(cubes, testCube, xRange, yRange, zRange)
                        allSeen.addAll(touchedAndSeen.second)
                        if (touchedAndSeen.first > 0) {
                            totalSurface -= touchedAndSeen.first
                        }
                    }
                }
            }
        }
    }

    return totalSurface
}

fun escapeBoundingCube(cubes: List<Coord3d>, source: Coord3d, xRange: IntRange, yRange: IntRange, zRange: IntRange):
        Pair<Int, Set<Coord3d>> {
    val seen = mutableSetOf<Coord3d>()
    val queue = ArrayDeque<Coord3d>()
    queue.add(source)
    var facesTouched = 0

    while (queue.isNotEmpty()) {
        val cube = queue.removeFirst()
        if (cube in seen) {
            continue
        }
        seen.add(cube)
        if (cube.x !in xRange || cube.y !in yRange || cube.z !in zRange) {
            return Pair(0, seen)
        }
        cube.getNeighbors().forEach { neighbor ->
            if (neighbor in cubes) {
                facesTouched++
            } else {
                if (neighbor !in seen) {
                    queue.add(neighbor)
                }
            }
        }
    }

    return Pair(facesTouched, seen)
}

private fun findTotalSurface(cubes: List<Coord3d>): Int {
    val freeSideMap = mutableMapOf<Coord3d, Int>()
    cubes.forEach { cube ->
        val neighborCoords = cube.getNeighbors()
        val neighbors = neighborCoords.count { cubes.contains(it) }
        freeSideMap[cube] = 6 - neighbors
    }
    return freeSideMap.values.sum()
}

data class Coord3d(val x: Int, val y: Int, val z: Int) {

    companion object {
        fun fromInputString(input: String): Coord3d {
            val split = input.split(",").map { it.toInt() }
            return Coord3d(split[0], split[1], split[2])
        }
    }

    fun getNeighbors(): List<Coord3d> {
        val deltas = listOf(
            Coord3d(1, 0, 0), Coord3d(-1, 0, 0),
            Coord3d(0, 1, 0), Coord3d(0, -1, 0),
            Coord3d(0, 0, 1), Coord3d(0, 0, -1)
        )
        return deltas.map { Coord3d(x + it.x, y + it.y, z + it.z) }
    }

    override fun toString(): String {
        return "{x: $x, y: $y, z: $z}"
    }
}
