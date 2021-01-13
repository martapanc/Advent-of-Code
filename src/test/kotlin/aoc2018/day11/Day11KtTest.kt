package aoc2018.day11

import aoc2019.day09.readInput
import aoc2019.day11.Day11.processInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class Day11KtTest {
    @Test
    fun testReadInput() {
        val list: ArrayList<Long> = readInput(INPUT1)
        assertEquals(648, list.size)
    }

    @Test
    fun testProcessInput() {
        assertEquals(1951, processInput(readInput(INPUT1), 0))
        assertEquals(250, processInput(readInput(INPUT1), 1))
    } //

    //   █  █ █  █   ██ ███   ██  █  █  ██  ███
    //   █  █ █ █     █ █  █ █  █ █  █ █  █ █  █
    //   ████ ██      █ ███  █  █ ████ █    █  █
    //   █  █ █ █     █ █  █ ████ █  █ █    ███
    //   █  █ █ █  █  █ █  █ █  █ █  █ █  █ █ █
    //   █  █ █  █  ██  ███  █  █ █  █  ██  █  █
    //
    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2018/day11/input1"
    }
}