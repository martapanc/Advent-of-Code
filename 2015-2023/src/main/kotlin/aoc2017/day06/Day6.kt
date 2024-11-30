package aoc2017.day06

fun runRedistributionProgram(list: MutableList<Int>, isPart2: Boolean = false): Int {
    val seenList = mutableListOf<String>()
    var cycleCount = 0
    while (!seenList.contains(toString(list))) {
        seenList.add(toString(list))
        val maxBlocks = list.maxOrNull()!!
        var indexOfMax = list.indexOf(maxBlocks)
        list[indexOfMax] = 0
        for (i in 1..maxBlocks) {
            indexOfMax++
            if (indexOfMax == list.size)
                indexOfMax = 0
            list[indexOfMax]++
        }
        cycleCount++
    }
    if (isPart2)
        return seenList.size - seenList.indexOf(toString(list))
    return cycleCount
}

private fun toString(list: MutableList<Int>) = list.joinToString("")
