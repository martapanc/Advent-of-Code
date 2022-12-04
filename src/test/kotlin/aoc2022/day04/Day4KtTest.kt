package aoc2022.day04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day4KtTest {

    private val testInput0 = readInputToRanges("src/main/kotlin/aoc2022/day04/assets/input0")
    private val testInput = readInputToRanges("src/main/kotlin/aoc2022/day04/assets/input")

    @Test
    fun testPart1() {
        assertEquals(2, part1(testInput0))
        assertEquals(507, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(4, part2(testInput0))
        assertEquals(897, part2(testInput))
    }

    @Test
    fun testIsRangeFullyContainedInOther() {
        assertTrue(isRangeFullyContainedInOther(Pair(Range(2, 8), Range(3, 7))))
        assertTrue(isRangeFullyContainedInOther(Pair(Range(6, 6), Range(4, 6))))
        assertTrue(isRangeFullyContainedInOther(Pair(Range(6, 6), Range(4, 6))))
        assertTrue(isRangeFullyContainedInOther(Pair(Range(6, 8), Range(8, 8))))

        assertFalse(isRangeFullyContainedInOther(Pair(Range(2, 4), Range(6, 8))))
        assertFalse(isRangeFullyContainedInOther(Pair(Range(2, 6), Range(4, 8))))
    }

    @Test
    fun testDoRangesOverlap() {
        assertTrue(doRangesOverlap(Pair(Range(5, 7), Range(7, 9))))
        assertTrue(doRangesOverlap(Pair(Range(2, 8), Range(3, 7))))
        assertTrue(doRangesOverlap(Pair(Range(6, 6), Range(4, 6))))
        assertTrue(doRangesOverlap(Pair(Range(2, 6), Range(4, 8))))

        assertFalse(doRangesOverlap(Pair(Range(2, 4), Range(6, 8))))
        assertFalse(doRangesOverlap(Pair(Range(5, 6), Range(3, 4))))
    }
}

