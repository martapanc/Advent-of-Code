package aoc2019.day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class Day16KtTest {

    companion object {
        private val INPUT = readInputFile("src/main/kotlin/aoc2019/day16/input")
        private const val INPUT2 = "12345678"
        private const val INPUT3 = "48226158"
        private const val INPUT4 = "34040438"
        private const val INPUT5 = "03415518"
        private const val INPUT6 = "01029498"
        private const val INPUT7 = "80871224585914546619083218645595"
    }

    @Test
    fun testReadInput() {
        println(INPUT)
        println(INPUT2)
    }

    @Test
    fun testComputeNthDigit() {
        assertEquals(4, computeNthDigit(readString(INPUT2), 1))
        assertEquals(8, computeNthDigit(readString(INPUT2), 2))
        assertEquals(2, computeNthDigit(readString(INPUT2), 3))
        assertEquals(2, computeNthDigit(readString(INPUT2), 4))
        assertEquals(6, computeNthDigit(readString(INPUT2), 5))
        assertEquals(1, computeNthDigit(readString(INPUT2), 6))
        assertEquals(5, computeNthDigit(readString(INPUT2), 7))
        assertEquals(8, computeNthDigit(readString(INPUT2), 8))
    }

    @Test
    fun testComputePhase() {
        assertEquals(INPUT3, computePhase(readString(INPUT2), 1, true))
        assertEquals(INPUT4, computePhase(readString(INPUT3), 1, true))
        assertEquals(INPUT4, computePhase(readString(INPUT2), 2, true))
        assertEquals(INPUT5, computePhase(readString(INPUT2), 3, true))
        assertEquals(INPUT6, computePhase(readString(INPUT2), 4, true))
        assertEquals("24176176", computePhase(readString(INPUT7), 100, true))
        assertEquals("77038830", computePhase(INPUT, 100, true))
        //        assertEquals("77038830", computePhase(tenThousandTimesList(readInput(INPUT1)), 1, true));
        computePhase(readString(INPUT7), 100, false)
        computePhase(readString(INPUT2), 100, false)
    }

    @Test
    fun testCreateTenThousandTimesList() {
        assertEquals(80000, tenThousandTimesList(readString("12345678")).size)
        assertEquals(
            320000,
            tenThousandTimesList(readString("03036732577212944063491565474664")).size
        )
        assertEquals(6500000, tenThousandTimesList(INPUT).size)
    }
}