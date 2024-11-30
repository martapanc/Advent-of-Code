package aoc2017.day17

fun runSpinlock(step: Int): Int {
    val list = mutableListOf(0, 1)
    var index = 1
    for (n in 2..2017) {
        index = ((index + step) % list.size + 1) % list.size
        list.add(index, n)
    }
    return list[list.indexOf(2017) + 1]
}

fun runSpinlockV2(step: Int): Int {
    var next = 0
    var valueAtIndexOne = 0
    for (n in 1..50_000_000) {
        next = ((step + next) % n) + 1
        if (next == 1) {
            valueAtIndexOne = n
        }
    }
    return valueAtIndexOne
}