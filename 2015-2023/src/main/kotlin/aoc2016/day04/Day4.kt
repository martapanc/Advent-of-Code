package aoc2016.day04

import util.readInputLineByLine

fun countValidDoorNumbers(path: String): Int {
    var checksumAcc = 0
    for (line in readInputLineByLine(path)) {
        val split = line.split("[", "]")
        val doorId = split[0].substringBeforeLast("-")
        val sectorId = split[0].substringAfterLast("-").toInt()
        val checksum = split[1]
        if (find5MostCommonCharsSorted(doorId) == checksum) {
            checksumAcc += sectorId
        }
    }
    return checksumAcc
}

fun decryptAndFindDoor(path: String): Int {
    for (line in readInputLineByLine(path)) {
        val split = line.split("[", "]")
        val doorId = split[0].substringBeforeLast("-")
        val sectorId = split[0].substringAfterLast("-").toInt()
        if (decrypt(doorId, sectorId) == "northpole object storage") {
            return sectorId
        }
    }
    return -1
}

// Group by frequencies, convert to pairs, sort descending by count and then by character
fun find5MostCommonCharsSorted(input: String): String {
    val groups = input
        .replace("-", "")
        .groupBy { it }
        .mapValues { it.value.size }
        .toList()
        .sortedWith(compareBy({ 0 - it.second }, { it.first }))
        .take(5)
        .map { it.first }
    return groups.joinToString("")
}

fun decrypt(input: String, offset: Int = 1): String {
    var output = ""
    for (char in input) {
        output += if (char == '-') ' ' else shiftChar(char, offset)
    }
    return output
}

private fun shiftChar(char: Char, sectodId: Int): Char =
    ((((char - 'a') + sectodId) % 26) + 'a'.toInt()).toChar()