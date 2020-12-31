package aoc2017.day10

import util.readInputLineByLine

fun readInputToList(path: String): List<Int> {
    val line = readInputLineByLine(path)[0]
    return line.split(",").map { it.toInt() }
}

fun processListPart1(inputLength: List<Int>, listSize: Int): Long {
    val inputList = knotHash(inputLength, 1, listSize)
    return inputList[0].toLong() * inputList[1]
}

fun processListPart2(inputLength: List<Int>, listSize: Int = 256): String {
    val editedLengths = inputLength + listOf(17, 31, 73, 47, 23)
    val output = knotHash(editedLengths, 64, listSize).toList()
    val denseHashesList = output.chunked(16).map { computeXorOfList(it) }
    return computeHexString(denseHashesList)
}

fun knotHash(lengths: List<Int>, rounds: Int = 64, listSize: Int = 256): List<Int> {
    var index = 0
    var skipValue = 0
    var inputList = (0 until listSize).toMutableList()
    repeat(rounds) {
        for (length in lengths) {
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
            skipValue++
        }
    }
    return inputList
}

fun convertListToASCII(input: String, isPath: Boolean = false): List<Int> {
    val asciiList = mutableListOf<Int>()
    val inputString = if (isPath) readInputLineByLine(input)[0] else input
    for (char in inputString) asciiList.add(char.toInt())
    return asciiList
}

fun computeHexString(denseHashesList: List<Int>): String {
    var hexString = ""
    for (denseHash in denseHashesList) {
        val toHexString = Integer.toHexString(denseHash)
        hexString += if (toHexString.length == 1) "0$toHexString" else toHexString
    }
    return hexString
}

fun computeXorOfList(chunk: List<Int>): Int {
    var xorResult = 0
    for (element in chunk) xorResult = xorResult.xor(element)
    return xorResult
}
