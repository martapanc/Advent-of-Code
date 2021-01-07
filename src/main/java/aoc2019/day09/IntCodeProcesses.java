package aoc2019.day09;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.IntStream;

public class IntCodeProcesses {

    // IntCodes 1 and 2
    static void sumAndSubtractParam(ArrayList<Long> numbers, int index, int opCode, int relativeBase, Map<Integer, Integer> map) {
        int input1Pos = getParamMode(1, index, relativeBase, numbers, map);
        int input2Pos = getParamMode(2, index, relativeBase, numbers, map);
        int outputPos = getParamMode(3, index, relativeBase, numbers, map);

        addMemoryIfNeeded(numbers, Math.max(Math.max(input1Pos, input2Pos), outputPos));

        if (opCode == 1) {
            numbers.set(outputPos, numbers.get(input1Pos) + numbers.get(input2Pos));
        } else {
            numbers.set(outputPos, numbers.get(input1Pos) * numbers.get(input2Pos));
        }
    }

    // IntCodes 3 and 4
    static Output inputAndOutputParam(ArrayList<Long> numbers, int index, int opCode, int relativeBase, Map<Integer, Integer> map, int inputValue) {
        int outputPos = getParamMode(1, index, relativeBase, numbers, map);

        addMemoryIfNeeded(numbers, outputPos);

        if (opCode == 3) {
            // If 3 is in position mode, the input is stored at the position of the parameter
            // It it's in relative mode, the position is the outputPos (parameter value + relative base)
            int outputIndex = map.get(1) == 2 ? outputPos : Math.toIntExact(numbers.get(index + 1));

            numbers.set(outputIndex, getInput(inputValue));
            return new Output("", 2);
        } else {
            return new Output(numbers.get(outputPos) + "", 2);
        }
    }

    // IntCodes 5 and 6
    static long jumpIf(ArrayList<Long> numbers, int index, int opCode, int relativeBase, Map<Integer, Integer> map) {
        int input1Pos = getParamMode(1, index, relativeBase, numbers, map);
        int input2Pos = getParamMode(2, index, relativeBase, numbers, map);

        addMemoryIfNeeded(numbers, Math.max(input1Pos, input2Pos));

        if (opCode == 5) {
            if (numbers.get(input1Pos) != 0) {
                return numbers.get(input2Pos) - index;
            }
        } else {
            if (numbers.get(input1Pos) == 0) {
                return numbers.get(input2Pos) - index;
            }
        }

        return 3;
    }

    // IntCodes 7 and 8
    static void lessThanOrEquals(ArrayList<Long> numbers, int index, int opCode, int relativeBase, Map<Integer, Integer> map) {
        int input1Pos = getParamMode(1, index, relativeBase, numbers, map);
        int input2Pos = getParamMode(2, index, relativeBase, numbers, map);
        int outputPos = getParamMode(3, index, relativeBase, numbers, map);

        addMemoryIfNeeded(numbers, Math.max(Math.max(input1Pos, input2Pos), outputPos));
        if (opCode == 7) {
            numbers.set(outputPos, numbers.get(input1Pos) < numbers.get(input2Pos) ? 1L : 0L);
        } else {
            numbers.set(outputPos, numbers.get(input1Pos).equals(numbers.get(input2Pos)) ? 1L : 0L);
        }
    }

    // IntCode 9
    static int updateRelativeBase(ArrayList<Long> numbers, int index, int relativeBase, Map<Integer, Integer> map) {
        int outputPos = getParamMode(1, index, relativeBase, numbers, map);
        addMemoryIfNeeded(numbers, outputPos);
        relativeBase += numbers.get(outputPos);

        return relativeBase;
    }

    private static int getParamMode(int paramNum, int index, int relativeBase, ArrayList<Long> numbers, Map<Integer, Integer> map) {
        switch (map.get(paramNum)) {
            case 0:
                return Math.toIntExact(numbers.get(index + paramNum));
            case 1:
                return index + paramNum;
            default:
                return Math.toIntExact(numbers.get(index + paramNum)) + relativeBase;
        }
    }

    private static void addMemoryIfNeeded(ArrayList<Long> numbers, int maxIndex) {
        if (maxIndex >= numbers.size()) {
            IntStream.rangeClosed(numbers.size(), maxIndex).forEach(i -> numbers.add(i, 0L));
        }
    }

    private static long getInput(int input) {
        return input;
    }
}
