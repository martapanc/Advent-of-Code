package aoc2022.day23

import aoc2021.day25.printMap
import aoc2022.day22.Facing
import util.Coord

val deltas8 = listOf(Coord(-1, -1), Coord(0, -1), Coord(1, -1),
    Coord(-1, 0), Coord(1, 0),
    Coord(-1, 1), Coord(0, 1), Coord(1, 1))

val north = listOf(Coord(-1, -1), Coord(0, -1), Coord(1, -1))
val south = listOf(Coord(-1, 1), Coord(0, 1), Coord(1, 1))
val east = listOf(Coord(1, -1), Coord(1, 0), Coord(1, 1))
val west = listOf(Coord(-1, -1), Coord(-1, 0), Coord(-1, 1))
val directions = mutableMapOf(Facing.NORTH to north, Facing.SOUTH to south, Facing.EAST to east, Facing.WEST to west)

fun part1(map: MutableMap<Coord, Char>): Int {
    loop@ while(true) {
        val currentElves = map.filter { it.value == '#' }
        val neighborMap = mutableMapOf<Coord, List<Coord>>()
        currentElves.keys.forEach { coord ->
            neighborMap[coord] = coord.getNeighborElves(map, deltas8)
        }
        val elvesConsideringMoving = neighborMap.entries.filter { it.value.isNotEmpty() }
        if (elvesConsideringMoving.isEmpty()) {
            break@loop
        }
        val movementProposals = mutableMapOf<Coord, Coord>()
        elvesConsideringMoving.forEach { elf ->
            run assessDirections@ {
                directions.forEach { dir ->
                    val dirCoords = elf.key.getNeighborElves(map, dir.value)
                    if (dirCoords.isEmpty()) {
                        movementProposals[elf.key] = elf.key.move(dir.key)
                        return@assessDirections
                    }
                }
            }
        }
        movementProposals.entries.forEach { entry ->
            if (movementProposals.count { entry.value == it.value } == 1) {
                entry.key.moveOnMap(entry.value, map)
            }
        }

        val rotateKey = directions.keys.first()
        val rotateDir = directions.remove(rotateKey)!!
        directions[rotateKey] = rotateDir
        printMap(map)
    }
    return computeMinRectangle(map)
}

fun part2(map: Map<Coord, Char>): Int {
    return 0
}

fun Coord.getNeighborElves(map: Map<Coord, Char>, deltas: List<Coord>): List<Coord> {
    val neighborCoords = deltas.map { Coord(this.x + it.x, this.y + it.y) }
    val neighbors = mutableListOf<Coord>()
    neighborCoords.forEach { coord ->
        if (map[coord] == '#') neighbors.add(coord)
    }
    return neighbors
}

fun Coord.move(direction: Facing): Coord {
    return when(direction) {
        Facing.NORTH -> Coord(this.x, this.y - 1)
        Facing.SOUTH -> Coord(this.x, this.y + 1)
        Facing.EAST -> Coord(this.x + 1, this.y)
        Facing.WEST -> Coord(this.x - 1, this.y)
    }
}

fun Coord.moveOnMap(target: Coord, map: MutableMap<Coord, Char>): MutableMap<Coord, Char> {
    map[this] = '.'
    map[target] = '#'
    return map
}

fun computeMinRectangle(map: Map<Coord, Char>): Int {
    val loX = map.keys.minBy { it.x }.x
    val loY = map.keys.minBy { it.y }.y
    val hiX = map.keys.maxBy { it.x }.x
    val hiY = map.keys.maxBy { it.y }.y
    val elfCount = map.values.count { it == '#' }
    return (hiX - loX + 1) * (hiY - loY + 1) - elfCount
}
