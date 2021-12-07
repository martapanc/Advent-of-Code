package aoc2021.day07

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import kotlin.math.abs
import kotlin.math.round


fun getMinimumFuel(crabList: List<Int>, isPart2: Boolean = false): Int {
    val stats = DescriptiveStatistics()
    crabList.forEach { stats.addValue(it.toDouble()) }
    val median = stats.getPercentile(50.0).toInt()
    val mean = round(stats.mean).toInt()

    // For Part 1, the minimum fuel is consumed moving close to the median position
    // For Part 2, it's the mean. Additionally, the fuel used is found via the Gauss formula
    val position = if (isPart2) mean else median
    fun totalFuel(posDifference: Int): Int = if (isPart2) getSumOfFirstNNums(posDifference) else posDifference

    val deltaToFuel = mutableMapOf<Int, Int>()
    for (delta : Int in position - 5 .. position + 5) {
        deltaToFuel[delta] = crabList.sumBy { totalFuel(abs(it - delta)) }
    }

    return deltaToFuel.minOf { it.value }
}

// In part 2, the total fuel to move from A to B is the Gauss formula applied to the steps, abs(B - A)
// E.g. to move from 1 to 5, that is 4 steps, is Gauss(5 - 1) = 10
fun getSumOfFirstNNums(n: Int): Int = n * (n + 1) / 2
