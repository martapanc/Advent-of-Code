package aoc2019.day02

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day2KtTest {
    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2019/day02/input1"
        private const val INPUT2 = "src/main/kotlin/aoc2019/day02/input2"
        private const val INPUT3 = "src/main/kotlin/aoc2019/day02/input3"
        private const val INPUT4 = "src/main/kotlin/aoc2019/day02/input4"
        private const val INPUT5 = "src/main/kotlin/aoc2019/day02/input5"
        private const val INPUT1_2 = "src/main/kotlin/aoc2019/day02/input1_2"
    }

    @Test
    fun testReadInput() {
        println(readInput(INPUT1))
    }

    @Test
    fun processInput() {
        Assertions.assertEquals(3500, processInput(readInput(INPUT2)))
        Assertions.assertEquals(2, processInput(readInput(INPUT3)))
        Assertions.assertEquals(2, processInput(readInput(INPUT4)))
        Assertions.assertEquals(30, processInput(readInput(INPUT5)))
        Assertions.assertEquals(5866714, processInput(readInput(INPUT1)))
    }

    @Test
    fun findPair() {
        Assertions.assertEquals(5208, findPair(readInput(INPUT1_2)))
    }
}