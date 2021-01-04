package aoc2017.day17

fun runSpinlock(step: Int, maxValue: Long = 2017): Long {
    val list = mutableListOf(0L, 1)
    var index = 1
    for (n in 2..maxValue) {
        index = ((index + step) % list.size + 1) % list.size
        list.add(index, n)
    }
    return list[list.indexOf(2017) + 1]
}