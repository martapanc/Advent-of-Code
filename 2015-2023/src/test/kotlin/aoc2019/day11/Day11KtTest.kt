package aoc2019.day11

import aoc2019.day09.readInput
import aoc2019.day11.Day11.processInput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11KtTest {

    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2019/day11/input1"
    }

    @Test
    fun testReadInput() {
        val list = readInput(INPUT1)
        Assertions.assertEquals(648, list.size)
        println(list)
    }

    @Test
    fun testProcessInput() {
        Assertions.assertEquals(1951, processInput(readInput(INPUT1), 0))
        Assertions.assertEquals(250, processInput(readInput(INPUT1), 1))
    }

    //   █  █ █  █   ██ ███   ██  █  █  ██  ███
    //   █  █ █ █     █ █  █ █  █ █  █ █  █ █  █
    //   ████ ██      █ ███  █  █ ████ █    █  █
    //   █  █ █ █     █ █  █ ████ █  █ █    ███
    //   █  █ █ █  █  █ █  █ █  █ █  █ █  █ █ █
    //   █  █ █  █  ██  ███  █  █ █  █  ██  █  █
}