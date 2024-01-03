package aoc2023.day24

import aoc2022.day18.Coord3d
import kotlin.math.sign

fun parse(lines: List<String>): List<HailStone> {
    val hailStones = mutableListOf<HailStone>()
    lines.forEach { line ->
        val split = line.split("@").map { it.trim() }
        hailStones.add(HailStone(DoubleCoord3d(split[0]), Coord3d.fromInputString(split[1])))
    }
    return hailStones
}

fun part1(hailStones: List<HailStone>, range: LongRange = (200000000000000..400000000000000)): Int {
    val equationMap = hailStones.associateWith { hailStone -> equation2d(hailStone.coord, hailStone.velocity) }
    var validInterceptsCount = 0
    val hailstones = equationMap.keys.toList()

    for (i in 0 until hailstones.size - 1) {
        val hailstone1 = hailstones[i]

        for (j in i + 1 until hailstones.size) {
            val hailstone2 = hailstones[j]

            val line1 = equationMap[hailstone1]!!
            val line2 = equationMap[hailstone2]!!

            val intercept = findIntersection2d(line1, line2)

            if (intercept != null && range.contains(intercept.x.toLong()) && range.contains(intercept.y.toLong())) {
                if (!(lineInterceptedInThePast(intercept, hailstone1)
                            || lineInterceptedInThePast(intercept, hailstone2))
                ) {
                    validInterceptsCount++
                }
            }
        }
    }

    return validInterceptsCount
}

fun lineInterceptedInThePast(intercept: DoubleCoord3d, hailStone: HailStone): Boolean {
    val point = hailStone.coord
    val velocity = hailStone.velocity
    val xSign = velocity.x.sign.toDouble()
    val ySign = velocity.y.sign.toDouble()

    return (intercept.x - point.x).sign != xSign && (intercept.y - point.y).sign != ySign

}

fun part2(hailStones: List<HailStone>): Long {
    return 0
}

data class HailStone(val coord: DoubleCoord3d, val velocity: Coord3d)

data class DoubleCoord3d(val coord: String) {
    private val coords = coord.split(",").map { it.trim().toDouble() }
    val x = coords[0]
    val y = coords[1]
    val z = coords[2]

    constructor(x: Double, y: Double, z: Double) : this("$x, $y, $z")
}

data class Line(val slope: Double, val intercept: Double)

fun equation2d(coord: DoubleCoord3d, velocity: Coord3d): Line {
    val coord2 = DoubleCoord3d(coord.x + velocity.x, coord.y + velocity.y, coord.z + velocity.z)

    if (coord2.x - coord.x == 0.0) {
        throw Exception("Division by 0")
    }
    val slope = (coord2.y - coord.y).toDouble().div(coord2.x - coord.x)
    val intercept = coord.y - slope * coord.x
    return Line(slope, intercept)
}

fun Line.intersects(secondLine: Line): Boolean = (this.slope != secondLine.slope)

fun findIntersection2d(a: Line, b: Line): DoubleCoord3d? {
    if (a.intersects(b)) {
        val xIntercept = (b.intercept - a.intercept) / (a.slope - b.slope)
        val yIntercept = a.slope * xIntercept + a.intercept
        return DoubleCoord3d(xIntercept, yIntercept, 0.0)
    }
    return null
}