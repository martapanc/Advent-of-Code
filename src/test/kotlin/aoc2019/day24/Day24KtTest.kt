package aoc2019.day24

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day24KtTest {

    private val input = aoc2020.day11.readInputToMap("src/main/kotlin/aoc2019/day24/input")
    private val input0 = aoc2020.day11.readInputToMap("src/main/kotlin/aoc2019/day24/input0")

    @Test
    fun testReadInputToMap() {
        println(input0)
        println(input)
    }

    @Test
    fun testRunRounds() {
        assertEquals(2129920, runRounds(input0))
        assertEquals(14539258, runRounds(input))
    }
}