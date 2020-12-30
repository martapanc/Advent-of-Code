package aoc2020.day12

import java.io.File
import kotlin.math.abs

fun readInputToList(path: String): List<Instruction> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    return lineList.map { Instruction(it[0], it.substring(1).toInt()) }
}

fun navigateShip(instructions: List<Instruction>): Int {
    var position = Coordinate(0, 0)
    var facingDirection = CardinalPoints.EAST
    for (instruction in instructions) {
        when (instruction.direction) {
            'N' -> position = Coordinate(position.x, position.y + instruction.value)
            'S' -> position = Coordinate(position.x, position.y - instruction.value)
            'E' -> position = Coordinate(position.x + instruction.value, position.y)
            'W' -> position = Coordinate(position.x - instruction.value, position.y)
            'L', 'R' -> facingDirection = rotateShip(facingDirection, instruction.value, instruction.direction)
            'F' -> {
                position = when (facingDirection) {
                    CardinalPoints.EAST -> Coordinate(position.x + instruction.value, position.y)
                    CardinalPoints.SOUTH -> Coordinate(position.x, position.y - instruction.value)
                    CardinalPoints.WEST -> Coordinate(position.x - instruction.value, position.y)
                    CardinalPoints.NORTH -> Coordinate(position.x, position.y + instruction.value)
                }
            }
        }
    }
    return abs(position.x) + abs(position.y)
}

/**
 * Compute new direction of the ship after a rotation
 *
 * @param facingDirection: the initial direction of the ship (NORTH, SOUTH, EAST, WEST)
 * @param degrees: amount of rotation degrees (0, 90, 180, 270)
 * @param direction: left ('L') or right ('R')
 * @return the new facing direction of the ship
 */
fun rotateShip(facingDirection: CardinalPoints, degrees: Int, direction: Char): CardinalPoints {
    val clockwise = listOf(CardinalPoints.NORTH, CardinalPoints.EAST, CardinalPoints.SOUTH, CardinalPoints.WEST)
    var rotationAmount = listOf(0, 90, 180, 270).indexOf(degrees)
    rotationAmount = if (direction == 'R') rotationAmount else -rotationAmount
    var newDirectionIndex = clockwise.indexOf(facingDirection) + rotationAmount
    // Sadly, Kotlin doesn't support negative indices for lists...
    newDirectionIndex = if (newDirectionIndex < 0) 4 - abs(newDirectionIndex) else newDirectionIndex % 4
    return clockwise[newDirectionIndex]
}

fun navigateShipAndWaypoint(instructions: List<Instruction>): Int {
    var shipPos = Coordinate(0, 0)
    var waypointPos = Coordinate(10, 1)

    for (instruction in instructions) {
        val relativeWpPos = Coordinate(waypointPos.x - shipPos.x, waypointPos.y - shipPos.y)
        when (instruction.direction) {
            'N' -> waypointPos = Coordinate(waypointPos.x, waypointPos.y + instruction.value)
            'S' -> waypointPos = Coordinate(waypointPos.x, waypointPos.y - instruction.value)
            'E' -> waypointPos = Coordinate(waypointPos.x + instruction.value, waypointPos.y)
            'W' -> waypointPos = Coordinate(waypointPos.x - instruction.value, waypointPos.y)
            'L' -> {
                waypointPos = when (instruction.value) {
                    90 -> Coordinate(shipPos.x - relativeWpPos.y, shipPos.y + relativeWpPos.x)
                    180 -> Coordinate(shipPos.x - relativeWpPos.x, shipPos.y - relativeWpPos.y)
                    else -> Coordinate(shipPos.x + relativeWpPos.y, shipPos.y - relativeWpPos.x)
                }
            }
            'R' -> {
                waypointPos = when (instruction.value) {
                    90 -> Coordinate(shipPos.x + relativeWpPos.y, shipPos.y - relativeWpPos.x)
                    180 -> Coordinate(shipPos.x - relativeWpPos.x, shipPos.y - relativeWpPos.y)
                    else -> Coordinate(shipPos.x - relativeWpPos.y, shipPos.y + relativeWpPos.x)
                }
            }
            'F' -> {
                val newShipX = shipPos.x + relativeWpPos.x * instruction.value
                val newShipY = shipPos.y + relativeWpPos.y * instruction.value
                shipPos = Coordinate(newShipX, newShipY)
                waypointPos = Coordinate(shipPos.x + relativeWpPos.x, shipPos.y + relativeWpPos.y)
            }
        }
    }
    return abs(shipPos.x) + abs(shipPos.y)
}

data class Instruction(var direction: Char, var value: Int)

enum class CardinalPoints { NORTH, SOUTH, EAST, WEST }

class Coordinate(var x: Int, var y: Int)