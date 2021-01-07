package aoc2019.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day12 {

    public static ArrayList<Point3d> readInput(String input) {
        ArrayList<Point3d> list = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(input));
            String line = reader.readLine();
            while (line != null) {
                String[] tuple = line.replace("<", "").replace("x", "")
                        .replace("y", "").replace("z", "")
                        .replaceAll("=", "").replace(">", "").split(", ");
                list.add(new Point3d(tuple[0], tuple[1], tuple[2]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Point3d> computeMovements(ArrayList<Point3d> list, long steps) {
        while (steps-- > 0) {
            for (Point3d moon : list) {
                ArrayList<Point3d> otherMoons = new ArrayList<>(list);
                otherMoons.remove(moon);

                int xSpeed = moon.getxSpeed();
                int ySpeed = moon.getySpeed();
                int zSpeed = moon.getzSpeed();

                for (Point3d otherMoon : otherMoons) {
                    if (moon.getX() < otherMoon.getX()) {
                        xSpeed++;
                    }
                    if (moon.getX() > otherMoon.getX()) {
                        xSpeed--;
                    }
                    if (moon.getY() < otherMoon.getY()) {
                        ySpeed++;
                    }
                    if (moon.getY() > otherMoon.getY()) {
                        ySpeed--;
                    }
                    if (moon.getZ() < otherMoon.getZ()) {
                        zSpeed++;
                    }
                    if (moon.getZ() > otherMoon.getZ()) {
                        zSpeed--;
                    }
                }
                moon.setSpeed(xSpeed, ySpeed, zSpeed);
            }

            for (Point3d moon : list) {
                moon.setX(moon.getX() + moon.getxSpeed());
                moon.setY(moon.getY() + moon.getySpeed());
                moon.setZ(moon.getZ() + moon.getzSpeed());
            }
        }
        return list;
    }

    static int computeTotalEnergyAfterNSteps(ArrayList<Point3d> list, long steps) {
        return computeMovements(list, steps).stream().mapToInt(moon -> moon.getPotentialEnergy() * moon.getKineticEnergy()).sum();
    }

    static long findXAxisCyclePeriod(ArrayList<Point3d> list) {
        long axisPeriod = 1;
        int[] xAxisInitial = new int[]{list.get(0).getX(), list.get(1).getX(), list.get(2).getX(), list.get(3).getX()};
        ArrayList<Point3d> newList = getNewList(list);

        do {
            axisPeriod++;
            newList = computeMovements(newList, 1);
        } while (xAxisInitial[0] != newList.get(0).getX()
                || xAxisInitial[1] != newList.get(1).getX()
                || xAxisInitial[2] != newList.get(2).getX()
                || xAxisInitial[3] != newList.get(3).getX());
        return axisPeriod;
    }

    static long findYAxisCyclePeriod(ArrayList<Point3d> list) {
        long axisPeriod = 1;
        int[] yAxisInitial = new int[]{list.get(0).getY(), list.get(1).getY(), list.get(2).getY(), list.get(3).getY()};
        ArrayList<Point3d> newList = getNewList(list);

        do {
            axisPeriod++;
            newList = computeMovements(newList, 1);
        } while (yAxisInitial[0] != newList.get(0).getY()
                || yAxisInitial[1] != newList.get(1).getY()
                || yAxisInitial[2] != newList.get(2).getY()
                || yAxisInitial[3] != newList.get(3).getY());
        return axisPeriod;
    }

    static long findZAxisCyclePeriod(ArrayList<Point3d> list) {
        long axisPeriod = 1;
        int[] zAxisInitial = new int[]{list.get(0).getZ(), list.get(1).getZ(), list.get(2).getZ(), list.get(3).getZ()};
        ArrayList<Point3d> newList = getNewList(list);

        do {
            axisPeriod++;
            newList = computeMovements(newList, 1);
        } while (zAxisInitial[0] != newList.get(0).getZ()
                || zAxisInitial[1] != newList.get(1).getZ()
                || zAxisInitial[2] != newList.get(2).getZ()
                || zAxisInitial[3] != newList.get(3).getZ());
        return axisPeriod;
    }

    private static ArrayList<Point3d> getNewList(ArrayList<Point3d> list) {
        ArrayList<Point3d> newList = new ArrayList<>();
        for (Point3d point3d : list) {
            newList.add(new Point3d(point3d.getX(), point3d.getY(), point3d.getZ()));
        }
        return newList;
    }
}
