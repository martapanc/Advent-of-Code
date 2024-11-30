package aoc2017.day08

import util.readInputLineByLine

fun readInputAndFindMaxRegisterValue(path: String, isPartTwo: Boolean = false): Int {
    val instructionRegex = Regex("([a-z]+) (dec|inc) (-?\\d+) if ([a-z]+) (>=|>|<|<=|==|!=) (-?\\d+)")
    val registers = mutableMapOf<String, Int>()
    var maxRegisterValue = 0
    for (line in readInputLineByLine(path)) {
        val match = instructionRegex.matchEntire(line)!!
        val regId = match.groupValues[1]
        val increase = match.groupValues[2] == "inc"
        val changeValue = match.groupValues[3].toInt()
        val targetRegId = match.groupValues[4]
        val operator = match.groupValues[5]
        val comparisonValue = match.groupValues[6].toInt()

        val targetRegValue = if (registers[targetRegId] != null) registers[targetRegId]!! else 0
        val editRegister: Boolean = when (operator) {
            ">=" -> targetRegValue >= comparisonValue
            ">" -> targetRegValue > comparisonValue
            "<" -> targetRegValue < comparisonValue
            "<=" -> targetRegValue <= comparisonValue
            "==" -> targetRegValue == comparisonValue
            "!=" -> targetRegValue != comparisonValue
            else -> false
        }

        if (editRegister)
            if (increase) {
                if (registers[regId] != null) {
                    registers[regId] = registers[regId]!!.plus(changeValue)
                } else {
                    registers[regId] = changeValue
                }
                if (registers[regId]!! > maxRegisterValue) {
                    maxRegisterValue = registers[regId]!!
                }
            } else {
                if (registers[regId] != null) {
                    registers[regId] = registers[regId]!!.minus(changeValue)
                } else {
                    registers[regId] = -changeValue
                }
                if (registers[regId]!! > maxRegisterValue) {
                    maxRegisterValue = registers[regId]!!
                }
            }
    }
    return if (isPartTwo) maxRegisterValue else registers.maxByOrNull { it.value }!!.value
}
