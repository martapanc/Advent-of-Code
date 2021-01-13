package aoc2018.day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day4KtTest {
    //Sort entries by date and time
    //Get guard with most minutes asleep overall
    //Result is guard's ID * minute of most common asleep time
    @Test
    fun test_read_input_and_sort() {
    }

    @Test
    fun test_get_guard_with_most_time_asleep() {
        val list: List<GuardStatus> = sortInputByDate(readInputAndParse("src/main/kotlin/aoc2018/day04/out2"))
        assertEquals(10, getGuardWithMostTimeAsleep(list))
    }

    @Test
    fun test_get_guard_with_most_time_asleep_2() {
        val list: List<GuardStatus> = sortInputByDate(readInputAndParse("src/main/kotlin/aoc2018/day04/out"))
        assertEquals(1487, getGuardWithMostTimeAsleep(list))
    }

    @Test
    fun test_draw_sleep_pattern() {
        val list: List<GuardStatus> = sortInputByDate(readInputAndParse("src/main/kotlin/aoc2018/day04/out2"))
        drawSleepPattern(10, list)
    }

    @Test
    fun test_draw_sleep_pattern_2() {
        val list: List<GuardStatus> = sortInputByDate(readInputAndParse("src/main/kotlin/aoc2018/day04/out"))
        drawSleepPattern(1487, list)
    }

    //Result is 1487*34
    // Part 2 - Example
    // Matrix where x are the minutes and y are the guard IDs
    //   000000000011111111112222222222333333333344444444445555555555
    //   012345678901234567890123456789012345678901234567890123456789
    //10 .....11111111111111111112111101111111111111111111111111.....
    //99 ....................................1111222223222211111.....
    //
    // The result is the ID of the guard times the minute you chose (e.g. 99 * 45 = 4455)
    @Test
    fun test_sleep_count_matrix() {
        val list: List<GuardStatus> = sortInputByDate(readInputAndParse("src/main/kotlin/aoc2018/day04/out2"))
        buildSleepCountMatrix(list)
    }

    @Test
    fun test_sleep_count_matrix_2() {
        val list: List<GuardStatus> = sortInputByDate(readInputAndParse("src/main/kotlin/aoc2018/day04/out"))
        buildSleepCountMatrix(list)
    }
}