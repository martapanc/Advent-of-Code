package aoc2021.day08

fun readToListsOfPatternsAndOutputs(inputList: List<String>): Pair<List<String>, List<String>> {
    val patterns = mutableListOf<String>()
    val outputValues = mutableListOf<String>()

    inputList.forEach { line ->
        val split = line.split(" | ")
        split[0].split(" ").forEach { patterns.add(it) }
        split[1].split(" ").forEach { outputValues.add(it) }
    }
    return Pair(patterns, outputValues)
}

fun countUniqueSegments(outputValueList: List<String>): Int {
    return outputValueList.count { it.length != 5 && it.length != 6 }
}

fun decodePatterns(inputList: List<String>): Long {
    var sum = 0L

    inputList.forEach { line ->
        val split = line.split(" | ")
        val patterns = split[0].split(" ").toMutableList()

        val numberToPatternMap = mutableMapOf<Int, String>()
        (0..9).forEach { numberToPatternMap[it] = "" }
        numberToPatternMap[1] = patterns.first { it.length == 2 }
        numberToPatternMap[4] = patterns.first { it.length == 4 }
        numberToPatternMap[7] = patterns.first { it.length == 3 }
        numberToPatternMap[8] = patterns.first { it.length == 7 }
        patterns.removeIf { it.length != 5 && it.length != 6 }

        val one = numberToPatternMap[1]!!
        numberToPatternMap[6] = patterns.first { _6HasSixSegmentsAndLacksOneSegmentOf1(it, one) }
        patterns.remove(numberToPatternMap[6])
        numberToPatternMap[9] = patterns.first { _9HasSixSegmenstAndBothOf4Minus1(it, one, numberToPatternMap[4]!!) }
        patterns.remove(numberToPatternMap[9])
        numberToPatternMap[0] = patterns.first { it.length == 6 }

        numberToPatternMap[3] = patterns.first { _3HasFiveSegmentsAndBothSegmentsOf1(it, one) }
        val topSegmentOf1 = _getTopSegmentOf1(numberToPatternMap[6]!!)
        numberToPatternMap[5] = patterns.first { _5HasFiveSegmentsAndLacksTopSegmentOf1(it, topSegmentOf1) }
        patterns.removeIf { it == numberToPatternMap[3] || it == numberToPatternMap[5] }
        numberToPatternMap[2] = patterns.first { it.length == 5 }

        val sortedPatternsToNum =
            numberToPatternMap.entries.associateBy({ it.value.toSortedSet().joinToString("") }) { it.key }

        val sortedOutputValues = mutableListOf<String>()
        split[1].split(" ").forEach { sortedOutputValues.add(it.toSortedSet().joinToString("")) }
        var outputNumber = ""
        sortedOutputValues.forEach {
            outputNumber += sortedPatternsToNum[it]
        }
        sum += outputNumber.toInt()
    }

    return sum
}

private fun _6HasSixSegmentsAndLacksOneSegmentOf1(pattern: String, one: String): Boolean {
    return pattern.length == 6
            && one.contains(
        toCharSet("abcdefg").subtract(toCharSet(pattern)).first()
    )
}

private fun _3HasFiveSegmentsAndBothSegmentsOf1(pattern: String, one: String): Boolean {
    return pattern.length == 5 && toCharSet(pattern).containsAll(toCharSet(one))
}

private fun _9HasSixSegmenstAndBothOf4Minus1(pattern: String, one: String, four: String): Boolean {
    val fourMinusOne = toCharSet(four).subtract(toCharSet(one))
    return pattern.length == 6 && toCharSet(pattern).containsAll(fourMinusOne)
}

private fun _5HasFiveSegmentsAndLacksTopSegmentOf1(pattern: String, topSegmentOf1: Char): Boolean {
    return pattern.length == 5 && !pattern.contains(topSegmentOf1)
}

private fun _getTopSegmentOf1(six: String): Char {
    return toCharSet("abcdefg").subtract(toCharSet(six)).first()
}

fun toCharSet(string: String): Set<Char> {
    return string.toCharArray().toSet()
}
