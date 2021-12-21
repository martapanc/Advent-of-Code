package aoc2021.day17

import aoc2020.day20.Coord

fun parseInput(inputString: String): Range {
  val split = inputString.split(", ")
  val splitX = split[0].replace("x=", "").split("..")
  val splitY = split[1].replace("y=", "").split("..")
  return Range(splitX[0].toInt(), splitX[1].toInt(), splitY[0].toInt(), splitY[1].toInt())
}

fun fireProbe(initHorizontalVelocity: Int, initVerticalVelocity: Int, range: Range): HitOrMiss {
  val position = Coord(0, 0)
  var maxYReached = position.y
  var horizontalVelocity = initHorizontalVelocity
  var verticalVelocity = initVerticalVelocity

  while (!isPositionInRange(position, range)) {
    if (verticalVelocity < -10000)
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

fun findAllInitialVelocities(range: Range): Int {
  val initialVelocities = mutableSetOf<Velocity>()

  for (x in 1..152) {
    for (y in -160..160) {
      if (fireProbe(x, y, range).hitTarget) {
        initialVelocities.add(Velocity(x, y))
      }
    }
  }

  return initialVelocities.size
}

fun isPositionInRange(probePosition: Coord, range: Range): Boolean {
  return isInHorizontalRange(probePosition, range) && isInVerticalRange(probePosition, range)
}

private fun isInHorizontalRange(probePosition: Coord, range: Range) =
  probePosition.x >= range.minX && probePosition.x <= range.maxX

private fun isInVerticalRange(probePosition: Coord, range: Range) =
  probePosition.y >= range.minY && probePosition.y <= range.maxY

class Range(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

class HitOrMiss(val hitTarget: Boolean, val highestY: Int)

class Velocity(private val horizontalVelocity: Int, private val verticalVelocity: Int) {
  override fun toString(): String {
    return "($horizontalVelocity, $verticalVelocity)"
  }
}

fun printGrid(range: Range) {
  for (y in 0 downTo range.minY) {
    for (x in 0..range.maxX) {
      if (isPositionInRange(Coord(x, y), range)) print("T") else print(".")
    }
    println()
  }
}
