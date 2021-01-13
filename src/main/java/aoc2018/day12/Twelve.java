package aoc2018.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Twelve {

    static char[] readInput(String inputFile) {

        char[] chars = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            chars = line.toCharArray();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chars;
    }

    static Map<String, String> read_input_rules(String inputRulesFile) {

        Map<String, String> ruleMap = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputRulesFile));
            String line = reader.readLine();
            while (line != null) {
                String[] rule = line.split(" => ");
                ruleMap.put(rule[0], rule[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ruleMap;
    }

    public static long findPotsWithPlantsAfterGenerations(String inputFile, String inputFileRules, long targetGens) {

        final int limitForPerformance = 300;

        char[] input = readInput(inputFile);
        Map<String, String> rules = read_input_rules(inputFileRules);

        PlantArray plants = new PlantArray(input);
        PlantArray newGen = null;

        int totShifts = plants.getShift();
        int generations = 0;
        long loopLimit = (targetGens < limitForPerformance ? targetGens : limitForPerformance);

        while (generations < loopLimit) {

            newGen = new PlantArray(plants);
            for (int i = 2; i < plants.size() - 2; i++) {
                String pattern = plants.getNeighborsOfPlant(i);
                if (rules.containsKey(pattern)) {
                    newGen.setPlantAt(i, rules.get(pattern).charAt(0));
                }
            }
//            newGen.printAsAString();
            plants = new PlantArray(newGen.getArray());
            totShifts += plants.getShift();
            generations += 1;
        }

        long currSum = calcSumOfGen(Objects.requireNonNull(newGen), totShifts);
        long penSum = calcSumOfGen(newGen, totShifts+1);

        if (loopLimit == targetGens) {
            return currSum;
        }
        return currSum + (targetGens - limitForPerformance) * (currSum - penSum);
    }

    public static long calcSumOfGen(PlantArray gen, int shifts) {

        List<Character> result = new ArrayList<>();

        for (long i = shifts; i < gen.size(); i++) {
            result.add(gen.getChar((int) i));
        }

        long sum = IntStream.range(0, result.size()).filter(i -> result.get(i) == '#').sum();

        for (long i = shifts; i > 0; i--) {
            if (gen.getArray()[(int) i] == '#') {
                sum -= shifts - i;
            }
        }
        return sum;
    }
}
