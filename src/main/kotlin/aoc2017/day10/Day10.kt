package aoc2017.day10

import util.readInputLineByLine

fun readInputToList(path: String): List<Int> {
    val line = readInputLineByLine(path)[0]
    return line.split(",").map { it.toInt() }
}

fun processList(inputLength: List<Int>, sizeOfList: Int): Long {
    var inputList = (0 until sizeOfList).toMutableList()
    inputList = runRound(inputLength, inputList).outputList
    return inputList[0].toLong() * inputList[1]
}

private fun runRound(lengths: List<Int>, numbers: List<Int>, initIndex: Int = 0, initSkipVal: Int = 0): Day10 {
    var index = initIndex
    var skipValue = initSkipVal
    var inputList = numbers.toMutableList()
    for ((_, length) in lengths.withIndex()) {
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
    return Day10(inputList, index, skipValue)
}

fun convertListToASCII(input: String, isPath: Boolean = false): List<Int> {
    val asciiList = mutableListOf<Int>()
    val inputString = if (isPath) readInputLineByLine(input)[0] else input
    for (char in inputString) {
        asciiList.add(char.toInt())
    }
    return asciiList
}

fun processListPart2(inputLength: List<Int>, sizeOfList: Int = 256): String {
    val inputList = (0 until sizeOfList).toMutableList()
    val editedInputLength = inputLength + listOf(17, 31, 73, 47, 23)
    var output = runRound(editedInputLength, inputList)
    for (i in 2..64) {
        output = runRound(editedInputLength, inputList, output.index, output.skipValue)
    }

    val denseHashesList = output.outputList.chunked(16).map { computeXorOfList(it) }
    return computeHexString(denseHashesList)
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
    for (i in 0 until 16) {
        xorResult = xorResult.xor(chunk[i])
    }
    return xorResult
}

class Day10(var outputList: MutableList<Int>, var index: Int = 0, var skipValue: Int = 0)
