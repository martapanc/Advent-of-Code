package aoc2018.day20

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day20KtTest {

    private val testInput1 = "^ENWWW(NEEE|SSE(EE|N))$"
    private val testInput2 = "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"
    private val testInput3 = "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$"
    private val testInput4 = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$"
    private val input = readInputLineByLine("src/main/kotlin/aoc2018/day20/input")[0]

    @Test
    fun testFindLongestUniquePathToFurthestDoor() {
        assertEquals(10, findLongestUniquePathToFurthestDoor(testInput1))
        assertEquals(18, findLongestUniquePathToFurthestDoor(testInput2))
        assertEquals(23, findLongestUniquePathToFurthestDoor(testInput3))
        assertEquals(31, findLongestUniquePathToFurthestDoor(testInput4))
        assertEquals(4431, findLongestUniquePathToFurthestDoor(input))
    }
}