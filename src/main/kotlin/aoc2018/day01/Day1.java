package aoc2018.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day1 {

    public static int frequencyCalculator(String s) {
        return IntStream.of(Arrays.stream(s.split(", ")).mapToInt(Integer::parseInt).toArray()).sum();
    }

    public static int findFirstRepeatedFrequency(String s) {
        int sum = 0;
        List<Integer> frequencies = new ArrayList<>();
        frequencies.add(sum);

        int[] values = Arrays.stream(s.split(", ")).mapToInt(Integer::parseInt).toArray();

        while (true)
            for (int val : values) {
                sum += val;
                if (frequencies.contains(sum)) {
                    return sum;
                } else {
                    frequencies.add(sum);
                }
            }
    }

    public static String readInput(String inputRulesFile) {

        String formattedInput = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputRulesFile));
            String line = reader.readLine();
            while (line != null) {
                formattedInput += line + ", ";
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formattedInput;
    }
}
