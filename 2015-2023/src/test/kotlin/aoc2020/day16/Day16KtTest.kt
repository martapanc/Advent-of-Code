package aoc2020.day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16KtTest {

    private val inputFields = readInputFieldsToMap("src/main/kotlin/aoc2020/day16/input_fields")
    private val inputTickets = readInputTicketsToList("src/main/kotlin/aoc2020/day16/input_tickets")
    private val input0Fields = readInputFieldsToMap("src/main/kotlin/aoc2020/day16/input0_fields")
    private val input0Tickets = readInputTicketsToList("src/main/kotlin/aoc2020/day16/input0_tickets")

    //Part 2
    private val input1Fields = readInputFieldsToMap("src/main/kotlin/aoc2020/day16/input1_fields")
    private val input1Tickets = readInputTicketsToList("src/main/kotlin/aoc2020/day16/input1_tickets")

    @Test
    fun readInputFieldsToMap() {
        assertEquals(20, inputFields.size)
    }

    @Test
    fun readInputTicketsToList() {
        assertEquals(235, inputTickets.size)
    }

    @Test
    fun computeTicketScanningErrorRate() {
        assertEquals(19070, computeTicketScanningErrorRate(inputTickets, inputFields).ticketScanningErrorRate)
        assertEquals(71, computeTicketScanningErrorRate(input0Tickets, input0Fields).ticketScanningErrorRate)
    }

    @Test
    fun computeDepartureValuesChecksum() {
        assertEquals(
            143, computeDepartureValuesChecksum(
                listOf(11, 12, 13), input1Tickets, input1Fields,
                listOf("row", "seat")
            )
        )
        assertEquals(
            161926544831, computeDepartureValuesChecksum(
                listOf(83, 53, 73, 139, 127, 131, 97, 113, 61, 101, 107, 67, 79, 137, 89, 109, 103, 59, 149, 71),
                inputTickets, inputFields,
                listOf(
                    "departure location",
                    "departure station",
                    "departure platform",
                    "departure track",
                    "departure date",
                    "departure time"
                )
            )
        )
    }
}