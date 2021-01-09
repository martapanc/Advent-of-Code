package aoc2019.day05

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5KtTest {
    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2019/day05/input/input1"
        private const val INPUT2 = "src/main/kotlin/aoc2019/day05/input/input2"
        private const val INPUT2_2 = "src/main/kotlin/aoc2019/day05/input/input2_2"
        private const val INPUT3 = "src/main/kotlin/aoc2019/day05/input/input3"
        private const val INPUT3_2 = "src/main/kotlin/aoc2019/day05/input/input3_2"
        private const val INPUT4 = "src/main/kotlin/aoc2019/day05/input/input4"
        private const val INPUT5 = "src/main/kotlin/aoc2019/day05/input/input5"
        private const val INPUT6 = "src/main/kotlin/aoc2019/day05/input/input6"
    }

    @Test
    fun testReadInput() {
        // Reusing the read method written for , as it's identical
        val list: List<Int> = readInput(INPUT1)
        Assertions.assertEquals(678, list.size)
        println(list)
    }

    @Test
    fun testProcessInput() {
        Assertions.assertEquals(7988899, processInput(readInput(INPUT1), 1))
        Assertions.assertEquals(13758663, processInput(readInput(INPUT1), 5))
        Assertions.assertEquals(0, processInput(readInput(INPUT2), 7))
        Assertions.assertEquals(1, processInput(readInput(INPUT2), 8))
        Assertions.assertEquals(0, processInput(readInput(INPUT2_2), 8))
        Assertions.assertEquals(1, processInput(readInput(INPUT2_2), 7))
        Assertions.assertEquals(0, processInput(readInput(INPUT3), 7))
        Assertions.assertEquals(1, processInput(readInput(INPUT3), 8))
        Assertions.assertEquals(0, processInput(readInput(INPUT3_2), 8))
        Assertions.assertEquals(1, processInput(readInput(INPUT3_2), 7))
        Assertions.assertEquals(0, processInput(readInput(INPUT4), 0))
        Assertions.assertEquals(1, processInput(readInput(INPUT4), 2))
        Assertions.assertEquals(0, processInput(readInput(INPUT5), 0))
        Assertions.assertEquals(1, processInput(readInput(INPUT5), 2))
        Assertions.assertEquals(999, processInput(readInput(INPUT6), 7))
        Assertions.assertEquals(1000, processInput(readInput(INPUT6), 8))
        Assertions.assertEquals(1001, processInput(readInput(INPUT6), 9))
    }
}