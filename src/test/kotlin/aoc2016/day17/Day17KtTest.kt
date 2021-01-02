package aoc2016.day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17KtTest {

    private val testInput1 = "ihgpwlah"
    private val testInput2 = "kglvqrro"
    private val testInput3 = "ulqzkmiv"
    private val input = "pxxbnzuo"

    @Test
    fun testNavigateVault() {
        assertEquals("DDRRRD", navigateVault(testInput1))
        assertEquals("DDUDRLRRUDRD", navigateVault(testInput2))
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", navigateVault(testInput3))
        assertEquals("RDULRDDRRD", navigateVault(input))
    }

    @Test
    fun testMd5() {
        assertEquals("ced9", md5("hijkl"))
        assertEquals("f2bc", md5("hijklD"))
        assertEquals("5745", md5("hijklDR"))
        assertEquals("528e", md5("hijklDU"))
    }
}