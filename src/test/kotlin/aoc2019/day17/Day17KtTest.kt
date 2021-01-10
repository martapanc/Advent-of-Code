package aoc2019.day17

import aoc2019.day09.readInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.awt.Point
import java.util.*

internal class Day17KtTest {
    companion object {
        private const val INPUT1 = "src/main/kotlin/aoc2019/day17/input1"
        private val READ_INPUT: ArrayList<Long> = readInput(INPUT1)
        private val INTERSECTIONS = getIntersections(processInput(READ_INPUT))
    }

    @Test
    fun testReadInput() {
        println(READ_INPUT)
    }

    @Test
    fun testProcessInput() {
        processInput(READ_INPUT)
    }

    @Test
    fun testPrintMap() {
        printMap(processInput(READ_INPUT))
    }

    @Test
    fun testGetIntersections() {
        println(INTERSECTIONS)
    }

    @Test
    fun testMultiplyCoordinates() {
        val pointList: MutableList<Point> = ArrayList()
        pointList.add(Point(2, 2))
        pointList.add(Point(4, 2))
        pointList.add(Point(4, 6))
        pointList.add(Point(4, 10))
        assertEquals(76, multiplyCoordinates(pointList))
        assertEquals(7720, multiplyCoordinates(INTERSECTIONS))
    }
}