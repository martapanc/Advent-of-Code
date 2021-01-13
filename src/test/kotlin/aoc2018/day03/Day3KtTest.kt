package aoc2018.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class Day3KtTest {

    @Test
    fun test_compute_occupied_inches() {
        val expCoordinates: MutableList<String> = ArrayList()
        expCoordinates.add("2,2")
        expCoordinates.add("2,3")
        expCoordinates.add("3,2")
        expCoordinates.add("3,3")
        assertEquals(expCoordinates, computeOccupiedInches(15, 1, 1, 2, 2).coordinates)
    }

    @Test
    fun test_compute_occupied_inches_2() {
        val expCoordinates: MutableList<String> = ArrayList()
        expCoordinates.add("3,4")
        assertEquals(expCoordinates, computeOccupiedInches(16, 2, 3, 1, 1).coordinates)
    }

    @Test
    fun test_compute_occupied_inches_3() {
        val expCoordinates: MutableList<String> = ArrayList()
        expCoordinates.add("2,2")
        assertEquals(expCoordinates, computeOccupiedInches(17, 1, 1, 1, 1).coordinates)
    }

    @Test
    fun test_compute_occupied_inches_4() {
        val expCoordinates: MutableList<String> = ArrayList()
        expCoordinates.add("2,3")
        expCoordinates.add("2,4")
        expCoordinates.add("2,5")
        expCoordinates.add("3,3")
        expCoordinates.add("3,4")
        expCoordinates.add("3,5")
        assertEquals(expCoordinates, computeOccupiedInches(18, 1, 2, 2, 3).coordinates)
    }

    @Test
    fun test_compute_occupied_inches_5() {
        val expCoordinates: MutableList<String> = ArrayList()
        expCoordinates.add("2,4")
        expCoordinates.add("2,5")
        expCoordinates.add("2,6")
        expCoordinates.add("2,7")
        expCoordinates.add("3,4")
        expCoordinates.add("3,5")
        expCoordinates.add("3,6")
        expCoordinates.add("3,7")
        expCoordinates.add("4,4")
        expCoordinates.add("4,5")
        expCoordinates.add("4,6")
        expCoordinates.add("4,7")
        expCoordinates.add("5,4")
        expCoordinates.add("5,5")
        expCoordinates.add("5,6")
        expCoordinates.add("5,7")
        assertEquals(expCoordinates, computeOccupiedInches(19, 1, 3, 4, 4).coordinates)
    }

    @Test
    fun test_compute_occupied_inches_6() {
        val expCoordinates: MutableList<String> = ArrayList()
        expCoordinates.add("1,1")
        expCoordinates.add("1,2")
        expCoordinates.add("2,1")
        expCoordinates.add("2,2")
        assertEquals(expCoordinates, computeOccupiedInches(20, 0, 0, 2, 2).coordinates)
    }

    @Test
    fun test_check_overlapping_coordinates() {
        val coordinatesList: MutableList<Item> = ArrayList()
        coordinatesList.add(computeOccupiedInches(21, 1, 3, 4, 4))
        coordinatesList.add(computeOccupiedInches(22, 3, 1, 4, 4))
        val overlappingCoordinates: MutableList<String> = ArrayList()
        overlappingCoordinates.add("4,4")
        overlappingCoordinates.add("4,5")
        overlappingCoordinates.add("5,4")
        overlappingCoordinates.add("5,5")
        assertEquals(overlappingCoordinates, computeOverlappingCoordinates(coordinatesList))
    }

    @Test
    fun test_check_number_of_overlapping_coordinates() {
        val coordinatesList: MutableList<Item> = ArrayList()
        coordinatesList.add(computeOccupiedInches(23, 1, 3, 4, 4))
        coordinatesList.add(computeOccupiedInches(24, 3, 1, 4, 4))
        coordinatesList.add(computeOccupiedInches(25, 5, 5, 2, 2))
        assertEquals(4, computeNumberOfOverlappingCoordinates(coordinatesList))
    }

    @Test
    fun test_check_number_of_overlapping_coordinates_1() {
        val coordinatesList: MutableList<Item> = ArrayList()
        coordinatesList.add(computeOccupiedInches(26, 6, 51, 45, 16))
        coordinatesList.add(computeOccupiedInches(27, 1, 68, 14, 13))
        coordinatesList.add(computeOccupiedInches(28, 9, 65, 11, 22))
        assertEquals(100, computeNumberOfOverlappingCoordinates(coordinatesList))
    }

    @Test
    fun test_check_number_of_overlapping_coordinates_3() {
        val coordinatesList: MutableList<Item> = ArrayList()
        coordinatesList.add(computeOccupiedInches(29, 1, 1, 3, 3))
        coordinatesList.add(computeOccupiedInches(30, 1, 1, 5, 5))
        coordinatesList.add(computeOccupiedInches(31, 28, 29, 11, 3))
        assertEquals(9, computeNumberOfOverlappingCoordinates(coordinatesList))
    }
}