package aoc2023.day24

import aoc2022.day18.Coord3d
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder
import org.apache.commons.math3.linear.*
import org.apache.commons.math3.linear.Array2DRowRealMatrix

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
    val equationMap = hailStones.associateWith { hailStone -> equation3d(hailStone.coord, hailStone.velocity) }

    // Calculate linear equations
    val equation1 = calculateLinearEquation(hailStones[0], hailStones[1])
    val equation2 = calculateLinearEquation(hailStones[2], hailStones[3])
    val equation3 = calculateLinearEquation(hailStones[1], hailStones[4])
    val equation4 = calculateLinearEquation(hailStones[1], hailStones[2])

    val solution = solveLinearSystem(arrayOf(equation1, equation2, equation3, equation4))

    return 0
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

data class Line3d(val a: Int, val b: Int, val c: Int, val d: Double)

fun equation2d(coord: DoubleCoord3d, velocity: Coord3d): Line {
    val coord2 = DoubleCoord3d(coord.x + velocity.x, coord.y + velocity.y, coord.z + velocity.z)

    if (coord2.x - coord.x == 0.0) {
        throw Exception("Division by 0")
    }
    val slope = (coord2.y - coord.y).div(coord2.x - coord.x)
    val intercept = coord.y - slope * coord.x
    return Line(slope, intercept)
}

fun equation3d(point1: DoubleCoord3d, velocity: Coord3d): Line3d {
    val d = -(velocity.x * point1.x + velocity.y * point1.y + velocity.z * point1.z)

    return Line3d(velocity.x, velocity.y, velocity.z, d)
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

data class LinearEquation(val coefficients: DoubleArray, val constant: Double)

fun calculateLinearEquation(h1: HailStone, h2: HailStone): LinearEquation {
    val coefficients = doubleArrayOf(
        h2.velocity.y.toDouble() - h1.velocity.y.toDouble(),
        h1.velocity.x.toDouble() - h2.velocity.x.toDouble(),
        h2.coord.y - h1.coord.y,
        h1.coord.x - h2.coord.x
    )

    val constant = h1.coord.y * h1.velocity.x - h2.coord.y * h2.velocity.x +
            h2.coord.x * h2.velocity.y - h1.coord.x * h1.velocity.y

    return LinearEquation(coefficients, constant)
}

fun solveLinearSystem(matrix: Array<LinearEquation>): RealVector {
    val coefficients = Array2DRowRealMatrix(matrix.map { it.coefficients }.toTypedArray(), false)
    val constants = matrix.map { it.constant }.toDoubleArray()
    val solver: DecompositionSolver = SingularValueDecomposition(coefficients).solver
    return solver.solve(ArrayRealVector(constants, false))
}
