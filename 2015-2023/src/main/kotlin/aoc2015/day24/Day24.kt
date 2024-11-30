package aoc2015.day24

import org.paukov.combinatorics3.Generator


fun balancePresents(inputList: List<Int>, isPart2: Boolean = false): Long? {
    val totalSum = inputList.sum()
    val groupWeight = totalSum / if (isPart2) 4 else 3
    val validPackages = mutableListOf<List<Int>>()
    var groupSize = 1
    while (validPackages.isEmpty()) {
        Generator.combination(inputList).simple(groupSize)
            .filterTo(validPackages) { it.sum() == groupWeight }
        groupSize++
    }

    val quantumEntanglements = mutableListOf<Long>()
    for (validPackage in validPackages) {
        var product = 1L
        for (item in validPackage) product *= item
        quantumEntanglements.add(product)
    }
    return quantumEntanglements.minOrNull()
}



