package aoc2020.day25

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25KtTest {

    @Test
    fun findEncryptionKey() {
        assertEquals(14897079, findEncryptionKey(doorPubKey = 17807724, cardPubKey = 5764801))
        assertEquals(5025281, findEncryptionKey(doorPubKey = 12721030, cardPubKey = 10943862))
    }
}