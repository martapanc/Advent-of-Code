package aoc2016.day19

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
                val presentsStolen = elfMap[targetIndex]!!.presents
                elfMap[i]!!.presents = elfMap[i]!!.presents + presentsStolen
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

data class Elf(val elfId: Int, var presents: Int = 1)