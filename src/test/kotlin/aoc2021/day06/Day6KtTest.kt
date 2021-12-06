package aoc2021.day06

import aoc2019.day02.readInput
import aoc2020.day05.readInputToList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    val input0 = readInput("src/main/kotlin/aoc2021/day06/assets/input0")
    val input = readInput("src/main/kotlin/aoc2021/day06/assets/input")

    @Test
    fun getLanternfishGrowth() {
        assertEquals(5, getLanternfishGrowth(input0, 0))
        assertEquals(5, getLanternfishGrowth(input0, 1))
        assertEquals(6, getLanternfishGrowth(input0, 2))
        assertEquals(7, getLanternfishGrowth(input0, 3))
        assertEquals(9, getLanternfishGrowth(input0, 4))
        assertEquals(19, getLanternfishGrowth(input0, 13))
        assertEquals(26, getLanternfishGrowth(input0, 18))
        assertEquals(5934, getLanternfishGrowth(input0, 80))
        assertEquals(25724, getLanternfishGrowth(input0, 97))
        assertEquals(28543, getLanternfishGrowth(input0, 98))
        assertEquals(30822, getLanternfishGrowth(input0, 99))
        assertEquals(33893, getLanternfishGrowth(input0, 100))
//        assertEquals(26984457539, getLanternfishGrowth(input0, 256))

        assertEquals(300, getLanternfishGrowth(input, 0))
        assertEquals(345793, getLanternfishGrowth(input, 80))
    }
}