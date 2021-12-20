package aoc2021.day14

import util.readInputLineByLine

fun readInputToInsertionRules(inputString: String): Map<String, String> {
  val insertionRules = mutableMapOf<String, String>()
  for (line in readInputLineByLine(inputString)) {
    val split = line.split(" -> ")
    insertionRules[split[0]] = split[1]
  }
  return insertionRules
}

fun computePolimerisation(insertionRules: Map<String, String>, initialPolymer: String, iterations: Int = 1): String {
  var newPolymer = initialPolymer
  (1..iterations).forEach { _ ->
    var firstTwoChars = newPolymer.subSequence(0, 2)
    var expandedPolymer = firstTwoChars[0] + insertionRules[firstTwoChars.toString()]!! + firstTwoChars[1]
    newPolymer = newPolymer.removeRange(0, 1)

    while (newPolymer.length >= 2) {
      firstTwoChars = newPolymer.subSequence(0, 2)
      expandedPolymer += insertionRules[firstTwoChars.toString()]!! + firstTwoChars[1]
      newPolymer = newPolymer.removeRange(0, 1)

    }

    newPolymer = expandedPolymer
  }
  return newPolymer
}

fun computeChecksum(insertionRules: Map<String, String>, initialPolymer: String, iterations: Int): Int {
  val finalPolymer = computePolimerisation(insertionRules, initialPolymer, iterations)
  val frequencies = mutableMapOf<Char, Int>()
  for (uniqueChar : Char in finalPolymer.toCharArray().toSet()) {
    frequencies[uniqueChar] = finalPolymer.count { it == uniqueChar }
  }
  return frequencies.maxOf { it.value } - frequencies.minOf { it.value }
}

