package aoc2022.day22

import util.Coord
import util.readInputLineByLine

fun readInputToMapWithBlanks(path: String): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    val lines = readInputLineByLine(path)
    for (y in lines.indices) {
        if (lines[y].isBlank()) break
        lines[y].toCharArray().forEachIndexed { x, char ->
            if (char != ' ') map[Coord(x, y)] = char
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

fun part2(path: String): Long {
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
                    val aroundCube = pos.wrapAroundCubeExample(facing)
                    newPos = aroundCube.pos
                    facing = aroundCube.facing
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

fun Coord.wrapAroundCubeExample(facing: Facing): PosAndFacing {
    val x = this.x
    val y = this.y
    return when {
        facing == Facing.NORTH && y == 0 && x in 8 until 12 -> PosAndFacing(Coord(3 - (x - 8), 4), Facing.SOUTH) // Side 1 to 2
        facing == Facing.NORTH && y == 4 && x in 0 until 4 -> PosAndFacing(Coord((3 - x) + 8, 0), Facing.SOUTH) // Side 2 to 1

        facing == Facing.SOUTH && y == 7 && x in 0 until 4 -> PosAndFacing(Coord((3 - x) + 8, 11), Facing.SOUTH) // Side 2 to 5
        facing == Facing.SOUTH && y == 11 && x in 8 until 12 -> PosAndFacing(Coord(3 - (x - 8), 7), Facing.SOUTH) // Side 5 to 2

        facing == Facing.EAST && x == 11 && y in 0 until 4 -> PosAndFacing(Coord(15, (3 - y) + 8), Facing.WEST) // 1 to 6
        facing == Facing.EAST && x == 15 && y in 8 until 12 -> PosAndFacing(Coord(11, 3 - (y - 8)), Facing.WEST) // 6 to 1

        facing == Facing.WEST && x == 0 && y in 4 until 8 -> PosAndFacing(Coord(3 - (y-4) + 12, 11), Facing.NORTH) // 2 to 5
        facing == Facing.SOUTH && y == 11 && x in 12 until 16 -> PosAndFacing(Coord(0, (3 - (x-12)) + 4), Facing.EAST) // 5 to 2

        facing == Facing.WEST && x == 8 && y in 0 until 4 -> PosAndFacing(Coord(4 + y, 4), Facing.SOUTH) // 1 to 3
        facing == Facing.NORTH && y == 4 && x in 4 until 8 -> PosAndFacing(Coord(8, x - 4), Facing.EAST) // 3 to 1

        facing == Facing.EAST && x == 11 && y in 4 until 8 -> PosAndFacing(Coord(3 - (y-4) + 12, 8), Facing.SOUTH) // 4 to 6
        facing == Facing.NORTH && y == 8 && x in 12 until 16 -> PosAndFacing(Coord(11, 3 - (x-12) + 4), Facing.WEST) // 6 to 4

        facing == Facing.SOUTH && y == 7 && x in 4 until 8 -> PosAndFacing(Coord(8, 3 - (x-4) + 8), Facing.EAST) // 3 to 5
        facing == Facing.WEST && x == 8 && y in 8 until 12 -> PosAndFacing(Coord(3 - (y-8) + 4, 7), Facing.NORTH) // 5 to 3

        else -> PosAndFacing(Coord(0, 0), Facing.NORTH)
    }

}

private fun Facing.wrapAround(newPos: Coord, map: Map<Coord, Char>) = when (this) {
    Facing.NORTH -> Coord(newPos.x, map.keys.filter { it.x == newPos.x }.maxBy { it.y }.y)
    Facing.EAST -> Coord(map.keys.filter { it.y == newPos.y }.minBy { it.x }.x, newPos.y)
    Facing.SOUTH -> Coord(newPos.x, map.keys.filter { it.x == newPos.x }.minBy { it.y }.y)
    Facing.WEST -> Coord(map.keys.filter { it.y == newPos.y }.maxBy { it.x }.x, newPos.y)
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

class PosAndFacing(val pos: Coord, val facing: Facing)

enum class Facing(val value: Int) { NORTH(3), EAST(0), SOUTH(1), WEST(2) }

enum class Rotation(val char: Char) {
    LEFT('L'), RIGHT('R');

    companion object {
        fun fromSign(char: Char): Rotation? = Rotation.values().firstOrNull() { char == it.char }
    }
}

