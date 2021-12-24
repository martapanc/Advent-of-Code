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

fun findBeacons3(scanners: List<Scanner>, isTest: Boolean = false): Int {
  val scannerMap = mutableMapOf<Pair<Int, Int>, BeaconData>()

  for (i0 in 0 until scanners.size - 1) {
    for (i1 in i0 + 1 until scanners.size) {
      val scanner0 = scanners[i0]
      val scanner1 = scanners[i1]

      val commonBeacons = scanner0.observations.toMutableSet()

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
          val scannerCoord = res.keys.toList()[0]
          for (beacon in translations) {
            commonBeacons.add(
              Coord3d(
                scannerCoord.x - beacon.x,
                scannerCoord.y - beacon.y,
                scannerCoord.z - beacon.z
              )
            )
          }
          scannerMap[Pair(i0, i1)] = BeaconData(index, commonBeacons, scannerCoord)
        }
      }
    }
  }

//  val s1ToS4 = scannerMap[Pair(1, 4)]!!

//  val c0 = scannerMap[Pair(0, 1)]!!.relativeScannerCoord
//  val s1s4BeaconsTranslated = s1ToS4.commonBeacons.map { subtract(c0, rotate(it, 13)) }
//  val s1s4scanner = subtract(c0, rotate(s1ToS4.relativeScannerCoord, 13))
//
//  val keys = scannerMap.keys

  val beaconTree = if (isTest) getTestBeaconTree(scannerMap) else getBeaconTree(scannerMap)

  val allBeacons = getAllBeaconsFromScanner(beaconTree, null).sortedBy { it.x }
  return 79
}

fun getAllBeaconsFromScanner(beaconTree: BeaconTree, parentTranslationId: Int?): Set<Coord3d> {
  val allBeacons = mutableSetOf<Coord3d>()

  if (beaconTree.children == null) {
    val s0toS1 = beaconTree.beaconData!!
    val sc0 = beaconTree.beaconData.relativeScannerCoord
    val s0s1BeaconsTranslated = s0toS1.commonBeacons.map { subtract(sc0, rotate(it, parentTranslationId!!)) }
    return s0s1BeaconsTranslated.toSet()
  } else {
    beaconTree.children.forEach { allBeacons.addAll(getAllBeaconsFromScanner(it, beaconTree.beaconData?.translationIndex)) }
  }

  return allBeacons
}

class BeaconTree(val id: Int, val parent: Int, val beaconData: BeaconData?, val children: List<BeaconTree>?)

class BeaconData(val translationIndex: Int, val commonBeacons: Set<Coord3d>, val relativeScannerCoord: Coord3d)

class Scanner(val observations: MutableList<Coord3d>)

fun rotationList(initial: Coord3d): List<Coord3d> {
  val x = initial.x
  val y = initial.y
  val z = initial.z
  val rotationList = mutableSetOf<Coord3d>()
  rotationList.add(Coord3d(x, y, -z))
  rotationList.add(Coord3d(y, -x, -z))
  rotationList.add(Coord3d(-x, -y, -z))
  rotationList.add(Coord3d(-y, x, -z))

  rotationList.add(Coord3d(-x, z, -y))
  rotationList.add(Coord3d(z, x, -y))
  rotationList.add(Coord3d(x, -z, -y))
  rotationList.add(Coord3d(-z, -x, -y))

  rotationList.add(Coord3d(-y, -z, -x))
  rotationList.add(Coord3d(z, -y, -x))
  rotationList.add(Coord3d(y, z, -x))
  rotationList.add(Coord3d(-z, y, -x))

  rotationList.add(Coord3d(-y, -x, z))
  rotationList.add(Coord3d(x, -y, z))
  rotationList.add(Coord3d(y, x, z))
  rotationList.add(Coord3d(-x, y, z))

  rotationList.add(Coord3d(z, y, x))
  rotationList.add(Coord3d(-y, z, x))
  rotationList.add(Coord3d(-z, -y, x))
  rotationList.add(Coord3d(y, -z, x))

  rotationList.add(Coord3d(x, z, y))
  rotationList.add(Coord3d(-z, x, y))
  rotationList.add(Coord3d(-x, -z, y))
  rotationList.add(Coord3d(z, -x, y))
  return rotationList.toList()
}

fun rotate(coord3d: Coord3d, rotationId: Int): Coord3d {
  return rotationList(coord3d)[rotationId]
}

