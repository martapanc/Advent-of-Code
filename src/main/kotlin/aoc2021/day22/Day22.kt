package aoc2021.day22

import aoc2020.day17.Coord3d
import util.readInputLineByLine

fun readInputToCoordRanges(inputString: String): List<CoordRange> {
  val coordRanges = mutableListOf<CoordRange>()
  readInputLineByLine(inputString).forEach { line ->
    val on = line.split(" ")[0] == "on"
    val split = line.split(" ")[1].split(",")
    val xRange = pairs(split, "x", 0)
    val yRange = pairs(split, "y", 1)
    val zRange = pairs(split, "z", 2)
    coordRanges.add(CoordRange(on, xRange, yRange, zRange))
  }
  return coordRanges
}

private fun pairs(split: List<String>, value: String, idx: Int): Pair<Int, Int> {
  val values = split[idx].replace("$value=", "").split("..").map { it.toInt() }
  return Pair(values[0], values[1])
}

fun initialiseReactor(coordRanges: List<CoordRange>, isPart2: Boolean = false): Long {
  val reactorMap = mutableMapOf<Coord3d, Boolean>()
  coordRanges.forEach { range ->
    if (isPart2 || areCoordsInRange(range)) {
      (range.zRange.first..range.zRange.second).forEach { z ->
        (range.yRange.first..range.yRange.second).forEach { y ->
          (range.xRange.first..range.xRange.second).forEach { x ->
            val coord = Coord3d(x, y, z)
            reactorMap[coord] = range.on
          }
        }
      }
    }
  }

  var count = 0L
  reactorMap.values.forEach { if (it) count++ }
  return count
}

fun areCoordsInRange(coordRange: CoordRange, min: Int = -50, max: Int = 50): Boolean {
  var coordsInRange = true
  coordRange.getRanges().forEach { range ->
    if (range.first < min || range.second > max)
      coordsInRange = false
  }
  return coordsInRange
}

data class CoordRange(
  val on: Boolean = false,
  val xRange: Pair<Int, Int>,
  val yRange: Pair<Int, Int>,
  val zRange: Pair<Int, Int>
) {
  override fun toString(): String {
    return "{" + (if (on) "ON" else "OFF") + ", x=$xRange, y=$yRange, z=$zRange}"
  }

  fun getRanges() = listOf(xRange, yRange, zRange)
}
