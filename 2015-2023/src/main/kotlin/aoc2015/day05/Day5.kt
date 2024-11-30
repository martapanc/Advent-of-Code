package aoc2015.day05

import util.readInputLineByLine

fun isNiceString(string: String): Boolean {
    val vowels = Regex("[aeiou]")
    val doubleLetters = Regex("(.)\\1")
    val forbidden = Regex("(ab|cd|pq|xy)")
    return vowels.findAll(string).count() >= 3 &&
            doubleLetters.findAll(string).count() >= 1 &&
            !string.contains(forbidden)
}

fun isNiceStringPart2(string: String): Boolean {
    val nonOverlappingTwoLetterPairs = Regex("(..).*\\1")
    val twoLettersWithOneInBetween = Regex("(.).\\1")
    return nonOverlappingTwoLetterPairs.findAll(string).count() >= 1 &&
            twoLettersWithOneInBetween.findAll(string).count() >= 1
}

fun countNiceStrings(path: String, validationMethod: (String) -> Boolean): Int {
    return readInputLineByLine(path).count { validationMethod(it) }
}