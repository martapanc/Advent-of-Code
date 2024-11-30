package aoc2017.day15

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day15KtTest {

    private val generatorAStart = 634L
    private val generatorBStart = 301L

    @Test
    fun testComputeNextNumber() {
        assertEquals(1181022009, computeNextNumber(1092455, Generator.A))
        assertEquals(137874439, computeNextNumber(1431495498, Generator.B))
    }

    @Test
    fun testFindRightMost16BinaryRepresentation() {
        assertEquals("1010101101100111", genRightmost16BinRepresentation(1092455))
        assertEquals("1110001101001010", genRightmost16BinRepresentation(1431495498))
        assertEquals("1110001101001010", genRightmost16BinRepresentation(1431495498 - 65536))
    }

    @Test
    fun testCompareGeneratorValues() {
        assertEquals(588, compareGeneratorValues(1092455, 430625591))
        assertEquals(573, compareGeneratorValues(generatorAStart, generatorBStart))
    }

    @Test
    fun testCompareGeneratorValuesPart2() {
        assertEquals(309, compareGeneratorValuesPart2(1352636452, 1233683848))
        assertEquals(294, compareGeneratorValuesPart2(generatorAStart, generatorBStart))
    }
}