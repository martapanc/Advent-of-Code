package aoc2016.day19

import org.magicwerk.brownies.collections.GapList

fun findThievingElf(elfNumber: Int): Int {
    val elfMap = mutableMapOf<Int, Elf>()
    for (i in 0 until elfNumber)
        elfMap[i] = Elf(elfId = i + 1)

    while (!elfMap.values.any { it.presents == elfNumber }) {
        for (i in 0 until elfNumber) {
            if (elfMap[i]!!.presents > 0) {
                var candidateIndex = i + 1
                var targetIndex = candidateIndex % elfNumber
                while (elfMap[targetIndex]!!.presents == 0) {
                    candidateIndex++
                    targetIndex = candidateIndex % elfNumber
                }
                elfMap[i]!!.presents += elfMap[targetIndex]!!.presents
                elfMap[targetIndex]!!.presents = 0
            }
        }
    }
    var thievingElf = -1
    elfMap.entries
        .filter { it.value.presents == elfNumber }
        .forEach { thievingElf = it.value.elfId }
    return thievingElf
}

fun findThievingElfPart2(elfNumber: Int): Int {
    val elvesList = GapList<Int>()
    elvesList += 1..elfNumber

    while (elvesList.size > 1) {
        var i = 0
        while (i < elvesList.size) {
            val targetIndex = (i + elvesList.size / 2) % elvesList.size
            elvesList.removeAt(targetIndex)
            if (targetIndex > i)
                i++
        }
    }
    return elvesList.first()
}

data class Elf(val elfId: Int, var presents: Int = 1)