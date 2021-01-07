package aoc2019.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day14 {

    private static final String FUEL = "FUEL";
    private static final String ORE = "ORE";

    public static Map<Chemical, List<Chemical>> readInput(String input) {
        Map<Chemical, List<Chemical>> chemicalListMap = new HashMap<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();
            while (line != null) {
                String[] reaction = line.split(" => ");
                String[] consumed = reaction[0].split(", ");
                List<Chemical> consumedChemicals = Arrays.stream(consumed)
                        .map(consumedChemical -> consumedChemical.split(" "))
                        .map(c -> new Chemical(Integer.parseInt(c[0]), c[1]))
                        .collect(Collectors.toList());

                String[] produced = reaction[1].split(" ");
                chemicalListMap.put(new Chemical(Integer.parseInt(produced[0]), produced[1]), consumedChemicals);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chemicalListMap;
    }

    static long computeChemicals(Map<Chemical, List<Chemical>> map) {
        return computeOreNeededFor1FuelProduction(map.get(new Chemical(1, FUEL)), map, new ArrayList<>()).getOreNeeded();
    }

    static long computeMaxFuel(Map<Chemical, List<Chemical>> map, long oreNum) {
        List<Chemical> chemicalList = map.get(new Chemical(1, FUEL));
        List<Chemical> storage = new ArrayList<>();
        long oreTotal = 0;
        long fuelProduced = 0;

        while (oreTotal < oreNum) {
            FuelProduction fuelProduction = computeOreNeededFor1FuelProduction(chemicalList, map, storage);

            oreTotal += fuelProduction.getOreNeeded();
            storage = fuelProduction.getWaste();

            fuelProduced++;
            chemicalList = map.get(new Chemical(1, FUEL));
        }
        return fuelProduced - 1;
    }

    private static FuelProduction computeOreNeededFor1FuelProduction(List<Chemical> chemicalList, Map<Chemical, List<Chemical>> map, List<Chemical> storage) {
        long oreTotal = 0;
        while (true) {
            List<Chemical> neededChemicals = new ArrayList<>();

            for (Chemical chemical : chemicalList) {
                Map.Entry<Chemical, List<Chemical>> producerAndList = findProducersOfChemical(chemical.getName(), map);
                Chemical producer = producerAndList.getKey();
                Chemical chemInStorage = findChemicalInList(producer.getName(), storage);

                chemical = checkExistingWaste(storage, chemical, chemInStorage);

                int remainder;
                int multiplier = 1;

                // If the produced quantity is larger than needed, the waste is their difference
                if (chemical.getQuantity() < producer.getQuantity()) {
                    remainder = producer.getQuantity() - chemical.getQuantity();
                    // Case when previous waste can be used to produce the chemical
                    if (chemical.getQuantity() == 0) {
                        multiplier = 0;
                    }
                } else {
                    multiplier = (int) Math.ceil((double) chemical.getQuantity() / producer.getQuantity());
                    remainder = multiplier * producer.getQuantity() - chemical.getQuantity();
                }

                addWasteToStorage(storage, chemical, producer, remainder);
                oreTotal = updateProducerChemicals(neededChemicals, oreTotal, producerAndList, multiplier);
            }
            chemicalList = new ArrayList<>(neededChemicals);
            if (neededChemicals.isEmpty()) {
                break;
            }
        }

        return new FuelProduction(oreTotal, storage);
    }

    private static Chemical checkExistingWaste(List<Chemical> storage, Chemical chemical, Chemical chemInStorage) {
        if (chemInStorage != null) {
            // If the stored waste is larger than the needed amount, the chemical can be produced already
            if (chemInStorage.getQuantity() > chemical.getQuantity()) {
                storage.add(new Chemical(chemInStorage.getQuantity() - chemical.getQuantity(), chemical.getName()));
                chemical = new Chemical(0, chemical.getName());
                storage.remove(chemInStorage);
            } else {
                chemical = new Chemical(chemical.getQuantity() - chemInStorage.getQuantity(), chemical.getName());
                storage.remove(chemInStorage);
            }
        }
        return chemical;
    }

    private static void addWasteToStorage(List<Chemical> storage, Chemical chemical, Chemical producer, int remainder) {
        // If there is waste, add it to storage, or update existing waste's quantity
        if (remainder != 0 && chemical.getQuantity() != 0) {
            Chemical chem = findChemicalInList(producer.getName(), storage);
            if (chem != null) {
                storage.add(new Chemical(chem.getQuantity() + remainder, producer.getName()));
                storage.remove(chem);
            } else {
                storage.add(new Chemical(remainder, producer.getName()));
            }
        }
    }

    private static long updateProducerChemicals(List<Chemical> neededChemicals, long oreTotal, Map.Entry<Chemical, List<Chemical>> producerAndList, int multiplier) {
        for (Chemical chem : producerAndList.getValue()) {
            if (chem.getName().equals(ORE)) {
                oreTotal += chem.getQuantity() * multiplier;
            } else {
                neededChemicals.add(new Chemical(chem.getQuantity() * multiplier, chem.getName()));
            }
        }
        return oreTotal;
    }

    private static Chemical findChemicalInList(String name, List<Chemical> chemicals) {
        return chemicals.stream()
                .filter(carnet -> carnet.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static Map.Entry<Chemical, List<Chemical>> findProducersOfChemical(String name, Map<Chemical, List<Chemical>> map) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
