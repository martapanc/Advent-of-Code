package aoc2019.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    static int calculateFuelForModel(int mass) {
        return (mass / 3) - 2;
    }

    static int calculateTotalFuel(String input, boolean recursive) {
        List<Integer> list = readInput(input);
        int sum = 0;

        for (Integer i : list) {
            sum += (recursive ? calculateRecursiveFuel(i) : calculateFuelForModel(i));
        }

        return sum;
    }

    static int calculateRecursiveFuel(int mass) {
        int accumulator = 0;
        int fuel = calculateFuelForModel(mass);

        do {
            accumulator += fuel;
            fuel = calculateFuelForModel(fuel);
        } while (fuel > 0);

        return accumulator;
    }

    public static List<Integer> readInput(String input) {
        List<Integer> integerList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();
            while (line != null) {
                integerList.add(Integer.parseInt(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return integerList;
    }
}
