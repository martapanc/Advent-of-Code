package aoc2019.day22

import util.readInputLineByLine
import kotlin.math.abs
import kotlin.math.min

fun runNaiveShuffler(path: String, deckSize: Int = 10007, outputCard: Int = 2019): Int {
    val deck = IntArray(deckSize) { it }
    for (line in readInputLineByLine(path))
        when {
            line.startsWith("cut") -> {
                val cutValue = line.split(" ")[1].toInt()
                val newDeck = IntArray(deckSize) { c ->
                    deck[c + cutValue modulo deckSize]
                }
                newDeck.copyInto(deck)
            }
            line.startsWith("deal with increment") -> {
                val incrementValue = line.split(" ")[3].toInt()
                val newDeck = IntArray(deckSize)
                var index = 0
                repeat(deckSize) { i ->
                    newDeck[index] = deck[i]
                    index += incrementValue
                    index %= deckSize
                }
                newDeck.copyInto(deck)
            }
            line == "deal into new stack" -> {
                deck.reverse()
            }
        }
    return deck.indexOf(outputCard)
}

fun runInsaneShuffler(
    path: String,
    deckSize: Long = 119315717514047,
    repeat: Long = 101741582076661,
    outputPos: Long = 2020
): Long {
    var a = 1L
    var b = 0L

    for (line in readInputLineByLine(path)) {
        when {
            line == "deal into new stack" -> {
                // x → -x - 1; ax + b → -ax - b - 1
                a = -a modulo deckSize
                b = b.inv() modulo deckSize // b.inv() = -b - 1
            }
            "cut" in line -> {
                // x → x - i; ax + b → ax + b - i
                val i = line.split(' ').last().toInt()
                b = b - i modulo deckSize
            }
            "deal with increment" in line -> {
                // x → x · i; ax + b → aix + bi
                val i = line.split(' ').last().toLong()
                a = a.mulMod(i, deckSize)
                b = b.mulMod(i, deckSize)
            }
        }
    }

    // invert basis function. f^-1(x) = (a^-1)(x - b)
    a = a.powMod(deckSize - 2, deckSize) // modular multiplicative inverse for prime m
    b = a.mulMod(-b, deckSize)

    // start exponentiation for function, f^k(x) = cx + d
    var c = 1L
    var d = 0L
    var e = abs(repeat)

    // exponentiation by squaring. Equivalent to computing
    // ⌈ a 0 ⌉ k
    // ⌊ b 1 ⌋
    while (e > 0) {
        if (e and 1 == 1L) {
            // a(cx + d) + b = acx + (ad + b)
            c = a.mulMod(c, deckSize)
            d = (a.mulMod(d, deckSize) + b) % deckSize
        }
        e = e shr 1
        b = (a.mulMod(b, deckSize) + b) % deckSize
        a = a.mulMod(a, deckSize)
    }
    return (outputPos.mulMod(c, deckSize) + d) % deckSize
}

fun Long.mulMod(other: Long, m: Long): Long = _mulMod(this modulo m, other modulo m, m)

fun _norm(it: Long, mod: Long) = (it shr Long.SIZE_BITS - 1 and mod) + it
private fun _doubleAndAdd(a: Long, b: Long, m: Long): Long {
    var res = 0L
    var b1 = b
    var a1 = a
    if (a1 < b1) a1 = b1.also { b1 = a1 }

    while (b1 > 0) {
        if (b1 and 1 == 1L) res = _norm(res + a1 - m, m)
        b1 = b1 shr 1
        a1 = _norm(a1.shl(1) - m, m)
    }
    return res
}

// unchecked version, assumes 0 <= a, b < m
fun _mulMod(a: Long, b: Long, m: Long): Long {
    if (m <= FLOOR_SQRT_MAX_LONG + 1) return a * b % m
    if (m < 1L shl 57) {
        val g = a.toDouble() * b / m
        return a * b - g.toLong() * m modulo m
    }
    if (m > Long.MIN_VALUE ushr 1) return _doubleAndAdd(a, b, m)
    var hi = Math.multiplyHigh(a, b) shl 1
    var lo = a * b
    if (lo < 0) {
        hi = hi or 1
        lo = lo xor Long.MIN_VALUE
    }
    return _norm(hi.shl63Mod(m) + lo % m - m, m)
}

const val FLOOR_SQRT_MAX_LONG = 3037000499L
inline val Long.numLeadingZeroes get() = java.lang.Long.numberOfLeadingZeros(this)
private fun Long.shl63Mod(m: Long): Long {
    var a = this
    var remShift = 63
    do {
        val shift = min(remShift, a.numLeadingZeroes - 1)
        a = a.shl(shift) % m
        remShift -= shift
    } while (remShift > 0)
    return a
}

fun Long.powMod(exponent: Long, mod: Long): Long {
    if (exponent < 0) error("Inverse not implemented")
    var res = 1L
    var e = exponent
    var b = modulo(mod)
    while (e > 0) {
        if (e and 1 == 1L) res = _mulMod(res, b, mod)
        e = e shr 1
        b = _mulMod(b, b, mod)
    }
    return res
}

infix fun Int.modulo(mod: Int): Int = (this % mod).let { (it shr Int.SIZE_BITS - 1 and mod) + it }
infix fun Long.modulo(mod: Long) = (this % mod).let { (it shr Long.SIZE_BITS - 1 and mod) + it }
