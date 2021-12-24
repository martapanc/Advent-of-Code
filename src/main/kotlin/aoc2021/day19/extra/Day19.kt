package aoc2021.day19.extra

import util.readInputLineByLine
import kotlin.math.absoluteValue

class Day19(input: String) {

  private val scanners: List<Set<Point3d>> = parseInput(input)

  fun solvePart1(): Int =
    solve().beacons.size

//  fun solvePart2(): Int =
//    solve().scanners.pairs().maxOf { it.first distanceTo it.second }

  private fun solve(): Solution {
    val baseSector = scanners.first().toMutableSet()
    val foundScanners = mutableSetOf(Point3d(0,0,0))
    val unmappedSectors = ArrayDeque<Set<Point3d>>().apply { addAll(scanners.drop(1)) }
    while(unmappedSectors.isNotEmpty()) {
      val thisSector = unmappedSectors.removeFirst()
      when (val transform = findTransformIfIntersects(baseSector, thisSector)) {
        null -> unmappedSectors.add(thisSector)
        else -> {
          baseSector.addAll(transform.beacons)
          foundScanners.add(transform.scanner)
        }
      }
    }
    return Solution(foundScanners, baseSector)
  }

  private fun findTransformIfIntersects(left: Set<Point3d>, right: Set<Point3d>): Transform? =
    (0 until 6).firstNotNullOfOrNull { face ->
      (0 until 4).firstNotNullOfOrNull { rotation ->
        val rightReoriented = right.map { it.face(face).rotate(rotation) }.toSet()
        left.firstNotNullOfOrNull { s1 ->
          rightReoriented.firstNotNullOfOrNull { s2 ->
            val difference = s1 - s2
            val moved = rightReoriented.map { it + difference }.toSet()
            if (moved.intersect(left).size >= 12) {
              Transform(difference, moved)
            } else null
          }
        }
      }
    }

  private class Transform(val scanner: Point3d, val beacons: Set<Point3d>)
  private class Solution(val scanners: Set<Point3d>, val beacons: Set<Point3d>)

  private fun parseInput(input: String): List<Set<Point3d>> =
    readInputLineByLine(input).joinToString("\n").split("\n\n").map { singleScanner ->
      singleScanner
        .lines()
        .drop(1)
        .map { Point3d.of(it) }
        .toSet()
    }
}

data class Point3d(val x: Int, val y: Int, val z: Int) {

  infix fun distanceTo(other: Point3d): Int =
    (this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue + (this.z - other.z).absoluteValue

  operator fun plus(other: Point3d): Point3d =
    Point3d(x + other.x, y + other.y, z + other.z)

  operator fun minus(other: Point3d): Point3d =
    Point3d(x - other.x, y - other.y, z - other.z)

  fun face(facing: Int): Point3d =
    when (facing) {
      0 -> this
      1 -> Point3d(x, -y, -z)
      2 -> Point3d(x, -z, y)
      3 -> Point3d(-y, -z, x)
      4 -> Point3d(y, -z, -x)
      5 -> Point3d(-x, -z, -y)
      else -> error("Invalid facing")
    }

  fun rotate(rotating: Int): Point3d =
    when (rotating) {
      0 -> this
      1 -> Point3d(-y, x, z)
      2 -> Point3d(-x, -y, z)
      3 -> Point3d(y, -x, z)
      else -> error("Invalid rotation")
    }

  companion object {
    fun of(rawInput: String): Point3d =
      rawInput.split(",").let { part ->
        Point3d(part[0].toInt(), part[1].toInt(), part[2].toInt())
      }
  }

}
