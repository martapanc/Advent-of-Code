package aoc2019.day07

import aoc2019.day02.Day2.readInput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.function.Consumer

class Day7Test {
    @Test
    fun testReadInput() {
        System.out.println(readInput(INPUT1))
        System.out.println(readInput(INPUT2))
        System.out.println(readInput(INPUT3))
        System.out.println(readInput(INPUT4))
    }

    @Test
    fun testProcessInput() {
        Assertions.assertEquals(4, processInput(readInput(INPUT1), 4, 0, 0).outputValue)
        Assertions.assertEquals(43, processInput(readInput(INPUT1), 3, 4, 0).outputValue)
        Assertions.assertEquals(432, processInput(readInput(INPUT1), 2, 43, 0).outputValue)
        Assertions.assertEquals(4321, processInput(readInput(INPUT1), 1, 432, 0).outputValue)
        Assertions.assertEquals(43210, processInput(readInput(INPUT1), 0, 4321, 0).outputValue)
        Assertions.assertEquals(5, processInput(readInput(INPUT2), 0, 0, 0).outputValue)
        Assertions.assertEquals(54, processInput(readInput(INPUT2), 1, 5, 0).outputValue)
        Assertions.assertEquals(543, processInput(readInput(INPUT2), 2, 54, 0).outputValue)
        Assertions.assertEquals(5432, processInput(readInput(INPUT2), 3, 543, 0).outputValue)
        Assertions.assertEquals(54321, processInput(readInput(INPUT2), 4, 5432, 0).outputValue)
        Assertions.assertEquals(6, processInput(readInput(INPUT3), 1, 0, 0).outputValue)
        Assertions.assertEquals(65, processInput(readInput(INPUT3), 0, 6, 0).outputValue)
        Assertions.assertEquals(652, processInput(readInput(INPUT3), 4, 65, 0).outputValue)
        Assertions.assertEquals(6521, processInput(readInput(INPUT3), 3, 652, 0).outputValue)
        Assertions.assertEquals(65210, processInput(readInput(INPUT3), 2, 6521, 0).outputValue)
    }

    @Test
    fun testSetupAmplifiers() {
        Assertions.assertEquals(43210, setupAmplifiers(readInput(INPUT1), intArrayOf(4, 3, 2, 1, 0)))
        Assertions.assertEquals(54321, setupAmplifiers(readInput(INPUT2), intArrayOf(0, 1, 2, 3, 4)))
        Assertions.assertEquals(12345, setupAmplifiers(readInput(INPUT2), intArrayOf(4, 3, 2, 1, 0)))
        Assertions.assertEquals(65210, setupAmplifiers(readInput(INPUT3), intArrayOf(1, 0, 4, 3, 2)))
        Assertions.assertEquals(1265, setupAmplifiers(readInput(INPUT3), intArrayOf(2, 3, 4, 1, 0)))
    }

    @Test
    fun testGeneratePermutations() {
        Assertions.assertEquals(1, PermutationUtil.generatePermutations("0").size)
        Assertions.assertEquals(2, PermutationUtil.generatePermutations("01").size)
        Assertions.assertEquals(6, PermutationUtil.generatePermutations("012").size)
        Assertions.assertEquals(24, PermutationUtil.generatePermutations("1023").size)
        Assertions.assertEquals(120, PermutationUtil.generatePermutations("12345").size)
        Assertions.assertEquals(720, PermutationUtil.generatePermutations("123456").size)
        PermutationUtil.generatePermutations("0123").forEach(Consumer { x: String? -> println(x) })
    }

    @Test
    fun testFindBestResult() {
        Assertions.assertEquals(43210, findBestResult(readInput(INPUT1), PHASE_SETTINGS, false))
        Assertions.assertEquals(54321, findBestResult(readInput(INPUT2), PHASE_SETTINGS, false))
        Assertions.assertEquals(54321, findBestResult(readInput(INPUT2), PHASE_SETTINGS, false))
        Assertions.assertEquals(65210, findBestResult(readInput(INPUT3), PHASE_SETTINGS, false))
        Assertions.assertEquals(67023, findBestResult(readInput(INPUT4), PHASE_SETTINGS, false))
    }

    @Test
    fun testSetupLoopingAmplifiers() {
        Assertions.assertEquals(139629729, setupLoopingAmplifiers(readInput(INPUT5), intArrayOf(9, 8, 7, 6, 5)))
        Assertions.assertEquals(18216, setupLoopingAmplifiers(readInput(INPUT6), intArrayOf(9, 7, 8, 5, 6)))
        Assertions.assertEquals(3710683, setupLoopingAmplifiers(readInput(INPUT4), intArrayOf(9, 8, 7, 6, 5)))
    }

    @Test
    fun testFindBestResultWithLoop() {
        Assertions.assertEquals(139629729, findBestResult(readInput(INPUT5), PHASE_SETTINGS_2, true))
        Assertions.assertEquals(18216, findBestResult(readInput(INPUT6), PHASE_SETTINGS_2, true))
        Assertions.assertEquals(7818398, findBestResult(readInput(INPUT4), PHASE_SETTINGS_2, true))
    }

    companion object {
        private const val PHASE_SETTINGS = "01234"
        private const val PHASE_SETTINGS_2 = "98765"
        private const val INPUT1 = "src/main/java/aoc2019/day07/input/input1"
        private const val INPUT2 = "src/main/java/aoc2019/day07/input/input2"
        private const val INPUT3 = "src/main/java/aoc2019/day07/input/input3"
        private const val INPUT4 = "src/main/java/aoc2019/day07/input/input4"
        private const val INPUT5 = "src/main/java/aoc2019/day07/input/input5"
        private const val INPUT6 = "src/main/java/aoc2019/day07/input/input6"
    }
}