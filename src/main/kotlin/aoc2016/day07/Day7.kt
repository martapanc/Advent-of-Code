package aoc2016.day07

import util.readInputLineByLine

fun countValidIPs(path: String, isPart2: Boolean = false): Int {
    var validTlsIpCount = 0
    var validSslIpCount = 0
    for (line in readInputLineByLine(path)) {
        val ip = line.split('[', ']').foldIndexed(Array(2) { mutableListOf<String>() }) { idx, acc, it ->
            acc[idx % 2].add(it)
            acc
        }
        val address = ip[0]
        val bracketContent = ip[1]

        val tlsSupport = address.any { matchesABBAPattern(it) } && bracketContent.none { matchesABBAPattern(it) }
        if (tlsSupport) validTlsIpCount++

        val ABAparts = address.flatMap { getABAPattern(it) }
        val BABparts = bracketContent.flatMap { getABAPattern(it) }
        if (ABAparts.map { "${it[1]}${it[0]}${it[1]}" }.any { it in BABparts }) validSslIpCount++
    }

    return if (isPart2) validSslIpCount else validTlsIpCount
}

private fun matchesABBAPattern(input: String): Boolean {
    return (0..input.length - 4)
        .firstOrNull { i -> input[i] != input[i + 1] && (input[i] == input[i + 3] && input[i + 1] == input[i + 2]) } != null
}

private fun getABAPattern(it: String): List<String> {
    return (0..it.length - 3)
        .filter { i -> it[i] != it[i + 1] && it[i] == it[i + 2] }
        .map { i -> it.substring(i, i + 3) }
}