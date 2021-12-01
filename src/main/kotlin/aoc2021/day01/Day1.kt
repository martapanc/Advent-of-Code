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