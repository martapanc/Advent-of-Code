package aoc2020.day14

import java.io.File
import kotlin.math.pow

fun readInputToList(path: String): Map<String, List<String>> {
    val map = mutableMapOf<String, List<String>>()
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    for (line in lineList) {
        if (line.startsWith("mask")) {
            val writeList = mutableListOf<String>()
            var index = lineList.indexOf(line) + 1
            while (index < lineList.size && !lineList[index].startsWith("mask")) {
                writeList.add(lineList[index])
                index++
            }
            val mask = line.replace("mask = ", "")
            map[mask] = writeList
        }
    }
    return map
}

fun runInitProgramme(map: Map<String, List<String>>): Long {
    val memoryMap = mutableMapOf<Int, Long>()
    for (mask in map.entries) {
        for (instruction in mask.value) {
            val split = instruction.split(" = ")
            var binaryInstruction = convertAndAddLeadingZeros(split[1], mask.key.length)
            binaryInstruction = replaceBitsUsingMask('X', mask, binaryInstruction)
            memoryMap[readMemoryAddressNumber(split)] = binaryToDecimal(binaryInstruction!!)
        }
    }
    return memoryMap.values.sum()
}

fun runInitProgramme2(map: Map<String, List<String>>): Long {
    val memoryMap = mutableMapOf<Long, Long>()
    for (mask in map.entries) {
        for (instruction in mask.value) {
            val split = instruction.split(" = ")
            var binaryAddress = convertAndAddLeadingZeros(readMemoryAddressNumber(split).toString(), mask.key.length)
            binaryAddress = replaceBitsUsingMask('0', mask, binaryAddress)

            val xCount = countXInBinaryString(binaryAddress!!)
            for (replacements in generateListOfBinaries(xCount)) {
                var count = 0
                var tempBinaryAddress = binaryAddress
                while (count < xCount) {
                    tempBinaryAddress = tempBinaryAddress?.replaceFirst('X', replacements[count])
                    count++
                }
                memoryMap[binaryToDecimal(tempBinaryAddress!!)] = split[1].toLong()
            }
        }
    }
    return memoryMap.values.sum()
}

private fun replaceBitsUsingMask(neutralChar: Char, mask: Map.Entry<String, List<String>>, binaryString: String?): String? {
    var tempBinary = binaryString
    for ((index, char) in mask.key.withIndex()) {
        if (char != neutralChar) {
            tempBinary = tempBinary?.replaceRange(index..index, char.toString())
        }
    }
    return tempBinary
}

private fun readMemoryAddressNumber(split: List<String>) =
    split[0].replace("mem[", "").replace("]", "").toInt()

// Generate list of combinations of N bits (e.g. 2 => ["00", "01", "10", "11"])
fun generateListOfBinaries(num: Int): List<String> {
    return (0 until 2.0.pow(num.toDouble()).toInt()).map { convertAndAddLeadingZeros(it.toString(), num)!! }
}

fun countXInBinaryString(string: String): Int {
    var xCount = 0
    for (char in string)
        if (char == 'X') xCount++
    return xCount
}

private fun convertAndAddLeadingZeros(string: String, length: Int): String? {
    var binaryInstruction = Integer.toBinaryString(string.toInt())
    for (i in 0 until (length - binaryInstruction.length)) {
        binaryInstruction = "0$binaryInstruction"
    }
    return binaryInstruction
}

// Generate decimal representation of large binary numbers, for which a Long is not enough
fun binaryToDecimal(input: String): Long {
    var result = 0L
    val reversed = input.reversed()
    for ((index, bit) in reversed.withIndex()) {
        result += 2.0.pow(index.toDouble()).toLong() * bit.toString().toInt()
    }
    return result
}