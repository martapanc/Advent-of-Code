package aoc2019.commons

import aoc2019.commons.IntCodeProgram.Status.*
import java.util.ArrayDeque
import kotlin.math.*

class IntCodeProgram(program: List<Long>) {

    companion object {
        const val MODE_POSITION = 0
        const val MODE_IMMEDIATE = 1
        const val MODE_RELATIVE = 2

        const val OPCODE_ADD = 1
        const val OPCODE_MULTIPLY = 2
        const val OPCODE_IN = 3
        const val OPCODE_OUT = 4
        const val OPCODE_TRUE_JUMP = 5
        const val OPCODE_FALSE_JUMP = 6
        const val OPCODE_LT = 7
        const val OPCODE_EQ = 8
        const val OPCODE_ADJUST_REL_BASE = 9
        const val OPCODE_HALT = 99

    }

    data class UnknownOpcode(val opCode: Int): Exception()
    class CannotWriteToImmediate: Exception()
    data class UnknownMode(val mode: Int): Exception()

    sealed class Status {
        object OK: Status()
        object Waiting: Status()
        object Halted: Status()
        data class Error(val exception: Exception): Status()
    }

    class Memory(program: List<Long>) {
        var arr = program.toLongArray()
        operator fun get(i: Long) = if(i >= arr.size) 0 else arr[i.toInt()]
        operator fun set(i: Long, v: Long) {
            if(i >= arr.size) {
                if(i > Int.MAX_VALUE shr 1) throw OutOfMemoryError()
                arr = arr.copyOf(max(i.toInt() + 1, arr.size + arr.size.shr(1)))
            }
            arr[i.toInt()] = v
        }

        fun clone() = Memory(arr.asList())
    }

    var mem = Memory(program)

    var status: Status = OK
    private val isWaiting get() = status is Waiting
    var ip = 0L
    var rb = 0L // relative base

    private val inputQueue = ArrayDeque<Long>()
    fun input(value: Long) {
        inputQueue.add(value)
    }
    fun input(value: Int) = input(value.toLong())
    val output = ArrayList<Long>()

    operator fun get(code: Long, offset: Int) = run {
        val i = ip + offset
        val mode = getMode(code, offset)

        when (mode) {
            MODE_POSITION -> mem[mem[i]]
            MODE_IMMEDIATE -> mem[i]
            MODE_RELATIVE -> mem[rb + mem[i]]
            else -> throw UnknownMode(mode)
        }
    }

    operator fun set(code: Long, offset: Int, v: Long) {
        val i = ip + offset

        when (val mode = getMode(code, offset)) {
            MODE_POSITION -> mem[mem[i]] = v
            MODE_IMMEDIATE -> throw CannotWriteToImmediate()
            MODE_RELATIVE -> mem[rb + mem[i]] = v
            else -> throw UnknownMode(mode)
        }
    }

    private fun getMode(code: Long, paramIdx: Int) = (code / longArrayOf(1, 10, 100, 1000, 10000)[paramIdx + 1] % 10).toInt()

    private fun _step(): Status {
        try {
            val code = mem[ip]

            val op = (code % 100).toInt()

            when (op) {
                OPCODE_ADD -> {
                    this[code, 3] = this[code, 1] + this[code, 2]
                    ip += 4
                }
                OPCODE_MULTIPLY -> {
                    this[code, 3] = this[code, 1] * this[code, 2]
                    ip += 4
                }
                OPCODE_IN -> {
                    if(inputQueue.isEmpty()) return Waiting
                    this[code, 1] = inputQueue.remove()
                    ip += 2
                }
                OPCODE_OUT -> {
                    output.add(this[code, 1])
                    ip += 2
                }
                OPCODE_TRUE_JUMP -> {
                    if(this[code, 1] != 0L) ip = this[code, 2]
                    else ip += 3
                }
                OPCODE_FALSE_JUMP -> {
                    if(this[code, 1] == 0L) ip = this[code, 2]
                    else ip += 3
                }
                OPCODE_LT -> {
                    this[code, 3] = (this[code, 1] < this[code, 2]).toLong()
                    ip += 4
                }
                OPCODE_EQ -> {
                    this[code, 3] = (this[code, 1] == this[code, 2]).toLong()
                    ip += 4
                }
                OPCODE_ADJUST_REL_BASE -> {
                    rb += this[code, 1]
                    ip += 2
                }
                OPCODE_HALT -> return Halted
                else -> throw UnknownOpcode(op)
            }
            return OK
        } catch (e: Exception) {
            return Error(e)
        }
    }

    private fun step() = _step().also { status = it }

    fun execute() {
        while(step() == OK) {}
    }

    fun clone(): IntCodeProgram {
        val new = IntCodeProgram(emptyList())
        new.mem = mem.clone()
        new.status = status
        new.ip = ip
        new.rb = rb
        new.inputQueue.addAll(inputQueue)
        new.output.addAll(output)

        return new
    }

    fun inputAscii(string: String) {
        for(char in string) input(char.toLong())
        input('\n'.toLong())
    }
    fun inputAscii(strings: Iterable<String>) { for(string in strings) inputAscii(string) }

    fun outputToAscii() = output.let { o -> String(CharArray(o.size) { o[it].toChar() }) }

    fun runAsConsole() {
        while(true) {
            do {
                if(output.isNotEmpty()) {
                    print(outputToAscii())
                    output.clear()
                }
            } while (step() == OK)
            if(isWaiting) inputAscii(readLine()!!)
            else break
        }
    }
}