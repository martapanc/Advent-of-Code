package aoc2015.day08

import util.readInputLineByLine

fun readInputToListAndRun(path: String): Int {
    var literalCharCount = 0
    var inMemoryCharCount = 0

    for (line in readInputLineByLine(path)) {
        literalCharCount += line.length
        inMemoryCharCount += line.substring(1, line.length - 1)
            .replace("\\\\\\W|\\\\x\\w{2}".toRegex(), "*")
            .length
    }
    return literalCharCount - inMemoryCharCount
}

fun partTwo(path: String): Int {
    var literalCharCount = 0
    var expandedCharCount = 0

    for (line in readInputLineByLine(path)) {
        literalCharCount += line.length
        expandedCharCount += line.replace("^\"|\"$".toRegex(), "***")
            .replace("\\\\{2}|\\\\\"".toRegex(), "****")
            .replace("\\\\x".toRegex(), "***")
            .length
    }
    return expandedCharCount - literalCharCount
}