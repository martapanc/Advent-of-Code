package aoc2021.day01

fun findIncreasingDepthValues(inputList: List<Int>): Int {
    var increasingDepthCount = 0
    var currentDepthValue = inputList[0]

    inputList.subList(1, inputList.size).forEach { depthValue ->
        if (depthValue > currentDepthValue) {
            increasingDepthCount++
        }
        currentDepthValue = depthValue;
    }

    return increasingDepthCount
}

fun findIncreasingThreeMeasurementDepthValues(inputList: ArrayList<Int>): Int {
    var increasingDepthCount = 0
    var currentThreeMeasurementDepthValue = inputList.take(3).sum()

    while (inputList.isNotEmpty()) {
        inputList.removeFirst()

        val threeMeasurementDepthValue = inputList.take(3).sum()
        if (threeMeasurementDepthValue > currentThreeMeasurementDepthValue) {
            increasingDepthCount++
        }
        currentThreeMeasurementDepthValue = threeMeasurementDepthValue
    }

    return increasingDepthCount;
}