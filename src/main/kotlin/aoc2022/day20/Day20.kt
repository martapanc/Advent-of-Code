package aoc2022.day20

import util.readInputLineByLine

fun readInputToNumbers(path: String): List<Number> = readInputLineByLine(path).map { Number(it.toInt()) }

fun part1(numbers: List<Number>): Int {
    val array = numbers.toTypedArray()
    array.mix(numbers)
    val indexOfZero = array.indexOfFirst { it.value == 0 }
    if (indexOfZero >= 0) {
        return array[(indexOfZero + 1000) % array.size].value +
                array[(indexOfZero + 2000) % array.size].value +
                array[(indexOfZero + 3000) % array.size].value
    }
    return -1
}

fun part2(numbers: List<Number>, MULTIPLIER: Long = 811589153L): Long {
    val array = numbers.toTypedArray()
    repeat(10) {
        array.mix(numbers, MULTIPLIER)
    }
    val indexOfZero = array.indexOfFirst { it.value == 0 }
    if (indexOfZero >= 0) {
        return MULTIPLIER * (
            array[(indexOfZero + 1000) % array.size].value +
            array[(indexOfZero + 2000) % array.size].value +
            array[(indexOfZero + 3000) % array.size].value
        )
    }
    return -1
}

private fun Array<Number>.mix(numbers: List<Number>, multiplier: Long = 1L) {
    numbers.forEach { num ->
        val index = indexOf(num)
        if (index >= 0) {
            val newIndex = (index + num.value * multiplier).mod(lastIndex)
            if (newIndex >= 0) {
                copyInto(this, minOf(index, newIndex + 1), minOf(index + 1, newIndex), maxOf(index, newIndex + 1))
                this[newIndex] = num
            }
        }
    }
}

class Number(val value: Int)

