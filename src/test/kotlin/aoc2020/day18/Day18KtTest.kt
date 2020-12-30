package aoc2020.day18

import aoc2020.day5.readInputToList
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day18KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day18/input")
    private val input0 = readInputToList("src/main/kotlin/aoc2020/day18/input0")

    @Test
    fun readInput() {
        println(input0)
        println(input)
    }

    @Test
    fun testRunOperationsLeftToRight() {
        val order = OperationOrder.LEFT_TO_RIGHT
        assertEquals(35, runOperations(listOf("( 2 + 3 ) * 7"), order))
        assertEquals(35, runOperations(listOf("2 + 3 * 7"), order))
        assertEquals(23, runOperations(listOf("3 * 7 + 2"), order))
        assertEquals(58, runOperations(listOf("5 + 3 * 7 + 2"), order))
        assertEquals(72, runOperations(listOf("( 5 + 3 ) * ( 7 + 2 )"), order))
        assertEquals(580, runOperations(listOf("( ( 5 + 3 ) * 7 ) + 2 * 10"), order))
        assertEquals(437, runOperations(listOf("5 + ( 8 * 3 + 9 + 3 * 4 * 3 )"), order))
        assertEquals(12240, runOperations(listOf("5 * 9 * ( 7 * 3 * 3 + 9 * 3 + ( 8 + 6 * 4 ) )"), order))
        assertEquals(13632, runOperations(listOf("( ( 2 + 4 * 9 ) * ( 6 + 9 * 8 + 6 ) + 6 ) + 2 + 4 * 2"), order))
        assertEquals(148, runOperations(input0, order))
        assertEquals(45283905029161, runOperations(input, order))
    }

    @Test
    fun testRunOperationsAdditionBeforeMultiplication() {
        val order = OperationOrder.ADDITION_BEFORE_MULTIPLICATION
        assertEquals(35, runOperations(listOf("( 2 + 3 ) * 7"), order))
        assertEquals(35, runOperations(listOf("2 + 3 * 7"), order))
        assertEquals(27, runOperations(listOf("3 * 7 + 2"), order))
        assertEquals(72, runOperations(listOf("5 + 3 * 7 + 2"), order))
        assertEquals(72, runOperations(listOf("( 5 + 3 ) * ( 7 + 2 )"), order))
        assertEquals(580, runOperations(listOf("( ( 5 + 3 ) * 7 ) + 2 * 10"), order))
        assertEquals(46, runOperations(listOf("2 * 3 + ( 4 * 5 )"), order))
        assertEquals(1445, runOperations(listOf("5 + ( 8 * 3 + 9 + 3 * 4 * 3 )"), order))
        assertEquals(669060, runOperations(listOf("5 * 9 * ( 7 * 3 * 3 + 9 * 3 + ( 8 + 6 * 4 ) )"), order))
        assertEquals(23340, runOperations(listOf("( ( 2 + 4 * 9 ) * ( 6 + 9 * 8 + 6 ) + 6 ) + 2 + 4 * 2"), order))
        assertEquals(231, runOperations(listOf("1 + 2 * 3 + 4 * 5 + 6"), order))
        assertEquals(282, runOperations(listOf("1 + 2 * 3 + 4 * 5 + 6", "1 + ( 2 * 3 ) + ( 4 * ( 5 + 6 ) )"), order))
        assertEquals(328, runOperations(listOf("1 + 2 * 3 + 4 * 5 + 6", "1 + ( 2 * 3 ) + ( 4 * ( 5 + 6 ) )", "2 * 3 + ( 4 * 5 )"), order))
        assertEquals(328, runOperations(input0, order))
        assertEquals(216975281211165, runOperations(input, order))
    }
}