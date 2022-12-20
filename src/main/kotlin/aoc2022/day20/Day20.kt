package aoc2022.day20

import util.readInputLineByLine

fun readInputToNumbers(path: String): List<Int> = readInputLineByLine(path).map { it.toInt() }

class Day20(lines: List<Int>) {
    private val boxes = lines.map { Box(it) }

    fun part1(): Int {
        val array = boxes.toTypedArray()
        array.mix()
        val base = array.indexOfFirst { it.value == 0 }
        check(base >= 0)
        return array[(base + 1000) % array.size].value +
                array[(base + 2000) % array.size].value +
                array[(base + 3000) % array.size].value
    }

    fun part2(): Long {
        val array = boxes.toTypedArray()
        repeat(10) { array.mix(MULTIPLIER) }
        val base = array.indexOfFirst { it.value == 0 }
        check(base >= 0)
        return MULTIPLIER * (
                array[(base + 1000) % array.size].value +
                        array[(base + 2000) % array.size].value +
                        array[(base + 3000) % array.size].value
                )
    }

    private class Box(val value: Int)

    private fun Array<Box>.mix(multiplier: Long = 1L) {
        for (node in boxes) {
            val i = indexOf(node)
            check(i >= 0)
            val j = (i + node.value * multiplier).mod(lastIndex)
            check(j >= 0)
            copyInto(this, minOf(i, j + 1), minOf(i + 1, j), maxOf(i, j + 1))
            this[j] = node
        }
    }

    companion object {
        private const val MULTIPLIER = 811589153L
    }
}
