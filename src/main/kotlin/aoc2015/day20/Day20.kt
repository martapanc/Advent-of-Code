package aoc2015.day20

fun computeFactorsOfNumber(num: Int, skip50: Boolean = false) : List<Int> {
    val factors = mutableListOf<Int>()
    if (num < 1)
        return factors
    (1..num / 2)
        .filter { num % it == 0 }
        .forEach { factors.add(it) }
    factors.add(num)
    // E.g. after 50, Elf 1 stops delivering gifts. After 100, Elf 2 stops, etc.
    if (skip50) {
        return factors.filter { it > (num - 1) / 50 }
    }
    return factors
}

fun computeGiftsDeliveredAtHouseNumber(houseNumber: Int, multiplier: Int = 10): Int {
    return computeFactorsOfNumber(houseNumber, multiplier == 1).sumBy { it * multiplier }
}

fun findHouseWithNGiftsDelivered(numOfGifts: Int): Int {
    return (665000..666000).firstOrNull { computeGiftsDeliveredAtHouseNumber(it) >= numOfGifts } ?: -1
}

fun findHouseWithNGiftsDeliveredPart2(numOfGifts: Int): Int {
    return (704600..706600).firstOrNull { computeGiftsDeliveredAtHouseNumber(it, 11) >= numOfGifts } ?: -1
}