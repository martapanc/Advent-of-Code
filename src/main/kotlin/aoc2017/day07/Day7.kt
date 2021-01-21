package aoc2017.day07

import util.readInputLineByLine

fun readInputAndFindRoot(path: String): String {
    val leftSet = mutableSetOf<String>()
    val rightSet = mutableSetOf<String>()
    for (line in readInputLineByLine(path)) {
        val split = line.split(" -> ")
        val left = split[0].replace(Regex("\\(\\d+\\)"), "")
        leftSet += left.trim()
        if (split.size > 1) {
            rightSet.addAll(split[1].split(", "))
        }
    }
    return leftSet.subtract(rightSet).first()
}