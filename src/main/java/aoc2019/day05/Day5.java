package aoc2019.day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day5 {

    // 1: sum values at addresses (1) and (2), stores result at address (3)
    // 2: multiply values at addresses (1) and (2), stores result at address (3)
    // 3: read input and save it at address (1)
    // 4: output value of address (1)
    // 5: (1) != 0 set the pointer to the value of (2)
    // 6: (1) == 0 set the pointer to the value of (2)
    // 7: ((1) < (2) ? store 1 : store 0 ) at value of (3)
    // 8: ((1) == (2) ? store 1 : store 0 ) at value of (3)

    // 0 = position mode
    // 1 = immediate mode
    // Parameters that an instruction writes to will never be in immediate mode.

    // PARAMETER MODE DETAILS:
    // - Immediate mode for 4 (104) should output the value of the parameter (e.g. 104,0 -> out: 0). Otherwise it should output the
    //      value at index 0 (e.g. 3,...,104,0 -> out: 3)
    // - 5,6: 105 - (1) and (2) can be in immediate mode
    // - 7,8: 1107 - only (1) and (2) can be in immediate mode

    // The pointer should increment based on the number of parameters (e.g. 4 for Codes 1 and 2, 2 for Codes 3 and 4),
    // except for 5 and 6 where the pointer is updated as described

    static int processInput(ArrayList<Integer> numbers, int input) {
        int i = 0;
        StringBuilder outputBuilder = new StringBuilder();

        while (i < numbers.size()) {
            int opCode = numbers.get(i);
            if (opCode == 99) {
                break;
            }

            Output output = processParameterMode(numbers, i, opCode, input);
            outputBuilder.append(output.getCode());
            i += output.getIndex();
        }

        return Integer.parseInt(outputBuilder.toString());
    }

    public static Output processParameterMode(ArrayList<Integer> numbers, int index, int opCode, int inputValue) {
        int reducedOpCode = opCode % 100;
        Map<Integer, Integer> parameterModeMap = new HashMap<>();
        parameterModeMap.put(1, (opCode / 100) % 10);
        parameterModeMap.put(2, (opCode / 1000) % 10);
        parameterModeMap.put(3, (opCode / 10000) % 10);

        switch (reducedOpCode) {
            case 1:
            case 2:
                sumAndSubtractParam(numbers, index, reducedOpCode, parameterModeMap);
                return new Output("", 4);
            case 3:
            case 4:
                return inputAndOutputParam(numbers, index, reducedOpCode, parameterModeMap, inputValue);
            case 5:
            case 6:
                return new Output("", jumpIf(numbers, index, reducedOpCode, parameterModeMap));
            case 7:
            case 8:
                lessThanOrEquals(numbers, index, reducedOpCode, parameterModeMap);
                return new Output("", 4);
            default:
                return new Output("err");
        }
    }

    private static void sumAndSubtractParam(ArrayList<Integer> numbers, int index, int opCode, Map<Integer, Integer> map) {
        Integer input1Pos = numbers.get(index + 1);
        Integer input2Pos = numbers.get(index + 2);
        Integer outputPos = numbers.get(index + 3);
        if (opCode == 1) {
            numbers.set(
                    (map.get(3) == 0 ? outputPos : index + 3),
                    numbers.get((map.get(1) == 0 ? input1Pos : index + 1)) +
                            numbers.get((map.get(2) == 0 ? input2Pos : index + 2)));
        } else {
            numbers.set(
                    (map.get(3) == 0 ? outputPos : index + 3),
                    numbers.get((map.get(1) == 0 ? input1Pos : index + 1)) *
                            numbers.get((map.get(2) == 0 ? input2Pos : index + 2)));
        }
    }

    private static Output inputAndOutputParam(ArrayList<Integer> numbers, int index, int opCode, Map<Integer, Integer> map, int inputValue) {
        Integer pos = numbers.get(index + 1);
        if (opCode == 3) {
            numbers.set(pos, getInput(inputValue));
            return new Output("", 2);
        } else {
            return new Output(numbers.get((map.get(1) == 0 ? pos : index + 1)) + "", 2);
        }
    }

    private static int jumpIf(ArrayList<Integer> numbers, int index, int opCode, Map<Integer, Integer> map) {
        Integer input1Pos = numbers.get(index + 1);
        Integer input2Pos = numbers.get(index + 2);
        if (opCode == 5) {
            if (numbers.get(map.get(1) == 0 ? input1Pos : index + 1) != 0) {
                return numbers.get((map.get(2) == 0 ? input2Pos : index + 2)) - index;
            }
        } else {
            if (numbers.get(map.get(1) == 0 ? input1Pos : index + 1) == 0) {
                return numbers.get((map.get(2) == 0 ? input2Pos : index + 2)) - index;
            }
        }

        return 3;
    }

    private static void lessThanOrEquals(ArrayList<Integer> numbers, int index, int opCode, Map<Integer, Integer> map) {
        Integer input1Pos = numbers.get(index + 1);
        Integer input2Pos = numbers.get(index + 2);
        Integer outputPos = numbers.get(index + 3);
        if (opCode == 7) {
            if (numbers.get(map.get(1) == 0 ? input1Pos : index + 1) <
                    numbers.get(map.get(2) == 0 ? input2Pos : index + 2)) {
                numbers.set(outputPos, 1);
            } else {
                numbers.set(outputPos, 0);
            }
        } else {
            if (numbers.get(map.get(1) == 0 ? input1Pos : index + 1).equals(
                    numbers.get(map.get(2) == 0 ? input2Pos : index + 2))
            ) {
                numbers.set(outputPos, 1);
            } else {
                numbers.set(outputPos, 0);
            }
        }
    }

    private static int getInput(int input) {
        return input;
    }
}
