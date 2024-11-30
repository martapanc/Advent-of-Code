package aoc2019.day09

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9KtTest {
    @Test
    fun testReadInput() {
        val list = readInput(INPUT1)
        Assertions.assertEquals(16, list.size)
        println(list)
    }

    @Test
    fun testProcessInput() {
//        assertEquals(1, processInput(readInput(INPUT1), 1));
        Assertions.assertEquals(1219070632396864L, processInput(readInput(INPUT2), 1))
        Assertions.assertEquals(1125899906842624L, processInput(readInput(INPUT3), 1))
        Assertions.assertEquals(2377080455L, processInput(readInput(INPUT4), 1))
        Assertions.assertEquals(74917, processInput(readInput(INPUT4), 2))
    }

    // Check that the code still work for previous versions
    @Test
    fun testProcessInput_Regression() {
        Assertions.assertEquals(7988899, processInput(readInput(INPUT1_five), 1))
        Assertions.assertEquals(13758663, processInput(readInput(INPUT1_five), 5))
        Assertions.assertEquals(0, processInput(readInput(INPUT2_five), 7))
        Assertions.assertEquals(1, processInput(readInput(INPUT2_five), 8))
        Assertions.assertEquals(0, processInput(readInput(INPUT2_2_five), 8))
        Assertions.assertEquals(1, processInput(readInput(INPUT2_2_five), 7))
        Assertions.assertEquals(0, processInput(readInput(INPUT3_five), 7))
        Assertions.assertEquals(1, processInput(readInput(INPUT3_five), 8))
        Assertions.assertEquals(0, processInput(readInput(INPUT3_2_five), 8))
        Assertions.assertEquals(1, processInput(readInput(INPUT3_2_five), 7))
        Assertions.assertEquals(0, processInput(readInput(INPUT4_five), 0))
        Assertions.assertEquals(1, processInput(readInput(INPUT4_five), 2))
        Assertions.assertEquals(0, processInput(readInput(INPUT5_five), 0))
        Assertions.assertEquals(1, processInput(readInput(INPUT5_five), 2))
        Assertions.assertEquals(999, processInput(readInput(INPUT6_five), 7))
        Assertions.assertEquals(1000, processInput(readInput(INPUT6_five), 8))
        Assertions.assertEquals(1001, processInput(readInput(INPUT6_five), 9))
    }

    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2019/day09/input/input1"
        private const val INPUT2 = "src/main/kotlin/aoc2019/day09/input/input2"
        private const val INPUT3 = "src/main/kotlin/aoc2019/day09/input/input3"
        private const val INPUT4 = "src/main/kotlin/aoc2019/day09/input/input4"
        private const val INPUT1_five = "src/main/kotlin/aoc2019/day05/input/input1"
        private const val INPUT2_five = "src/main/kotlin/aoc2019/day05/input/input2"
        private const val INPUT2_2_five = "src/main/kotlin/aoc2019/day05/input/input2_2"
        private const val INPUT3_five = "src/main/kotlin/aoc2019/day05/input/input3"
        private const val INPUT3_2_five = "src/main/kotlin/aoc2019/day05/input/input3_2"
        private const val INPUT4_five = "src/main/kotlin/aoc2019/day05/input/input4"
        private const val INPUT5_five = "src/main/kotlin/aoc2019/day05/input/input5"
        private const val INPUT6_five = "src/main/kotlin/aoc2019/day05/input/input6"
    }
}