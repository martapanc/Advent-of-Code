package aoc2016.day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day4KtTest {

    private val path = "src/main/kotlin/aoc2016/day04/input"
    private val testPath = "src/main/kotlin/aoc2016/day04/testInput"

    @Test
    fun testCountValidDoorNumbers() {
        assertEquals(1514, countValidDoorNumbers(testPath))
        assertEquals(278221, countValidDoorNumbers(path))
    }

    @Test
    fun testDecryptAndFindDoor() {
        assertEquals(267, decryptAndFindDoor(path))
    }

    @Test
    fun find5MostCommonCharsSorted() {
        assertEquals("abxyz", find5MostCommonCharsSorted("aaaaa-bbb-z-y-x"))
        assertEquals("abcde", find5MostCommonCharsSorted("a-b-c-d-e-f-g-h-"))
        assertEquals("abcde", find5MostCommonCharsSorted("a-b-c-e-d-f-g-h-"))
        assertEquals("oarel", find5MostCommonCharsSorted("not-a-real-room"))
    }

    @Test
    fun testDecrypt() {
        assertEquals("very encrypted name", decrypt("qzmt-zixmtkozy-ivhz", 343))
    }
}