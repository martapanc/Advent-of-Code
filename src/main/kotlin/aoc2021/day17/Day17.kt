package aoc2021.day17

import aoc2020.day20.Coord

fun parseInput(inputString: String): Range {
  val split = inputString.split(", ")
  val splitX = split[0].replace("x=", "").split("..")
  val splitY = split[1].replace("y=", "").split("..")
  return Range(splitX[0].toInt(), splitX[1].toInt(), splitY[0].toInt(), splitY[1].toInt())
}

fun findTrajectory(input: String): Int {
  val range = parseInput(input)

  val result = fireProbe(7, 2, range)
  return -1
}

fun fireProbe(initHorizontalVelocity: Int, initVerticalVelocity: Int, range: Range): HitOrMiss {
  val position = Coord(0, 0)
  var maxYReached = position.y
  var horizontalVelocity = initHorizontalVelocity
  var verticalVelocity = initVerticalVelocity

  while (!isPositionInRange(position, range)) {
    if (targetMissed(position, range))
      return HitOrMiss(false, maxYReached)
    position.x += horizontalVelocity
    position.y += verticalVelocity

    if (maxYReached < position.y) {
      maxYReached = position.y
    }

    if (horizontalVelocity > 0) {
      horizontalVelocity--
    } else if (horizontalVelocity < 0) {
      horizontalVelocity++
    }
    verticalVelocity--
  }

  return HitOrMiss(true, maxYReached)
}

fun isPositionInRange(probePosition: Coord, range: Range): Boolean {
  return probePosition.x >= range.minX && probePosition.x <= range.maxX &&
    probePosition.y >= range.minY && probePosition.y <= range.maxY
}

fun targetMissed(probePosition: Coord, range: Range): Boolean {
  return probePosition.x > range.maxX && probePosition.y < range.maxY
}


class Range(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

class HitOrMiss(val hitTarget: Boolean, val highestY: Int)

fun printGrid(range: Range) {
  for (y in 0 downTo range.minY ) {
    for (x in 0 .. range.maxX) {
      if (isPositionInRange(Coord(x, y), range)) {
        print("T")
      } else {
        print(".")
      }
    }
    println()
  }
}
