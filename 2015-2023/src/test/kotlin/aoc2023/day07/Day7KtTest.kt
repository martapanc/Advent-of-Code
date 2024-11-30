package aoc2023.day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day7KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day07/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day07/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(6440, part1(testInput))
        assertEquals(249390788, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(5905, part2(testInput))
        assertEquals(248750248, part2(input))
    }

    @Test
    fun testDetermineType() {
        assertEquals(HandType.HIGH_CARD, determineType("23456"))
        assertEquals(HandType.HIGH_CARD, determineType("A2JT3"))
        assertEquals(HandType.ONE_PAIR, determineType("2J3AJ"))
        assertEquals(HandType.ONE_PAIR, determineType("8922K"))
        assertEquals(HandType.TWO_PAIR, determineType("232J3"))
        assertEquals(HandType.TWO_PAIR, determineType("AKKA7"))
        assertEquals(HandType.THREE_OF_A_KIND, determineType("3Q338"))
        assertEquals(HandType.FULL_HOUSE, determineType("3Q3Q3"))
        assertEquals(HandType.FOUR_OF_A_KIND, determineType("A4AAA"))
        assertEquals(HandType.FIVE_OF_A_KIND, determineType("44444"))
    }

    @Test
    fun testCompareHands() {
        assertEquals(-1, compareHands("44444", "A4AAA"))
        assertEquals(1, compareHands("A4AAA", "44444"))
        assertEquals(1, compareHands("A4A3A", "A4A4A"))
        assertEquals(-1, compareHands("A4A3A", "A2A4A"))
        assertEquals(1, compareHands("AAA4A", "AAAA4"))
        assertEquals(0, compareHands("23456", "23456"))
    }
}