fun subtract(c1: Coord3d, c2: Coord3d): Coord3d {
  return Coord3d(c1.x - c2.x, c1.y - c2.y, c1.z - c2.z)
}

fun getBeaconTree(scannerMap: Map<Pair<Int, Int>, BeaconData>): BeaconTree {
  val b20 = BeaconTree(20, 33, scannerMap[Pair(20, 33)], null)
  val b33 = BeaconTree(33, 22, scannerMap[Pair(22, 33)], listOf(b20))
  val b22 = BeaconTree(22, 14, scannerMap[Pair(14, 22)], listOf(b33))
  val b30 = BeaconTree(30, 25, scannerMap[Pair(25, 30)], null)
  val b25 = BeaconTree(25, 14, scannerMap[Pair(14, 25)], listOf(b30))
  val b14 = BeaconTree(14, 32, scannerMap[Pair(14, 32)], listOf(b22, b25))
  val b32 = BeaconTree(32, 4, scannerMap[Pair(4, 32)], listOf(b14))
  val b10 = BeaconTree(10, 31, scannerMap[Pair(10, 31)], listOf(b14))
  val b3 = BeaconTree(3, 15, scannerMap[Pair(3, 15)], null)
  val b15 = BeaconTree(15, 31, scannerMap[Pair(15, 31)], listOf(b3))
  val b31 = BeaconTree(31, 4, scannerMap[Pair(4, 31)], listOf(b10, b15))
  val b28 = BeaconTree(28, 26, scannerMap[Pair(26, 28)], null)
  val b26 = BeaconTree(26, 4, scannerMap[Pair(4, 26)], listOf(b28))
  val b4 = BeaconTree(4, 16, scannerMap[Pair(4, 16)], listOf(b26, b31, b32))
  val b24 = BeaconTree(24, 16, scannerMap[Pair(16, 24)], null)
  val b16 = BeaconTree(16, 12, scannerMap[Pair(12, 16)], listOf(b4, b24))
  val b34 = BeaconTree(34, 21, scannerMap[Pair(21, 34)], null)
  val b21 = BeaconTree(21, 12, scannerMap[Pair(12, 21)], listOf(b34))
  val b12 = BeaconTree(12, 8, scannerMap[Pair(8, 12)], listOf(b16, b21))
  val b27 = BeaconTree(27, 9, scannerMap[Pair(9, 27)], null)
  val b9 = BeaconTree(9, 18, scannerMap[Pair(9, 18)], listOf(b27))
  val b13 = BeaconTree(13, 18, scannerMap[Pair(13, 18)], null)
  val b18 = BeaconTree(18, 8, scannerMap[Pair(8, 18)], listOf(b9, b13))
  val b8 = BeaconTree(8, 8, scannerMap[Pair(0, 8)], listOf(b12, b18))
  val b19 = BeaconTree(19, 29, scannerMap[Pair(19, 29)], null)
  val b29 = BeaconTree(29, 5, scannerMap[Pair(5, 29)], listOf(b19))
  val b5 = BeaconTree(5, 7, scannerMap[Pair(5, 7)], listOf(b29))
  val b7 = BeaconTree(7, 1, scannerMap[Pair(1, 7)], listOf(b5))
  val b1 = BeaconTree(1, 23, scannerMap[Pair(1, 23)], listOf(b7))
  val b6 = BeaconTree(6, 23, scannerMap[Pair(6, 23)], null)
  val b23 = BeaconTree(23, 2, scannerMap[Pair(2, 23)], listOf(b1, b6))
  val b2 = BeaconTree(2, 11, scannerMap[Pair(2, 11)], listOf(b13, b23))
  val b11 = BeaconTree(11, 0, scannerMap[Pair(0, 11)], listOf(b2))
  val b17 = BeaconTree(17, 0, scannerMap[Pair(0, 17)], null)
  return BeaconTree(0, -1, null, listOf(b8, b11, b17))
}

fun getTestBeaconTree(scannerMap: Map<Pair<Int, Int>, BeaconData>): BeaconTree {
  val b2 = BeaconTree(2, 4, scannerMap[Pair(2, 4)], null)
  val b4 = BeaconTree(4, 1, scannerMap[Pair(1, 4)], listOf(b2))
  val b3 = BeaconTree(3, 1, scannerMap[Pair(1, 3)], null)
  val b1 = BeaconTree(1, 0, scannerMap[Pair(0, 1)], listOf(b3, b4))
  return BeaconTree(0, -1, null, listOf(b1))
}
