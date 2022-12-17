package aoc2022.day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17KtTest {

    private val testInput0 = readInputToJetStreams("src/main/kotlin/aoc2022/day17/assets/input0")
    private val testInput = readInputToJetStreams("src/main/kotlin/aoc2022/day17/assets/input")

    @Test
    fun testPart1() {
        assertEquals(4, part1(testInput0, 2))
        assertEquals(17, part1(testInput0, 10))
        assertEquals(3068, part1(testInput0))
        assertEquals(3069, part1(testInput))
    }

    @Test
    fun testPart2() {
//        assertEquals(157, part2(testInput0, 100))
//        assertEquals(1520, part2(testInput0, 1000))
//
//        assertEquals(3037, part2(testInput, 2000))
//        assertEquals(6074, part2(testInput, 4000))

//        assertEquals(322, part2(testInput, 220))
//        assertEquals(2919, part2(testInput, 1925))
//        assertEquals(5516, part2(testInput, 3630))
//        assertEquals(8113, part2(testInput, 5335))
//        assertEquals(10710, part2(testInput, 7040))

//        assertEquals(966, part2(testInput, 652)) // 220 + 432
//        assertEquals(2393, part2(testInput, 1585))
//        assertEquals(4811, part2(testInput, 3180))
//
//        assertEquals(1873, part2(testInput, 1255))
//        assertEquals(4470, part2(testInput, 2960))
//        assertEquals(7067, part2(testInput, 4665))

        assertEquals(2214, part2(testInput, 1475))
        assertEquals(4811, part2(testInput, 3180))
//        assertEquals(15199, part2(testInput, 10000)) // OK

        assertEquals(2393, part2(testInput, 1585))

        //1523167155404
    }
}
