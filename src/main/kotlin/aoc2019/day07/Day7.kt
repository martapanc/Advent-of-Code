package aoc2019.day07

import aoc2019.day05.Day5.processParameterMode
import aoc2019.day05.Output
import java.util.ArrayList

// PART 2
// * 3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5
// The instruction should finish after reaching 1005, which goes to 99 only if [28] == 0, otherwise it jumps back to 3,27
// The input the latter should receive, after the initial 0, must come from the previous amplifier (e.g. E -> A)
// The input that the current amplifier passes to the following one is after 4 ([27] in this case)
// The amplifier's index, as well as the Amp's memory, should be saved so that the process can be continued after the end of the loop (e.g. 1001,28)
// Eventually, the loop should halt when Amp E reaches 99
object Day7 {
    fun findBestResult(numbers: ArrayList<Int>, phaseSettings: String, withLoop: Boolean): Int {
        var maxResult = 0
        for (ps in PermutationUtil.generatePermutations(phaseSettings)) {
            val phaseSettingsArr = IntArray(phaseSettings.length)
            IntStream.range(0, ps.length)
                .forEach(IntConsumer { i: Int -> phaseSettingsArr[i] = ps[i].toString().toInt() })
            val result = if (withLoop) setupLoopingAmplifiers(numbers, phaseSettingsArr) else setupAmplifiers(
                numbers,
                phaseSettingsArr
            )
            if (result > maxResult) {
                maxResult = result
            }
        }
        return maxResult
    }

    fun setupAmplifiers(numbers: ArrayList<Int>, phaseSettings: IntArray): Int {
        var input = 0
        for (phaseSetting in phaseSettings) {
            input = processInput(numbers, phaseSetting, input, 0).outputValue
        }
        return input
    }

    fun setupLoopingAmplifiers(numbers: ArrayList<Int>?, phaseSettings: IntArray): Int {
        var A = processInput(ArrayList(numbers), phaseSettings[0], 0, 0)
        var B = processInput(ArrayList(numbers), phaseSettings[1], A.outputValue, 0)
        var C = processInput(ArrayList(numbers), phaseSettings[2], B.outputValue, 0)
        var D = processInput(ArrayList(numbers), phaseSettings[3], C.outputValue, 0)
        var E = processInput(ArrayList(numbers), phaseSettings[4], D.outputValue, 0)
        var eOutput = E.outputValue
        var oldEOutput: Int
        do {
            oldEOutput = eOutput
            A = processInput(A.numbers, eOutput, -1, A.lastIndex)
            B = processInput(B.numbers, A.outputValue, -1, B.lastIndex)
            C = processInput(C.numbers, B.outputValue, -1, C.lastIndex)
            D = processInput(D.numbers, C.outputValue, -1, D.lastIndex)
            E = processInput(E.numbers, D.outputValue, -1, E.lastIndex)
            eOutput = E.outputValue
        } while (oldEOutput != eOutput)
        return eOutput
    }

    fun processInput(numbers: ArrayList<Int>, input1: Int, input2: Int, i: Int): LoopAmplifierOutput {
        var i = i
        val outputBuilder = StringBuilder()
        var firstInputUsed = false
        while (i < numbers.size) {
            val opCode = numbers[i]
            if (opCode == 99) {
                break
            }
            var inputValue = input1
            if (firstInputUsed) {
                inputValue = input2
            }
            if (opCode == 3) {
                firstInputUsed = true
            }
            val output: Output = processParameterMode(numbers, i, opCode, inputValue)
            i += output.index
            //Save index and break loop
            if (output.code != "") {
                outputBuilder.append(output.code)
                break
            }
        }

        // When the code finally reaches 99, the value output by IntCode 4 may not be in memory anymore
        // When this happens, an empty output is to be returned which causes an exception
        // However, the last output now corresponds as the first input, which can be returned instead
        return try {
            LoopAmplifierOutput(outputBuilder.toString().toInt(), i, numbers)
        } catch (e: NumberFormatException) {
            LoopAmplifierOutput(input1, i, numbers)
        }
    }
}