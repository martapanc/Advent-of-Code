package aoc2021.day07

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import kotlin.math.abs
import kotlin.math.round


fun getMinimumFuel(crabList: List<Int>): Int {
    val stats = DescriptiveStatistics()
    crabList.forEach { stats.addValue(it.toDouble()) }
    val median = stats.getPercentile(50.0).toInt()

    val deltaToFuel = mutableMapOf<Int, Int>()

    for (delta : Int in median - 5 .. median + 5) {
        val fuelAccumulator = crabList.sumBy { abs(it - delta) }
        deltaToFuel[delta] = fuelAccumulator
    }

    return deltaToFuel.minOf { it.value }
}

fun getMinimumFuelPart2(crabList: List<Int>): Int {
    val stats = DescriptiveStatistics()
    crabList.forEach { stats.addValue(it.toDouble()) }
    val mean = round(stats.mean).toInt()

    val deltaToFuel = mutableMapOf<Int, Int>()

    for (delta : Int in mean - 5 .. mean + 5) {
        val fuelAccumulator = crabList.sumBy { getSumOfFirstNNums(abs(it - delta)) }
        deltaToFuel[delta] = fuelAccumulator
    }

    return deltaToFuel.minOf { it.value }
}

fun getSumOfFirstNNums(n: Int): Int {
    return n * (n + 1) / 2
}