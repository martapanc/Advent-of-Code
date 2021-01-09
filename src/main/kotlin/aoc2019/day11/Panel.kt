package aoc2019.day11

import java.util.HashMap

class Panel(var currentPanel: Int, var direction: Direction) {
    fun paintPanelBlack() {
        currentPanel = 0
    }

    fun paintPanelWhite() {
        currentPanel = 1
    }

    enum class Direction(val dirChar: Char) {
        UP('^'), DOWN('v'), RIGHT('>'), LEFT('<');

        companion object {
            private val map: MutableMap<Char, Direction> = HashMap<Char, Direction>()
            fun valueOf(direction: Char): Direction? {
                return map[direction] as Direction?
            }

            init {
                for (d in values()) {
                    map[d.dirChar] = d
                }
            }
        }
    }

    override fun toString(): String {
        return "{" +
                "current=" + currentPanel +
                ", direction=" + direction +
                '}'
    }
}