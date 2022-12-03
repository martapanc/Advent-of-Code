package aoc2022.day03

fun computePrioritySumPart1(list: List<String>): Int {
    val itemValueMap = getItemValueMap()
    var prioritySum = 0
    list.forEach { line ->
        val compartment1 = line.subSequence(0, line.length / 2).toSet()
        val compartment2 = line.subSequence(line.length / 2, line.length).toSet()
        val commonItem: Char = compartment1.intersect(compartment2).first()
        prioritySum += itemValueMap[commonItem]!!
    }
    return prioritySum
}

fun computePrioritySumPart2(list: List<String>): Int {
    val itemValueMap = getItemValueMap()
    var prioritySum = 0
    for (i in list.indices step 3) {
        val rucksack1 = list[i].toSet()
        val rucksack2 = list[i + 1].toSet()
        val rucksack3 = list[i + 2].toSet()
        val commonItem: Char = rucksack1.intersect(rucksack2).intersect(rucksack3).first()
        prioritySum += itemValueMap[commonItem]!!
    }
    return prioritySum
}

private fun getItemValueMap(): MutableMap<Char, Int> {
    val itemValueMap = mutableMapOf<Char, Int>()
    var value = 1
    var char = 'a'
    while (char <= 'z') itemValueMap[char++] = value++
    char = 'A'
    while (char <= 'Z') itemValueMap[char++] = value++
    return itemValueMap
}
