package aoc2023.day24

import aoc2022.day18.Coord3d
import kotlin.math.pow
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

fun part2(hailStones: List<HailStone>): Long {
    return solve(hailStones)
}

fun lineInterceptedInThePast(intercept: DoubleCoord3d, hailStone: HailStone): Boolean {
    val point = hailStone.coord
    val velocity = hailStone.velocity
    val xSign = velocity.x.sign.toDouble()
    val ySign = velocity.y.sign.toDouble()

    return (intercept.x - point.x).sign != xSign && (intercept.y - point.y).sign != ySign
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
    val slope = (coord2.y - coord.y).div(coord2.x - coord.x)
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

fun matrixMinor(m: List<List<Double>>, i: Int, j: Int): List<List<Double>> {
    return (m.slice(0 until i) + m.slice(i + 1 until m.size)).map { row ->
        row.slice(0 until j) + row.slice(j + 1 until row.size)
    }
}

fun matrixDet(m: List<List<Double>>): Double {
    return when (m.size) {
        2 -> m[0][0] * m[1][1] - m[0][1] * m[1][0]
        else -> m[0].indices.sumByDouble { c ->
            (-1.0).pow(c.toDouble()) * m[0][c] * matrixDet(matrixMinor(m, 0, c))
        }
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> =
    if (isEmpty() || this[0].isEmpty()) emptyList()
    else (0 until this[0].size).map { col -> map { it[col] } }

fun matrixInverse(m: List<List<Double>>): List<List<Double>> {
    val determinant = matrixDet(m)
    val cofactors = m.indices.map { r ->
        m[0].indices.map { c ->
            (-1.0).pow((r + c).toDouble()) * matrixDet(matrixMinor(m, r, c))
        }
    }.transpose()

    return cofactors.map { row ->
        row.map { it / determinant }
    }
}

fun matrixMul(m: List<List<Double>>, vec: List<Double>): List<Double> {
    return m.map { row ->
        row.zip(vec).sumByDouble { (r, v) -> r * v }
    }
}

fun vectorDiff(a: DoubleCoord3d, b: DoubleCoord3d): DoubleCoord3d {
    return DoubleCoord3d(a.x - b.x, a.y - b.y, a.z - b.z)
}

fun getEquations(a: DoubleCoord3d, va: Coord3d, b: DoubleCoord3d, vb: Coord3d): Pair<List<List<Double>>, List<Double>> {
    val vaDouble = DoubleCoord3d(va.x.toDouble(), va.y.toDouble(), va.z.toDouble())
    val vbDouble = DoubleCoord3d(vb.x.toDouble(), vb.y.toDouble(), vb.z.toDouble())

    val dx = vectorDiff(a, b)
    val dvx = vectorDiff(vaDouble, vbDouble)

    val A = listOf(
        listOf(0.0, -dvx.z, dvx.y, 0.0, -dx.z, dx.y),
        listOf(dvx.z, 0.0, -dvx.x, dx.z, 0.0, -dx.x),
        listOf(-dvx.y, dvx.x, 0.0, -dx.y, dx.x, 0.0)
    )

    val B = listOf(
        b.y * vb.z - b.z * vb.y - (a.y * va.z - a.z * va.y),
        b.z * vb.x - b.x * vb.z - (a.z * va.x - a.x * va.z),
        b.x * vb.y - b.y * vb.x - (a.x * va.y - a.y * va.x)
    )

    return A to B
}

fun solve(hailstones: List<HailStone>): Long {
    val (a, va) = Pair(hailstones[0].coord, hailstones[0].velocity)
    val (b, vb) = Pair(hailstones[1].coord, hailstones[1].velocity)
    val (c, vc) = Pair(hailstones[2].coord, hailstones[2].velocity)

    val (a1, b1) = getEquations(a, va, b, vb)
    val (a2, b2) = getEquations(a, va, c, vc)
    val aSum = a1 + a2
    val bSum = b1 + b2

    val x = matrixMul(matrixInverse(aSum), bSum)
    return x.take(3).sumOf { it.toLong() }
}