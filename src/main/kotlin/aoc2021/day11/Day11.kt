package aoc2021.day11

import aoc2021.day09.readInputToMap
import aoc2020.day20.Coord

fun readInputToOctopusMap(input: String): Map<Coord, Octopus> {
  val octopusMap = mutableMapOf<Coord, Octopus>()
  for (entry in readInputToMap(input)) {
    octopusMap[entry.key] = Octopus(entry.value, false)
  }
  return octopusMap
}

fun countFlashingOctopi(input: String, steps: Int = 100): Int {
  var flashCount = 0
  var octopiMap = readInputToOctopusMap(input).toMutableMap()
  (1..steps).forEach { _ ->
    val newMap = octopiMap.toMutableMap()

    for (octopus in newMap) {
      newMap[octopus.key]!!.flashed = false
      newMap[octopus.key]!!.energyValue = newMap[octopus.key]!!.energyValue + 1
    }

    while (newMap.values.find { it.energyValue > 9 } != null) {

      val octopiWithMaxEnergy = newMap.filter { it.value.energyValue > 9 }
      for (octopus in octopiWithMaxEnergy) {
        val neighbors = getNeighborOctopi(octopiMap, currentOctopus = octopus.key)
        newMap[octopus.key]!!.energyValue = 0
        newMap[octopus.key]!!.flashed = true

        flashCount++
        for (neighbor: Coord in neighbors) {
          if (!newMap[neighbor]!!.flashed) {
            newMap[neighbor]!!.energyValue = newMap[neighbor]!!.energyValue + 1
          }
        }
      }
    }
    octopiMap = newMap
  }

  return flashCount
}

private fun getNeighborOctopi(octopi: Map<Coord, Octopus>, currentOctopus: Coord): Set<Coord> {
  val neighborOctopi = mutableSetOf<Coord>()
  val (x, y) = Pair(currentOctopus.x, currentOctopus.y)
  val deltas = listOf(
    Coord(x - 1, y - 1), Coord(x, y - 1), Coord(x + 1, y - 1),   // NW N NE
    Coord(x - 1, y), Coord(x + 1, y),                                     // W  .  E
    Coord(x - 1, y + 1), Coord(x, y + 1), Coord(x + 1, y + 1)    // SW S SE
  )

  for (delta: Coord in deltas) {
    val neighbor = octopi[delta]
    if (neighbor != null) neighborOctopi.add(delta)
  }
  return neighborOctopi
}


class Octopus(var energyValue: Int, var flashed: Boolean = false) {
  override fun toString(): String {
    return "{$energyValue, $flashed}"
  }
}
