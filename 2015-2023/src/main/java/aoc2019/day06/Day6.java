package aoc2019.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {

    public static List<OrbitSystem> readInput(String input) {
        BufferedReader reader;
        List<OrbitSystem> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();
            while (line != null) {
                String[] orbit = line.split("[)]");
                list.add(new OrbitSystem(orbit[0], orbit[1]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    static Map<String, Set<String>> getOrbitMap(List<OrbitSystem> list) {
        Map<String, Set<String>> orbitMap = new HashMap<>();
        list.forEach(orbitSystem -> {
            String center = orbitSystem.getCenter();
            String satellite = orbitSystem.getSatellite();
            if (orbitMap.containsKey(center)) {
                orbitMap.get(center).add(satellite);
            } else {
                Set<String> satellites = new HashSet<>();
                satellites.add(satellite);
                orbitMap.put(center, satellites);
            }
        });

        return orbitMap;
    }

    static Map<String, Set<String>> getCompleteOrbitMap(List<OrbitSystem> list) {
        Map<String, Set<String>> orbitMap = getOrbitMap(list);
        int satelliteListsTotalLength = getListSizeSum(orbitMap);
        int tempTotalLength = 0;

        while (tempTotalLength != satelliteListsTotalLength) {
            satelliteListsTotalLength = getListSizeSum(orbitMap);

            Map<String, Set<String>> newOrbitMap = orbitMap.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue()), (a, b) -> b));

            for (Map.Entry<String, Set<String>> entry : orbitMap.entrySet()) {
                for (String sat : entry.getValue()) {
                    if (newOrbitMap.containsKey(sat)) {
                        newOrbitMap.get(entry.getKey()).addAll(orbitMap.get(sat));
                    }
                }
            }
            orbitMap = newOrbitMap;
            tempTotalLength = getListSizeSum(orbitMap);
        }

        return orbitMap;
    }

    static int getListSizeSum(Map<String, Set<String>> orbitMap) {
        return orbitMap.values().stream().mapToInt(Set::size).sum();
    }

    static int calculateJumps(Map<String, Set<String>> completeOrbitMap, String inputFile) {
        Map<String, Integer> mapOfCentersWithSantaAndYou = completeOrbitMap.entrySet().stream()
                .filter(entry -> entry.getValue().contains("SAN") && entry.getValue().contains("YOU"))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size(), (a, b) -> b));

        int minSize = 999999;
        String parent = "";

        for (Map.Entry<String, Integer> entry : mapOfCentersWithSantaAndYou.entrySet()) {
            if (entry.getValue() < minSize) {
                minSize = entry.getValue();
                parent = entry.getKey();
            }
        }

        List<OrbitSystem> orbitSystemList = readInput(inputFile);
        return getCount("SAN", parent, orbitSystemList) + getCount("YOU", parent, orbitSystemList) - 2;
    }

    private static int getCount(String satellite, String parent, List<OrbitSystem> orbitSystemList) {
        int santaQnt = 0;
        String tempParent = "";
        do {
            for (OrbitSystem os : orbitSystemList) {
                if (os.getSatellite().equals(satellite)) {
                    tempParent = os.getCenter();
                    break;
                }
            }
            santaQnt++;
            satellite = tempParent;
        } while (!tempParent.equals(parent));

        return santaQnt;
    }
}
