package aoc2021.day19.extra

import aoc2020.day17.Coord3d
import aoc2021.day19.Scanner

fun findBeacons(scanners: List<Scanner>): Int {
  val relativeDistancesList = mutableListOf<Map<Coord3d, List<Coord3d>>>()
  val uniqueBeacons = mutableSetOf<Coord3d>()
  val distanceSet = mutableSetOf<Coord3d>()
  val beaconMatches = mutableMapOf<Coord3d, MutableSet<Coord3d>>()

  scanners.forEach { scanner ->
    val distancesRelativeToBeacons = mutableMapOf<Coord3d, List<Coord3d>>()
    // Foreach beacon in each scanner, compute relative distance to all other beacons
    scanner.observations.forEach { beacon ->
      val distances = mutableListOf<Coord3d>()

      for (index in 0 until scanner.observations.size) {
        val relativeBeacon = scanner.observations[index]
        if (beacon != relativeBeacon) {
          val d = Coord3d(
            relativeBeacon.x - beacon.x,
            relativeBeacon.y - beacon.y,
            relativeBeacon.z - beacon.z
          )
          distances.add(d)
          if (distanceSet.contains(d)) {
            uniqueBeacons.add(beacon)
            if (beaconMatches[beacon] == null) {
              beaconMatches[beacon] = mutableSetOf()
            }

            val otherScannerBeacon = relativeDistancesList.find { it.values.any { v -> v.contains(d) } }!!
              .filter { it.value.contains(d) }.keys.toList()
            beaconMatches[beacon]!!.addAll(otherScannerBeacon)
          } else {
            distanceSet.add(d)
          }
        }
      }
      distancesRelativeToBeacons[beacon] = distances
    }

    relativeDistancesList.add(distancesRelativeToBeacons)
  }

  return uniqueBeacons.size
}
