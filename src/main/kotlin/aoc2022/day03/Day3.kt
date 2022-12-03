package aoc2022.day03

fun computePrioritySum(list: List<String>): Int {
    val itemValueMap = mutableMapOf<Char, Int>()
    var value = 1
    var char = 'a'
    while (char <= 'z') itemValueMap[char++] = value++
    char = 'A'
    while (char <= 'Z') itemValueMap[char++] = value++

    var prioritySum = 0
    list.forEach { line ->
        val compartment1 = line.subSequence(0, line.length / 2).toSet()
        val compartment2 = line.subSequence(line.length / 2, line.length).toSet()
        val commonItems: Char = compartment1.intersect(compartment2).first()
        prioritySum += itemValueMap[commonItems]!!
    }
    return prioritySum
}
