package aoc2021.day08

fun readToListsOfPatternsAndOutputs(inputList: List<String>): Pair<List<String>, List<String>> {
    val patterns = mutableListOf<String>()
    val outputValues = mutableListOf<String>()

    inputList.forEach { line ->
        val split = line.split(" | ")
        split[0].split(" ").forEach{ patterns.add(it)}
        split[1].split(" ").forEach{ outputValues.add(it)}
    }
    return Pair(patterns, outputValues)
}

fun countUniqueSegments(outputValueList: List<String>): Int {
    return outputValueList.count { it.length != 5 && it.length != 6 }
}
