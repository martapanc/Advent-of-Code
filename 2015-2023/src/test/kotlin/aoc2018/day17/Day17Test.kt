package aoc2018.day17

import org.junit.jupiter.api.Test

internal class Day17Test {

    private val input = readInputToMap("src/main/kotlin/aoc2018/day17/input")
    private val testInput = readInputToMap("src/main/kotlin/aoc2018/day17/testInput")

    @Test
    fun testRunPart1() {
        printTileMap(testInput)
        printTileMap(input)
//        assertEquals(34541, Day17(input).runPart1())
    }

    @Test
    fun testRunPart2() {
//        assertEquals(28000, Day17(input).runPart2())
    }
}