package aoc2018.day20

import com.github.curiousoddman.rgxgen.RgxGen

fun findLongestUniquePathToFurthestDoor(input: String): Int {
    val stack = ArrayDeque<Char>()
    for (char in input.substring(1, input.length - 1)) {
        if (char == ')') {
            var lastElem = stack.last()
            var bracketContent = ""
            while (lastElem != '(') {
                bracketContent = "$lastElem$bracketContent"
                stack.removeLast()
                lastElem = stack.last()
            }
            stack.removeLast()
            for (c in splitRegex(bracketContent)) {
                stack.add(c)
            }
        } else {
            stack.add(char)
        }
    }
    return stack.size
}

fun countPathsPassingThrough1000Doors(input: String): Int {
    val stack = ArrayDeque<Char>()
    for (char in input.substring(1, input.length - 1)) {
        if (char == ')') {
            var lastElem = stack.last()
            var bracketContent = ""
            while (lastElem != '(') {
                bracketContent = "$lastElem$bracketContent"
                stack.removeLast()
                lastElem = stack.last()
            }
            stack.removeLast()
            for (c in reduceRegex(bracketContent)) {
                stack.add(c)
            }
        } else {
            stack.add(char)
        }
    }
    val reduced = stack.joinToString("")
    val matched = RgxGen(reduced).stream().dropWhile { it.length < 1000 }.count()
    return stack.size
}

fun splitRegex(string: String): String {
    val regexParts = string.split("|")
    return when {
        regexParts.any { it.isEmpty() } -> ""
        else -> regexParts.maxByOrNull { it.length }!!
    }
}

fun reduceRegex(string: String): String {
    val regexParts = string.split("|")
    return when {
        regexParts.any { it.isEmpty() } -> ""
        else -> "($string)"
    }
}
