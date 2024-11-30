package aoc2017.day21

import util.readInputLineByLine

private val initialGrid = FractalGrid(".#./..#/###")

fun enhanceString(transforms: Map<FractalGrid, FractalGrid>, iterations: Int): Int {
    var grid = initialGrid
    repeat(iterations) {
        val splits = grid.split()
        val next = splits.map { transforms.getValue(it) }
        grid = next.join()
    }
    return grid.toString().count { it == '#' }
}


fun parseInputRow(input: String): Pair<FractalGrid, FractalGrid> =
    input.split(" => ".toRegex())
        .map { FractalGrid(it) }
        .let { it.first() to it.last() }


fun parseInput(path: String): Map<FractalGrid, FractalGrid> =
    readInputLineByLine(path).map { parseInputRow(it) }.flatMap { pair ->
        pair.first.combinations().map { combo ->
            combo to pair.second
        }
    }.toMap()

fun findAlternativePatterns(input: String): Set<String> {
    val patterns = mutableSetOf(input, flipPattern(input))
    var rotated = rotatePatternRight(input)
    var flipped = flipPattern(rotated)
    patterns.add(rotated)
    patterns.add(flipped)
    repeat(2) {
        rotated = rotatePatternRight(rotated)
        flipped = flipPattern(rotated)
        patterns.add(rotated)
        patterns.add(flipped)
    }
    return patterns
}

fun rotatePatternRight(string: String): String {
    val groups = string.split("/")
    val rotatedGroups = if (string.length == 5) {
        groups.indices.map { "" + groups[1][it] + groups[0][it] }
    } else {
        groups.indices.map { "" + groups[2][it] + groups[1][it] + groups[0][it] }
    }
    return rotatedGroups.joinToString("/")
}

fun flipPattern(string: String): String {
    val groups = string.split("/")
    val flippedGroups = groups.map { it.reversed() }
    return flippedGroups.joinToString("/")
}