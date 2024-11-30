package aoc2021.day06

fun getLanternfishGrowth(lanternfishList: List<Int>, days: Int = 1): Int {
    var mutableLanternfishList = lanternfishList.toMutableList()
    for (day in 0 until days) {
        val updatedLanternfishList = mutableLanternfishList.toMutableList()
        mutableLanternfishList.forEachIndexed { index, lanternFish: Int ->
            if (lanternFish == 0) {
                updatedLanternfishList[index] = 6
                updatedLanternfishList.add(8)
            } else {
                updatedLanternfishList[index] -= 1
            }
        }
        mutableLanternfishList = updatedLanternfishList.toMutableList()
    }
    return mutableLanternfishList.size
}

fun getLanternfishGrowthPart2(lanternfishList: List<Int>, days: Int = 1): Long {
    var lanternfishMap = getDefaultMap()

    for (lanternfish: Int in lanternfishList) {
        lanternfishMap[lanternfish] = lanternfishMap[lanternfish]!! + 1
    }

    for (day in 0 until days) {
        val newFish = getDefaultMap()
        for (fishTimerToQuantity: Map.Entry<Int, Long> in lanternfishMap.entries) {
            val fishTimer = fishTimerToQuantity.key - 1
            val fishQuantity = fishTimerToQuantity.value

            if (fishTimerToQuantity.key == 0) {
                newFish[6] = newFish[6]!! + fishQuantity
                newFish[8] = newFish[8]!! + fishQuantity
            } else {
                newFish[fishTimer] = newFish[fishTimer]!! + fishQuantity
            }
        }
        lanternfishMap = newFish
    }

    return lanternfishMap.values.sum()
}

private fun getDefaultMap(): MutableMap<Int, Long> {
    val defaultMap = mutableMapOf<Int, Long>()
    (0..8).forEach { defaultMap[it] = 0 }
    return defaultMap
}