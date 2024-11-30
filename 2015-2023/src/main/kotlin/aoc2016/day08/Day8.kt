package aoc2016.day08

import util.Coord
import util.readInputLineByLine
import java.util.*

//....................................................
//..##..####.#....####.#.....##..#...#####..##...###..
//.#..#.#....#....#....#....#..#.#...##....#..#.#.....
//.#....###..#....###..#....#..#..#.#.###..#....#.....
//.#....#....#....#....#....#..#...#..#....#.....##...
//.#..#.#....#....#....#....#..#...#..#....#..#....#..
//..##..#....####.####.####..##....#..#.....##..###...
//....................................................

fun run2FAProgram(path: String): Int {
    val grid = mutableMapOf<Coord, Char>()
    for (y in 0..5) for (x in 0..49) grid[Coord(x, y)] = '.'

    for (line in readInputLineByLine(path))
        if (line.startsWith("rect")) {
            val split = line.split(" ")[1].split("x")
            val xRect = split[0].toInt() - 1
            val yRect = split[1].toInt() - 1
            for (y in 0..yRect)
                for (x in 0..xRect)
                    grid[Coord(x, y)] = '#'
        } else {
            val split = line.split(" ")
            val rotateValue = split[4].toInt()
            if (line.contains("y=")) {
                val yRotate = split[2].replace("y=", "").toInt()
                val row = grid.entries.filter { it.key.y == yRotate }.map { it.value }
                for (x in 0..49) {
                    grid[Coord(x, yRotate)] = rotateRowOrColumn(row, rotateValue)[x]
                }
            } else {
                val xRotate = split[2].replace("x=", "").toInt()
                val column = grid.entries.filter { it.key.x == xRotate }.map { it.value }
                for (y in 0..5) {
                    grid[Coord(xRotate, y)] = rotateRowOrColumn(column, rotateValue)[y]
                }
            }
        }
    return grid.values.count { it == '#' }
}

fun rotateRowOrColumn(array: List<Char>, rotateBy: Int): List<Char> {
    val output = array.toMutableList()
    Collections.rotate(output, rotateBy)
    return output
}
