package aoc2019.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputToMap

internal class Day24KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2019/day24/input")
    private val input0 = readInputToMap("src/main/kotlin/aoc2019/day24/input0")

    @Test
    fun testReadInputToMap() {
        println(input0)
        println(input)
    }

    @Test
    fun testRunRounds() {
        assertEquals(2129920, playGameOfBugs(input0))
        assertEquals(14539258, playGameOfBugs(input))
    }
}