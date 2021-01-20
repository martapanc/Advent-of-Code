package aoc2019.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    private val input = "src/main/kotlin/aoc2019/day21/input"

    private val script1 = "" +
            "NOT A J\n" +
            "NOT J J\n" +
            "AND B J\n" +
            "AND C J\n" +
            "NOT J J\n" +
            "AND D J\n" +
            "WALK\n"

    private val script2 = "" +
            "OR A J\n" +
            "AND B J\n" +
            "AND C J\n" +
            "NOT J J\n" +
            "AND D J\n" +
            "OR E T\n" +
            "OR H T\n" +
            "AND T J\n" +
            "RUN\n"

    @Test
    fun testReadIntcodeInput() {
        assertEquals(19358416, readIntcodeInput(input, script1))
        assertEquals(1144641747, readIntcodeInput(input, script2))
    }
}