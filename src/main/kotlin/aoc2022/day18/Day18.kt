package aoc2022.day18

import util.readInputLineByLine

fun readInputTo3dCoords(path: String): List<Coord3d> = readInputLineByLine(path).map { Coord3d.fromInputString(it) }

fun part1(cubes: List<Coord3d>): Int = findTotalSurface(cubes)

fun part2(cubes: List<Coord3d>): Int {
    var totalSurface = findTotalSurface(cubes)
    val allSeen = mutableSetOf<Coord3d>()
    val xRange = cubes.minBy { it.x }.x .. cubes.maxBy { it.x }.x
    val yRange = cubes.minBy { it.y }.y .. cubes.maxBy { it.y }.y
    val zRange = cubes.minBy { it.z }.z .. cubes.maxBy { it.z }.z

    (zRange).forEach { z ->
        (yRange).forEach { y ->
            (xRange).forEach { x ->
                val testCube = Coord3d(x, y, z)
                if (testCube !in cubes && testCube !in allSeen) {
                    val result = escapeBoundingBox(cubes, testCube, xRange, yRange, zRange)
                    allSeen.addAll(result.cubesSeen)
                    totalSurface -= result.facesTouched
                }
            }
        }
    }
    return totalSurface
}

fun escapeBoundingBox(cubes: List<Coord3d>, source: Coord3d, x: IntRange, y: IntRange, z: IntRange): EscapeResult {
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
        if (cube.x !in x || cube.y !in y || cube.z !in z) {
            return EscapeResult(0, seen)
        }
        cube.getNeighbors().forEach { neighbor ->
            if (neighbor in cubes) {
                facesTouched++
            } else if (neighbor !in seen) {
                queue.add(neighbor)
            }
        }
    }
    return EscapeResult(facesTouched, seen)
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

data class Coord3d(val x: Int, val y: Int, var z: Int) {

    companion object {
        fun fromInputString(input: String): Coord3d {
            val split = input.split(",").map { it.trim().toInt() }
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

data class EscapeResult(val facesTouched: Int, val cubesSeen: Set<Coord3d>)
