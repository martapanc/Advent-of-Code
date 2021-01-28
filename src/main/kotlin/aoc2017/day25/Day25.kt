package aoc2017.day25

fun computeDiagnosticChecksum(steps: Int = 12919244): Int {
    var currentState = State.A
    var currentIndex = 0
    val machineMap = mutableMapOf(currentIndex to 0)
    repeat(steps) {
        when (currentState) {
            State.A ->
                if (isValueNullOrZero(machineMap, currentIndex)) {
                    machineMap[currentIndex] = 1
                    currentIndex++
                    currentState = State.B
                } else {
                    machineMap[currentIndex] = 0
                    currentIndex--
                    currentState = State.C
                }
            State.B ->
                if (isValueNullOrZero(machineMap, currentIndex)) {
                    machineMap[currentIndex] = 1
                    currentIndex--
                    currentState = State.A
                } else {
                    machineMap[currentIndex] = 1
                    currentIndex++
                    currentState = State.D
                }
            State.C ->
                if (isValueNullOrZero(machineMap, currentIndex)) {
                    machineMap[currentIndex] = 1
                    currentIndex++
                    currentState = State.A
                } else {
                    machineMap[currentIndex] = 0
                    currentIndex--
                    currentState = State.E
                }
            State.D ->
                if (isValueNullOrZero(machineMap, currentIndex)) {
                    machineMap[currentIndex] = 1
                    currentIndex++
                    currentState = State.A
                } else {
                    machineMap[currentIndex] = 0
                    currentIndex++
                    currentState = State.B
                }
            State.E ->
                if (isValueNullOrZero(machineMap, currentIndex)) {
                    machineMap[currentIndex] = 1
                    currentIndex--
                    currentState = State.F
                } else {
                    machineMap[currentIndex] = 1
                    currentIndex--
                    currentState = State.C
                }
            State.F ->
                if (isValueNullOrZero(machineMap, currentIndex)) {
                    machineMap[currentIndex] = 1
                    currentIndex++
                    currentState = State.D
                } else {
                    machineMap[currentIndex] = 1
                    currentIndex++
                    currentState = State.A
                }
        }
    }
    return machineMap.values.count { it == 1 }
}

private fun isValueNullOrZero(machineMap: Map<Int, Int>, currentIndex: Int) =
    machineMap[currentIndex] == null || machineMap[currentIndex] == 0

enum class State { A, B, C, D, E, F }