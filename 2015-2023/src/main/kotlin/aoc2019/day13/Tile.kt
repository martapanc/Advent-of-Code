package aoc2019.day13

import java.util.*

enum class Tile(private val id: Int, val symbol: String) {
    EMPTY(0, " "), WALL(1, "|"), BLOCK(2, "■"), HORIZONTAL_PADDLE(3, "_"), BALL(4, "o");

    companion object {
        private val map: MutableMap<Int, Tile> = HashMap<Int, Tile>()

        @JvmStatic
        fun valueOf(tileId: Int): Tile? {
            return map[tileId]
        }

        init {
            for (t in values()) {
                map[t.id] = t
            }
        }
    }

}