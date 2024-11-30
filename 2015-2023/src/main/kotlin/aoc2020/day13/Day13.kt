package aoc2020.day13

import util.lcm


fun readInputToList(input: String): List<Int> {
    val list = mutableListOf<Int>()
    for (v in input.split(",")) {
        if (v == "x") {
            list.add(-1)
        } else {
            list.add(v.toInt())
        }
    }
    return list
}

fun readInputToListExcludeX(input: String): List<Int> {
    return input.split(",")
        .filter { it != "x" }
        .map { it.toInt() }
}

fun findFirstAvailableBus(timestamp: Long, inputList: List<Int>): Int {
    val minWaitTime = mutableMapOf<Int, Int>()
    for (arrivalInterval in inputList) {
        val waitTime = ((timestamp / arrivalInterval) + 1) * arrivalInterval - timestamp
        minWaitTime[waitTime.toInt()] = arrivalInterval
    }

    val min = minWaitTime.keys.minOrNull()
    if (min != null) {
        return min * minWaitTime[min]!!
    }
    return -1
}

// This loops through the available buses and their arrival times finding multiples of the interval, but uses the LCM to update the interval,
// based on the multiplier found at every loop (which saves a lot of iterations), until a common multiple is found
fun findEarliestTimestamp(inputList: List<Int>): Long {
    val shuttleList = mutableListOf<Shuttle>()
    inputList.filter { it != -1 }
        .mapTo(shuttleList) { Shuttle(inputList.indexOf(it).toLong(), it.toLong()) }

    var index = shuttleList[0].index
    var interval = shuttleList[0].departureInterval
    for (shuttle in shuttleList.subList(1, shuttleList.size)) {
        for (i in index until Long.MAX_VALUE step interval) {
            if ((i + shuttle.index) % shuttle.departureInterval == 0L) {
                index = i
                interval = lcm(interval, shuttle.departureInterval)
                break
            }
        }
    }
    return index
}

class Shuttle(var index: Long, var departureInterval: Long)
