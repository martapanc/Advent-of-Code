package aoc2022.day22

import util.Coord
import util.readInputLineByLine

fun readInputToMapWithBlanks(path: String): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    val lines = readInputLineByLine(path)
    for (y in lines.indices) {
        if (lines[y].isBlank()) {
            break
        }
        lines[y].toCharArray().forEachIndexed { x, char ->
            if (char != ' ') {
                map[Coord(x, y)] = char
            }
        }
    }
    return map
}

fun readInputToInstructions(path: String): List<Instruction> {
    val lines = readInputLineByLine(path)
    val instructions = lines[lines.size - 1].replace("R", "R ").replace("L", "L ").split(" ")
    return instructions.map { string ->
        val rot = string.last()
        if (rot.isDigit()) {
            Instruction(string.toInt(), null)
        } else {
            val value = string.replace("L", "").replace("R", "")
            Instruction(value.toInt(), Rotation.fromSign(rot)!!)
        }
    }
}

fun part1(path: String): Long {
    val map = readInputToMapWithBlanks(path)
    val instructions = readInputToInstructions(path)

    val deltas = mapOf(Facing.EAST to Coord(1, 0), Facing.WEST to Coord(-1, 0),
        Facing.NORTH to Coord(0, -1), Facing.SOUTH to Coord(0, 1))
    var pos = map.toSortedMap(compareBy({ it.y }, { it.x })).firstKey()
    var facing = Facing.EAST
    instructions.forEach { instruction ->
        run forward@ {
            repeat(instruction.number) {
                var newPos = Coord(pos.x + deltas[facing]!!.x, pos.y + deltas[facing]!!.y)
                if (map[newPos] == null) {
                    newPos = facing.wrapAround(newPos, map)
                }
                if (map[newPos] != null && map[newPos] == '#') { // bump into wall
                    return@forward
                }
                pos = newPos
            }
        }
        if (instruction.rotation != null) {
            facing = facing.rotate(instruction.rotation)
        }
    }
    return 4 * (pos.x + 1) + 1000L * (pos.y + 1) + facing.value
}

private fun Facing.wrapAround(newPos: Coord, map: Map<Coord, Char>) = when (this) {
    Facing.NORTH -> Coord(newPos.x, map.keys.filter { it.x == newPos.x }.maxBy { it.y }.y)
    Facing.EAST -> Coord(map.keys.filter { it.y == newPos.y }.minBy { it.x }.x, newPos.y)
    Facing.SOUTH -> Coord(newPos.x, map.keys.filter { it.x == newPos.x }.minBy { it.y }.y)
    Facing.WEST -> Coord(map.keys.filter { it.y == newPos.y }.maxBy { it.x }.x, newPos.y)
}

fun part2(list: String): Long {
    return 0
}

fun Facing.rotate(rotation: Rotation): Facing {
    var index = Facing.values().indexOf(this)
    index = if (rotation == Rotation.RIGHT) {
        (index + 1) % 4
    } else {
        if (index > 0) index - 1 else 3
    }
    return Facing.values()[index]
}

class Instruction(val number: Int, val rotation: Rotation?) {
    override fun toString(): String = "$number $rotation"
}

enum class Facing(val value: Int) { NORTH(3), EAST(0), SOUTH(1), WEST(2) }

enum class Rotation(val char: Char) {
    LEFT('L'), RIGHT('R');

    companion object {
        fun fromSign(char: Char): Rotation? = Rotation.values().firstOrNull() { char == it.char }
    }
}
