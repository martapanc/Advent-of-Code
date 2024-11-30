package aoc2018.day05

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class Day5KtTest {
    @Test
    fun test_compute_alchemical_reduction() {
        val input = readInputFile("src/main/kotlin/aoc2018/day05/in1")
        Assertions.assertEquals("dabCBAcaDA", computeAlchemicalReduction(input))
    }

    @Test
    fun test_compute_alchemical_reduction_length() {
        val input = readInputFile("src/main/kotlin/aoc2018/day05/in1")
        Assertions.assertEquals(10, computeAlchemicalReductionLength(input))
    }

    @Test
    fun test_compute_alchemical_reduction_2() {
        val input = readInputFile("src/main/kotlin/aoc2018/day05/in2")
        Assertions.assertEquals(10638, computeAlchemicalReductionLength(input))
    }

    @Test
    fun test_compute_alchemical_reduction_3() {
        val input = readInputFile("src/main/kotlin/aoc2018/day05/in3")
        Assertions.assertEquals("yj", computeAlchemicalReduction(input))
    }

    @Test
    fun test_char_remove_at() {
        Assertions.assertEquals("mara", charRemoveAt("marta", 3))
    }

    @Test
    fun test_remove_all_letter() {
        Assertions.assertEquals("dabCBAcaDA", removeAllLetter("daFbCBfAcaFfDA", "f"))
    }

    @Test
    fun test_compute_minimum_alchemical_reduction() {
        val input = readInputFile("src/main/kotlin/aoc2018/day05/in2")
        val results: MutableList<Int> = ArrayList()
        var letter = 'a'
        while (letter <= 'z') {
            results.add(computeAlchemicalReductionLength(removeAllLetter(input, letter.toString() + "")))
            letter++
        }
        println(results)
    }
}