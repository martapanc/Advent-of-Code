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

//fun getLanternfishGrowthPart2(lanternfishList: List<Int>, days: Int = 1): Int {
//
//}