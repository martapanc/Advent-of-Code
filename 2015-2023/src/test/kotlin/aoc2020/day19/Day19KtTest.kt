package aoc2020.day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19KtTest {

    private val inputRules = readRulesToMap("src/main/kotlin/aoc2020/day19/input_rules")
    private val inputRulesV2 = readRulesToMap("src/main/kotlin/aoc2020/day19/input_rules_2")
    private val inputMessages = readMessagesToList("src/main/kotlin/aoc2020/day19/input_messages")
    private val input0Rules = readRulesToMap("src/main/kotlin/aoc2020/day19/input0_rules")
    private val input0Messages = readMessagesToList("src/main/kotlin/aoc2020/day19/input0_messages")
    private val input2Rules = readRulesToMap("src/main/kotlin/aoc2020/day19/input2_rules")
    private val input2RulesV2 = readRulesToMap("src/main/kotlin/aoc2020/day19/input2_rules_2")
    private val input2Messages = readMessagesToList("src/main/kotlin/aoc2020/day19/input2_messages")

    @Test
    fun testReadInputFiles() {
        println(input0Rules)
        println(input0Messages)
        println(inputRules)
        println(inputMessages)
    }

    @Test
    fun testFindRegex() {
        assertEquals("a((aa|bb)(ab|ba)|(ab|ba)(aa|bb))b", findRegex(input0Rules))
        assertEquals("(a(b(((b(bba|a(ba|bb))|a(a(aa|bb)|b((b|a", findRegex(inputRules).substring(0, 40))
        assertEquals("b(b(b|a)|ab))b|(aaa|(a(b|a)|bb)b)a)b))))", findRegex(inputRules).substring(2115, 2155))
    }

    @Test
    fun testCountValidMessages() {
        assertEquals(2, countValidMessages(input0Messages, findRegex(input0Rules)))
        assertEquals(3, countValidMessages(input2Messages, findRegex(input2Rules)))
        assertEquals(132, countValidMessages(inputMessages, findRegex(inputRules)))
    }

    @Test
    fun testNewRules() {
        testsWithNewRules(input2RulesV2)
    }

    @Test
    fun testCountValidMessagesV2() {
        assertEquals(12, countValidMessagesV2(input2Messages, input2RulesV2))
        assertEquals(306, countValidMessagesV2(inputMessages, inputRulesV2))
    }
}