package aoc2019.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputToMap

internal class Day24KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2019/day24/input")
    private val testInput = readInputToMap("src/main/kotlin/aoc2019/day24/input0")

    @Test
    fun testReadInputToMap() {
        println(testInput)
        println(input)
    }

    @Test
    fun testRunRounds() {
        assertEquals(2129920, playGameOfBugs(testInput))
        assertEquals(14539258, playGameOfBugs(input))
    }

    @Test
    fun testPlayMultilayerGameOfBugs() {
        assertEquals(27, playMultilayerGameOfBugs(testInput))
        assertEquals(99, playMultilayerGameOfBugs(testInput, 10))
        assertEquals(1977, playMultilayerGameOfBugs(input, 200))
    }
}