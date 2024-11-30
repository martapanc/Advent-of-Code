package aoc2019.day13

import aoc2019.day09.readInput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.ArrayList

class Day13KtTest {

    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2019/day13/input1"
        private const val INPUT2 = "src/main/kotlin/aoc2019/day13/input2"
    }

    @Test
    fun testReadInput() {
        val list: ArrayList<Long> = readInput(INPUT1)
        Assertions.assertEquals(2710, list.size)
    }

    @Test
    fun testProcessInput() {
        println(processInput(readInput(INPUT1)))
    }

    @Test
    fun testCountTilesOfType() {
        println(processInput(readInput(INPUT2)))
    }

    @Test
    fun testPrintTileMap() {
        printTileMap(processInput(readInput(INPUT1)))
        printTileMap(processInput(readInput(INPUT2)))
    }
}