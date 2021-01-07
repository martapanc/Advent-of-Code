package aoc2019.day07;

import aoc2019.day05.Output;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static aoc2019.day05.Day5.processParameterMode;
import static aoc2019.day07.PermutationUtil.generatePermutations;

// PART 2
// * 3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5
// The instruction should finish after reaching 1005, which goes to 99 only if [28] == 0, otherwise it jumps back to 3,27
// The input the latter should receive, after the initial 0, must come from the previous amplifier (e.g. E -> A)
// The input that the current amplifier passes to the following one is after 4 ([27] in this case)
// The amplifier's index, as well as the Amp's memory, should be saved so that the process can be continued after the end of the loop (e.g. 1001,28)
// Eventually, the loop should halt when Amp E reaches 99

public class Day7 {

    static int findBestResult(ArrayList<Integer> numbers, String phaseSettings, boolean withLoop) {
        int maxResult = 0;
        for (String ps : generatePermutations(phaseSettings)) {

            int[] phaseSettingsArr = new int[phaseSettings.length()];
            IntStream.range(0, ps.length())
                    .forEach(i -> phaseSettingsArr[i] = Integer.parseInt(String.valueOf(ps.charAt(i))));

            int result = withLoop ? setupLoopingAmplifiers(numbers, phaseSettingsArr) : setupAmplifiers(numbers, phaseSettingsArr);
            if (result > maxResult) {
                maxResult = result;
            }
        }
        return maxResult;
    }

    static int setupAmplifiers(ArrayList<Integer> numbers, int[] phaseSettings) {
        int input = 0;
        for (int phaseSetting : phaseSettings) {
            input = processInput(numbers, phaseSetting, input, 0).getOutputValue();
        }
        return input;
    }

    static int setupLoopingAmplifiers(ArrayList<Integer> numbers, int[] phaseSettings) {
        LoopAmplifierOutput A = processInput(new ArrayList<>(numbers), phaseSettings[0], 0, 0);
        LoopAmplifierOutput B = processInput(new ArrayList<>(numbers), phaseSettings[1], A.getOutputValue(), 0);
        LoopAmplifierOutput C = processInput(new ArrayList<>(numbers), phaseSettings[2], B.getOutputValue(), 0);
        LoopAmplifierOutput D = processInput(new ArrayList<>(numbers), phaseSettings[3], C.getOutputValue(), 0);
        LoopAmplifierOutput E = processInput(new ArrayList<>(numbers), phaseSettings[4], D.getOutputValue(), 0);

        int eOutput = E.getOutputValue();
        int oldEOutput;

        do {
            oldEOutput = eOutput;
            A = processInput(A.getNumbers(), eOutput, -1, A.getLastIndex());
            B = processInput(B.getNumbers(), A.getOutputValue(), -1, B.getLastIndex());
            C = processInput(C.getNumbers(), B.getOutputValue(), -1, C.getLastIndex());
            D = processInput(D.getNumbers(), C.getOutputValue(), -1, D.getLastIndex());
            E = processInput(E.getNumbers(), D.getOutputValue(), -1, E.getLastIndex());
            eOutput = E.getOutputValue();
        } while (oldEOutput != eOutput);

        return eOutput;
    }

    static LoopAmplifierOutput processInput(ArrayList<Integer> numbers, int input1, int input2, int i) {
        StringBuilder outputBuilder = new StringBuilder();
        boolean firstInputUsed = false;

        while (i < numbers.size()) {
            int opCode = numbers.get(i);
            if (opCode == 99) {
                break;
            }

            int inputValue = input1;
            if (firstInputUsed) {
                inputValue = input2;
            }
            if (opCode == 3) {
                firstInputUsed = true;
            }

            Output output = processParameterMode(numbers, i, opCode, inputValue);

            i += output.getIndex();
            //Save index and break loop
            if (!output.getCode().equals("")) {
                outputBuilder.append(output.getCode());
                break;
            }
        }

        // When the code finally reaches 99, the value output by IntCode 4 may not be in memory anymore
        // When this happens, an empty output is to be returned which causes an exception
        // However, the last output now corresponds as the first input, which can be returned instead
        try {
            return new LoopAmplifierOutput(Integer.parseInt(outputBuilder.toString()), i, numbers);
        } catch (NumberFormatException e) {
            return new LoopAmplifierOutput(input1, i, numbers);
        }
    }
}
