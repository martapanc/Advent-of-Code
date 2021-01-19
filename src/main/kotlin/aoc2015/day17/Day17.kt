package aoc2015.day17

fun countCombinationsOfMinNumOfContainers(liters: Int, containerList: List<Int>): Int {
    val containerCombinations = computeContainerCombinations(containerList, liters)
    val minNumber = containerCombinations.minByOrNull { it.size }!!.size
    return containerCombinations.count { it.size == minNumber }
}

fun countCombinationsOfContainers(liters: Int, containerList: List<Int>): Int {
    return computeContainerCombinations(containerList, liters).size
}

private fun computeContainerCombinations(containerList: List<Int>, liters: Int): MutableList<List<Int>> {
    val containers = mutableListOf<List<Int>>()
    for (i in 1..(1 shl containerList.size)) {
        val list = mutableListOf<Int>()

        containerList.indices.forEach {
            if (i shr it and 1 > 0) {
                list += containerList[it]
            }
        }
        if (list.sum() == liters) {
            containers += list
        }
    }
    return containers
}
