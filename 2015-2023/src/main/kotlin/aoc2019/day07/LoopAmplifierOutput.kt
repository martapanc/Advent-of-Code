package aoc2019.day07

import java.util.ArrayList

class LoopAmplifierOutput internal constructor(val outputValue: Int, val lastIndex: Int, val numbers: ArrayList<Int>) {
    override fun toString(): String {
        return "{" +
                "outputValue=" + outputValue +
                ", lastIndex=" + lastIndex +
                ", currentNumbers=" + numbers +
                '}'
    }
}