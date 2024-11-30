package aoc2019.day10;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class Day10 {

    private static final int MAX = 200;

    public static List<SpacePoint> readInput(String input) {
        List<SpacePoint> list = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();
            int y = 0;
            while (line != null) {
                int x = 0;
                char[] lineContent = line.trim().toCharArray();
                for (char c : lineContent) {
                    list.add(new SpacePoint(new Point(x++, y), SpaceItem.getSpaceItemFromId(c)));
                }
                line = reader.readLine();
                y++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    static List<SpacePoint> listAsteroids(List<SpacePoint> all) {
        List<SpacePoint> asteroids = new ArrayList<>();
        for (SpacePoint sp : all) {
            if (sp.getSpaceItem() == SpaceItem.ASTEROID) {
                asteroids.add(sp);
            }
        }
        return asteroids;
    }

    // For each asteroid, find the numbers of unique lines between it and the others (if two other asteroids are aligned, the line is added only once)
    static Map<SpacePoint, List<Line>> findAsteroids(List<SpacePoint> asteroids) {

        Map<SpacePoint, List<Line>> lineMap = new HashMap<>();
        for (SpacePoint asteroid : asteroids) {
            Set<Line> topLinesSet = new HashSet<>();
            Set<Line> bottomLinesSet = new HashSet<>();
            for (SpacePoint other : asteroids) {
                Line lineFromTwoPoints = Line.getLineFromTwoPoints(asteroid.getCoordinate(), other.getCoordinate());
                if (lineFromTwoPoints != null) {
                    if (other.getCoordinate().getY() < asteroid.getCoordinate().getY()) { // Top
                        topLinesSet.add(lineFromTwoPoints);
                    } else if (other.getCoordinate().getY() > asteroid.getCoordinate().getY()) { // Bottom
                        bottomLinesSet.add(lineFromTwoPoints);
                    } else { // Same row
                        // If the current point is on the left of the asteroid, add it to the top set; otherwise, add it to the bottom set
                        if (other.getCoordinate().getX() < asteroid.getCoordinate().getX()) {
                            topLinesSet.add(lineFromTwoPoints);
                        } else {
                            bottomLinesSet.add(lineFromTwoPoints);
                        }
                    }
                }
            }

            List<Line> lines = new ArrayList<>();
            lines.addAll(topLinesSet);
            lines.addAll(bottomLinesSet);
            lineMap.put(asteroid, lines);
        }
        return lineMap;
    }

    static int findLocationsOfBestAsteroid(Map<SpacePoint, List<Line>> map) {
        return map.values().stream().mapToInt(List::size).max().orElse(0);
    }

    static SpacePoint findBestAsteroid(Map<SpacePoint, List<Line>> map) {
        int max = 0;
        SpacePoint bestAsteroid = new SpacePoint(new Point(0, 0), SpaceItem.VOID);
        for (Map.Entry<SpacePoint, List<Line>> entry : map.entrySet()) {
            if (entry.getValue().size() > max) {
                max = entry.getValue().size();
                bestAsteroid = entry.getKey();
            }
        }

        return bestAsteroid;
    }

    static Map<SpacePoint, Double> getAngularCoefficientMap(Point origin, List<SpacePoint> asteroids) {
        Map<SpacePoint, Double> angularCoefficientMap = new HashMap<>();
        List<SpacePoint> q1List = new ArrayList<>();
        List<SpacePoint> q2List = new ArrayList<>();
        List<SpacePoint> q3List = new ArrayList<>();
        List<SpacePoint> q4List = new ArrayList<>();

        for (SpacePoint asteroid : asteroids) {
            double x1 = asteroid.getCoordinate().getX();
            double y1 = asteroid.getCoordinate().getY();
            double x0 = origin.getX();
            double y0 = origin.getY();

            Line lineFromTwoPoints = Line.getLineFromTwoPoints(origin, asteroid.getCoordinate());
            if (lineFromTwoPoints != null) {

                double angularCoefficient = lineFromTwoPoints.getAngularCoefficient();

                asteroid.setRelativeAngularCoeff(-angularCoefficient);
                if (angularCoefficient == 9999 || angularCoefficient == 0) {
                    asteroid.setRelativeAngularCoeff(angularCoefficient);
                }

                if (y1 < y0 && x1 >= x0) {
                    asteroid.setQuadrant(Quadrant.ONE);
                    q1List.add(asteroid);
                }
                if (y1 >= y0 && x1 > x0) {
                    asteroid.setQuadrant(Quadrant.TWO);
                    q2List.add(asteroid);
                }
                if (y1 > y0 && x1 <= x0) {
                    asteroid.setQuadrant(Quadrant.THREE);
                    q3List.add(asteroid);
                }
                if (y1 <= y0 && x1 < x0) {
                    asteroid.setQuadrant(Quadrant.FOUR);
                    q4List.add(asteroid);
                }

                angularCoefficientMap.put(asteroid, -angularCoefficient);
            }
        }

        q1List.sort(Collections.reverseOrder());
        q2List.sort(Collections.reverseOrder());
        q3List.sort(Collections.reverseOrder());
        q4List.sort(Collections.reverseOrder());

        int i = 0;

        do {
            double prevCoeff = 99999999;
            for (SpacePoint sp : q1List) {
                if (!sp.isDestroyed() && prevCoeff != sp.getRelativeAngularCoeff()) {
                    i++;
                    if (i == MAX) {
                        System.out.println(sp);
                    }
                    for (SpacePoint s : q1List) {
                        if (!s.equals(sp) && s.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff()) {
                            SpacePoint sameCoeff = q1List.stream()
                                    .filter(a -> a.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff())
                                    .max(Comparator.comparingInt(SpacePoint::getY))
                                    .orElse(null);
                            q1List.get(q1List.indexOf(sameCoeff)).setDestroyed(true);
                            prevCoeff = sameCoeff.getRelativeAngularCoeff();
                            break;
                        }
                    }
                }
            }
            for (SpacePoint sp : q2List) {
                if (!sp.isDestroyed() && prevCoeff != sp.getRelativeAngularCoeff()) {
                    i++;
                    if (i == MAX) {
                        System.out.println(sp);
                    }
                    for (SpacePoint s : q2List) {
                        if (!s.equals(sp) && s.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff()) {
                            SpacePoint sameCoeff = q2List.stream()
                                    .filter(a -> a.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff())
                                    .min(Comparator.comparingInt(SpacePoint::getY))
                                    .orElse(null);
                            q2List.get(q2List.indexOf(sameCoeff)).setDestroyed(true);
                            prevCoeff = sameCoeff.getRelativeAngularCoeff();
                            break;
                        }
                    }
                }
            }
            for (SpacePoint sp : q3List) {
                if (!sp.isDestroyed() && prevCoeff != sp.getRelativeAngularCoeff()) {
                    i++;
                    if (i == MAX) {
                        System.out.println(sp);
                    }
                    for (SpacePoint s : q3List) {
                        if (!s.equals(sp) && s.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff()) {
                            SpacePoint sameCoeff = q3List.stream()
                                    .filter(a -> a.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff())
                                    .max(Comparator.comparingInt(SpacePoint::getY))
                                    .orElse(null);
                            q3List.get(q3List.indexOf(sameCoeff)).setDestroyed(true);
                            prevCoeff = sameCoeff.getRelativeAngularCoeff();
                            break;
                        }
                    }
                }
            }
            for (SpacePoint sp : q4List) {
                if (!sp.isDestroyed() && prevCoeff != sp.getRelativeAngularCoeff()) {
                    i++;
                    if (i == MAX) {
                        System.out.println(sp);
                    }
                    for (SpacePoint s : q4List) {
                        if (!s.equals(sp) && s.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff()) {
                            SpacePoint sameCoeff = q4List.stream()
                                    .filter(a -> a.getRelativeAngularCoeff() == sp.getRelativeAngularCoeff())
                                    .min(Comparator.comparingInt(SpacePoint::getY))
                                    .orElse(null);
                            q4List.get(q4List.indexOf(sameCoeff)).setDestroyed(true);
                            prevCoeff = sameCoeff.getRelativeAngularCoeff();
                            break;
                        }
                    }
                }
            }
        } while (i < MAX);

        return angularCoefficientMap;
    }
}
