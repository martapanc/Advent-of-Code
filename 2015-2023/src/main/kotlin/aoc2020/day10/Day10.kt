package aoc2020.day10

import java.io.File

fun readInputToList(path: String): List<Int> {
    val lineList = mutableListOf<Int>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it.toInt()) }
    lineList.sort()
    lineList.add(0, 0)
    lineList.add(lineList[lineList.size - 1] + 3)
    return lineList
}

fun findJoltsDifferences(list: List<Int>): Int {
    var diffOf1Count = 0
    var diffOf3Count = 0
    for (i in 0 until list.size - 1) {
        when (list[i + 1] - list[i]) {
            1 -> diffOf1Count++
            3 -> diffOf3Count++
        }
    }
    return diffOf1Count * diffOf3Count
}

fun findPermutations(list: List<Int>): Long {
    var product: Long = 1
    generateGroups(list)
        .asSequence()
        .filter { it.size >= 3 }
        .forEach { product *= getNumberOfValidSubsets(it.subList(1, it.size - 1)) }
    return product
}

private fun generateGroups(list: List<Int>): List<List<Int>> {
    val groups = mutableListOf<List<Int>>()
    var group = mutableListOf(list[0])
    for (i in 0 until list.size - 1) {
        if (list[i + 1] - list[i] == 3) {
            groups.add(group)
            group = mutableListOf()
        }
        group.add(list[i])
    }
    groups.add(group)
    return groups
}

fun getNumberOfValidSubsets(list: List<Int>): Int {
    return generateValidSubsets(list).size
}

fun generateValidSubsets(list: List<Int>): List<List<Int>> {
    val validSubsets = mutableListOf<List<Int>>()
    val subsetsList = generateSubsets(list)
    val min = list[0] - 1
    val max = list[list.size - 1] + 1

    for (subset in subsetsList) {
        val extendedSubset = subset.toMutableList()
        extendedSubset.add(0, min)
        extendedSubset.add(max)
        if (isMaxDifferenceLessThan3(extendedSubset)) {
            validSubsets.add(subset)
        }
    }
    return validSubsets
}

// 1<<j (1 shl j) is a number with jth bit 1: when we 'and' them with the subset number we get which numbers are in the subset and which are not
fun generateSubsets(list: List<Int>): List<List<Int>> {
    return (0 until (1 shl list.size)).map { i ->
        (list.indices)
            .filter { i and (1 shl it) > 0 }
            .map { list[it] }
    }
}

private fun isMaxDifferenceLessThan3(extendedSubset: MutableList<Int>): Boolean {
    return (0 until extendedSubset.size - 1)
        .none { extendedSubset[it + 1] - extendedSubset[it] > 3 }
}