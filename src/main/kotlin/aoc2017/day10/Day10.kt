package aoc2017.day10

import util.readInputLineByLine

fun readInputToList(path: String): List<Int> {
    val line = readInputLineByLine(path)[0]
    return line.split(",").map { it.toInt() }
}

fun processList(inputLength: List<Int>, sizeOfList: Int): Long {
    var inputList = (0 until sizeOfList).toMutableList()
    var index = 0
    for ((skipValue, length) in inputLength.withIndex()) {
        if (index + length < inputList.size) {
            val selection = inputList.subList(index, index + length).reversed().toMutableList()
            for (value in selection) {
                inputList.remove(value)
            }
            inputList.addAll(index, selection)
        } else {
            val splitSelection = inputList.subList(index, inputList.size).toMutableList()
            val temp = inputList.subList(0, length - splitSelection.size)
            splitSelection.addAll(temp)
            splitSelection.reverse()

            val afterCurrentIndex = inputList.size - index
            val beforeCurrentIndex = length - afterCurrentIndex
            inputList = inputList.subList(0, index)
            for (i in 0 until afterCurrentIndex) {
                inputList.add(splitSelection.removeAt(0))
            }
            inputList = inputList.subList(beforeCurrentIndex, inputList.size)
            for (i in 0 until beforeCurrentIndex) {
                inputList.add(i, splitSelection[i])
            }
            inputList = inputList.toSet().toMutableList()
        }
        index = (index + (length + skipValue)) % inputList.size
    }
    return inputList[0].toLong() * inputList[1]
}