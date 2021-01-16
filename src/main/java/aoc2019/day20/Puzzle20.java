package aoc2019.day20;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO: optimize
public class Puzzle20 {

    private static char PATH = '.';
    private static String INPUT = "src/main/kotlin/aoc2019/day20/input";
    private static List<String> maze;

    private static Map<Location, Location> portals = new HashMap<>();
    private static Map<String, List<Location>> portalToLocations = new HashMap<>();

    public static void main(String[] args) throws IOException {
        maze = createMaze(INPUT);
        findAndMapHorizontalPortals();
        findAndMapVerticalPortals();
        connectPortalLocations();
        Location startLocation = portalToLocations.get("AA").get(0);
        Location endLocation = portalToLocations.get("ZZ").get(0);
        findShortestPath(startLocation, endLocation);
    }

    private static void findShortestPath(Location startLocation, Location endLocation) {
        Queue<Location> searchLocations = new LinkedList<>();
        HashMap<Integer, Set<Location>> visitedLocations = createVisitedLocations();
        searchLocations.add(startLocation);
        while (!visitedLocations.get(0).contains(endLocation)) {
            Location currentLocation = searchLocations.remove();
            visitedLocations.get(currentLocation.depth).add(currentLocation);
            Set<Location> nextLocations = findNextLocations(currentLocation).stream()
                    .filter(potentialNextLocations -> !visitedLocations.get(potentialNextLocations.depth).contains(potentialNextLocations))
                    .collect(Collectors.toSet());
            searchLocations.addAll(nextLocations);
        }
        System.out.println(visitedLocations.get(0).stream().filter(endLocation::equals).findFirst().get().distance);
    }

    private static HashMap<Integer, Set<Location>> createVisitedLocations() {
        HashMap<Integer, Set<Location>> visitedLocations = new HashMap<>();
        for(int i = 0; i< 150; i++) {
            visitedLocations.put(i, new HashSet<>());
        }
        return visitedLocations;
    }

    private static void printMaze(Location location) {
        for (int y = 0; y < maze.size(); y++) {
            for (int x = 0; x < maze.get(y).length(); x++) {
                if (location != null && location.matches(x, y)) {
                    System.out.print("@");
                } else {
                    System.out.print(maze.get(y).charAt(x));
                }

            }
            System.out.println();
        }

    }

    private static void connectPortalLocations() {
        portalToLocations.values().stream().filter(list -> list.size() == 2).forEach(
                locationList -> {
                    portals.put(locationList.get(0), locationList.get(1));
                    portals.put(locationList.get(1), locationList.get(0));
                }
        );
    }

    private static void findAndMapHorizontalPortals() {
        Pattern pattern = Pattern.compile("([A-Z]{2})[.]");
        for (int y = 0; y < maze.size(); y++) {
            Matcher matcher = pattern.matcher(maze.get(y));
            while (matcher.find()) {
                String portalName = matcher.group(1);
                List<Location> locationList = portalToLocations.getOrDefault(portalName, new ArrayList<>());
                locationList.add(new Location(matcher.end() - 1, y));
                portalToLocations.put(portalName, locationList);
            }
        }

        pattern = Pattern.compile("[.]([A-Z]{2})");
        for (int y = 0; y < maze.size(); y++) {
            Matcher matcher = pattern.matcher(maze.get(y));
            while (matcher.find()) {
                String portalName = matcher.group(1);
                List<Location> locationList = portalToLocations.getOrDefault(portalName, new ArrayList<>());
                locationList.add(new Location(matcher.start(), y));
                portalToLocations.put(portalName, locationList);
            }
        }
    }

    private static void findAndMapVerticalPortals() {
        List<String> turnedMaze = turnMaze();

        Pattern pattern = Pattern.compile("([A-Z]{2})[.]");
        for (int x = 0; x < turnedMaze.size(); x++) {
            Matcher matcher = pattern.matcher(turnedMaze.get(x));
            while (matcher.find()) {
                String portalName = matcher.group(1);
                List<Location> locationList = portalToLocations.getOrDefault(portalName, new ArrayList<>());
                locationList.add(new Location(x, matcher.end() - 1));
                portalToLocations.put(portalName, locationList);
            }
        }

        pattern = Pattern.compile("[.]([A-Z]{2})");
        for (int x = 0; x < turnedMaze.size(); x++) {
            Matcher matcher = pattern.matcher(turnedMaze.get(x));
            while (matcher.find()) {
                String portalName = matcher.group(1);
                List<Location> locationList = portalToLocations.getOrDefault(portalName, new ArrayList<>());
                locationList.add(new Location(x, matcher.start()));
                portalToLocations.put(portalName, locationList);
            }
        }
    }

    private static List<String> turnMaze() {
        List<String> turnedMaze = new ArrayList<>();
        StringBuilder builder;
        for (int x = 0; x < maze.get(0).length(); x++) {
            builder = new StringBuilder();
            for (int y = 0; y < maze.size(); y++) {
                builder.append(maze.get(y).charAt(x));
            }
            turnedMaze.add(builder.toString());
        }
        return turnedMaze;
    }

    private static List<String> createMaze(String url) throws IOException {
        return Files.lines(Paths.get(url)).collect(toList());
    }

    private static Set<Location> findNextLocations(Location location) {
        Location northPath = new Location(location.x, location.y + 1, location.distance + 1, location.depth);
        Location southPath = new Location(location.x, location.y - 1, location.distance + 1, location.depth);
        Location eastPath = new Location(location.x + 1, location.y, location.distance + 1, location.depth);
        Location westPath = new Location(location.x - 1, location.y, location.distance + 1, location.depth);
        Set<Location> paths = Stream.of(northPath, southPath, eastPath, westPath)
                .filter(path -> path.getTile() == PATH)
                .collect(toSet());
        if (location.isPortal()) {
            paths.add(location.travelToOtherSide());
        }

        return paths;
    }

    public static class Location {
        int x;
        int y;
        int distance;
        int depth;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
            this.distance = 0;
            this.depth = 0;
        }

        Location(int x, int y, int distance, int depth) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.depth = depth;
        }

        private Location(Location location, int distance, int depth) {
            this.x = location.x;
            this.y = location.y;
            this.distance = distance;
            this.depth = depth;
        }

        boolean matches(int x, int y) {
            return this.x == x && this.y == y;
        }

        char getTile() {
            return maze.get(y).charAt(x);
        }

        boolean isPortal() {
            if (isOuterPortal() && depth == 0) {
                return false;
            }
            return portals.containsKey(this);
        }

        Location travelToOtherSide() {
            return new Location(portals.get(this), distance + 1, calculateDepth());
        }

        int calculateDepth() {
            if (isOuterPortal()) {
                return depth - 1;
            } else {
                return depth + 1;
            }
        }

        boolean isOuterPortal() {
            return (x == 2 || x == maze.get(0).length() - 3 || y == 2 || y == maze.size() - 3);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Location location = (Location) o;
            return x == location.x &&
                    y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
