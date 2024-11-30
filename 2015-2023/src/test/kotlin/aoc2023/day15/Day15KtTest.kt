package aoc2023.day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day15KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day15/assets/input0")
    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day15/assets/input")

    @Test
    fun testPart1() {
        assertEquals(1320, part1(testInputLines))
        assertEquals(511215, part1(inputLines))
    }

    @Test
    fun testPart2() {
        assertEquals(145, part2(testInputLines))
        assertEquals(236057, part2(inputLines))
    }

    @Test
    fun testHash() {
        assertEquals(200, hash("H"))
        assertEquals(153, hash("HA"))
        assertEquals(172, hash("HAS"))
        assertEquals(52, hash("HASH"))
        assertEquals(30, hash("rn=1"))
        assertEquals(253, hash("cm-"))
        assertEquals(9, hash("ot=9"))
        assertEquals(231, hash("ot=7"))
    }
}
