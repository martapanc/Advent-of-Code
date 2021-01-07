package aoc2019.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static aoc2019.day09.IntCodeProcesses.*;


public class Day9 {

    public static ArrayList<Long> readInput(String input) {
        ArrayList<Long> list = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();
            while (line != null) {
                Arrays.stream(line.split(",")).map(Long::parseLong).forEachOrdered(list::add);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    static long processInput(ArrayList<Long> numbers, int input) {
        int i = 0;
        int relativeBase = 0;
        StringBuilder outputBuilder = new StringBuilder();

        while (i < numbers.size()) {
            int opCode = Math.toIntExact(numbers.get(i));
            if (opCode == 99) {
                break;
            }

            Output output = processParameterMode(numbers, i, opCode, input, relativeBase);
            outputBuilder.append(output.getCode());

            if (output.getRelativeBase() != 0) {
                relativeBase = output.getRelativeBase();
            }
            i += output.getIndex();
        }

        return Long.parseLong(outputBuilder.toString());
    }

    public static Output processParameterMode(ArrayList<Long> numbers, int index, int opCode, int inputValue, int relativeBase) {
        int reducedOpCode = opCode % 100;
        Map<Integer, Integer> parameterModeMap = new HashMap<>();
        parameterModeMap.put(1, (opCode / 100) % 10);
        parameterModeMap.put(2, (opCode / 1000) % 10);
        parameterModeMap.put(3, (opCode / 10000) % 10);

        switch (reducedOpCode) {
            case 1:
            case 2:
                sumAndSubtractParam(numbers, index, reducedOpCode, relativeBase, parameterModeMap);
                return new Output("", 4);
            case 3:
            case 4:
                return inputAndOutputParam(numbers, index, reducedOpCode, relativeBase, parameterModeMap, inputValue);
            case 5:
            case 6:
                return new Output("", jumpIf(numbers, index, reducedOpCode, relativeBase, parameterModeMap));
            case 7:
            case 8:
                lessThanOrEquals(numbers, index, reducedOpCode, relativeBase, parameterModeMap);
                return new Output("", 4);
            case 9:
                return new Output("", 2, updateRelativeBase(numbers, index, relativeBase, parameterModeMap));
            default:
                return new Output("err");
        }
    }
}
