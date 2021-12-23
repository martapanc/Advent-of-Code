package aoc2021.day19

import aoc2020.day17.Coord3d
import util.readInputLineByLine

fun readScannerObservations(inputString: String): List<Scanner> {
  val scanners = mutableListOf<Scanner>()
  var scanner = Scanner(mutableListOf())
  readInputLineByLine(inputString).forEach { line ->
    if (!line.startsWith("--- scanner") && line.isNotEmpty()) {
      val split = line.split(",").map { it.toInt() }
      scanner.observations.add(Coord3d(split[0], split[1], split[2]))
    }
    if (line.isEmpty()) {
      scanners.add(scanner)
      scanner = Scanner(mutableListOf())
    }
  }
  return scanners
}

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

// Translate every beacon in scanner1 with first combination
// If the orientation is right, a linear translation should
fun findBeacons3(scanners: List<Scanner>): Int {
  val scanner0 = scanners[0]
  val scanner1 = scanners[1]

  for (index: Int in 0 until 24) {
    val translations = scanner1.observations.map { rotate(it, index) }
    val differences = mutableListOf<Coord3d>()
    for (b0 in scanner0.observations) {
      for (b1 in translations) {
        differences.add(Coord3d(b1.x + b0.x, b1.y + b0.y, b1.z + b0.z))
      }
    }
    val res = differences.groupingBy { it }.eachCount().filter { it.value >= 12 }
    if (res.isNotEmpty()) {
      val allBeacons = scanner0.observations.toMutableSet()
      val scannerCoord = res.keys.toList()[0]
      for (beacon in translations) {
        allBeacons.add(Coord3d(
            scannerCoord.x - beacon.x,
            scannerCoord.y - beacon.y,
            scannerCoord.z - beacon.z
        ))
      }
      println()
    }

  }
  return -1
}

class Scanner(val observations: MutableList<Coord3d>)

fun rotationList(initial: Coord3d): MutableList<Coord3d> {
  val x = initial.x
  val y = initial.y
  val z = initial.z
  val rotationList = mutableListOf<Coord3d>()
  rotationList.add(Coord3d(x, y, z))
  rotationList.add(Coord3d(x, y, -z))
  rotationList.add(Coord3d(x, -y, z))
  rotationList.add(Coord3d(x, -y, -z))
  rotationList.add(Coord3d(-x, y, z))
  rotationList.add(Coord3d(-x, y, -z))
  rotationList.add(Coord3d(-x, -y, z))
  rotationList.add(Coord3d(-x, -y, -z))

  rotationList.add(Coord3d(y, z, x))
  rotationList.add(Coord3d(y, z, -x))
  rotationList.add(Coord3d(y, -z, x))
  rotationList.add(Coord3d(y, -z, -x))
  rotationList.add(Coord3d(-y, z, x))
  rotationList.add(Coord3d(-y, z, -x))
  rotationList.add(Coord3d(-y, -z, x))
  rotationList.add(Coord3d(-y, -z, -x))

  rotationList.add(Coord3d(z, x, y))
  rotationList.add(Coord3d(z, x, -y))
  rotationList.add(Coord3d(z, -x, y))
  rotationList.add(Coord3d(z, -x, -y))
  rotationList.add(Coord3d(-z, x, y))
  rotationList.add(Coord3d(-z, x, -y))
  rotationList.add(Coord3d(-z, -x, y))
  rotationList.add(Coord3d(-z, -x, -y))
  return rotationList
}

fun rotate(coord3d: Coord3d, rotationId: Int): Coord3d {
  return rotationList(coord3d)[rotationId]
}
