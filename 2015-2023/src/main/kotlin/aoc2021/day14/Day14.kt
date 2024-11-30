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

fun readInputToInsertionRules2(inputString: String): Map<String, Pair<String, String>> {
  val insertionRules = mutableMapOf<String, Pair<String, String>>()
  for (line in readInputLineByLine(inputString)) {
    val split = line.split(" -> ")
    // E.g. CH -> B becomes CH -> CB, BH for easier replacement
    insertionRules[split[0]] = Pair(split[0][0] + split[1], split[1] + split[0][1])
  }
  return insertionRules
}

fun computePolymerisation(insertionRules: Map<String, String>, initialPolymer: String, iterations: Int = 1): String {
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

fun computeChecksum(insertionRules: Map<String, String>, initialPolymer: String): Int {
  val finalPolymer = computePolymerisation(insertionRules, initialPolymer, 10)
  val frequencies = mutableMapOf<Char, Int>()
  for (uniqueChar: Char in finalPolymer.toCharArray().toSet()) {
    frequencies[uniqueChar] = finalPolymer.count { it == uniqueChar }
  }
  return frequencies.maxOf { it.value } - frequencies.minOf { it.value }
}

fun computePolymerisationOptimised(
  insertionRules: Map<String, Pair<String, String>>,
  initialPolymer: String,
  iterations: Int = 1
): Map<String, Long> {
  var polymer = mutableMapOf<String, Long>()
  initialPolymer.windowed(2, 1).forEach { pair -> polymer[pair] = 1 }
  (1..iterations).forEach { _ ->
    val newPolymer = mutableMapOf<String, Long>()
    for (pair in polymer.entries) {
      val newPairs = insertionRules[pair.key]!!

      if (newPolymer.containsKey(newPairs.first)) {
        newPolymer[newPairs.first] = newPolymer[newPairs.first]!! + pair.value
      } else {
        newPolymer[newPairs.first] = pair.value
      }

      if (newPolymer.containsKey(newPairs.second)) {
        newPolymer[newPairs.second] = newPolymer[newPairs.second]!! + pair.value
      } else {
        newPolymer[newPairs.second] = pair.value
      }
    }

    polymer = newPolymer
  }

  return polymer
}

fun computeChecksumPart2(
  insertionRules: Map<String, Pair<String, String>>,
  initialPolymer: String,
  iterations: Int
): Long {
  val finalPolymer = computePolymerisationOptimised(insertionRules, initialPolymer, iterations)
  val frequencies = mutableMapOf<Char, Long>()

  for (entry in finalPolymer.entries) {
    if (frequencies.containsKey(entry.key[0])) {
      frequencies[entry.key[0]] = frequencies[entry.key[0]]!! + entry.value
    } else {
      frequencies[entry.key[0]] = entry.value
    }
  }

  return frequencies.maxOf { it.value } - frequencies.minOf { it.value }
}
