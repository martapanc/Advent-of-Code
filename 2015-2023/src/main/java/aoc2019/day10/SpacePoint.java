package aoc2019.day10;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpacePoint implements Comparable<SpacePoint> {

    private Point coordinate;
    private SpaceItem spaceItem;
    private Quadrant quadrant = Quadrant.ONE;
    private double relativeAngularCoeff = -1;
    private boolean destroyed = false;

    public SpacePoint(Point coordinate, SpaceItem spaceItem) {
        this.coordinate = coordinate;
        this.spaceItem = spaceItem;
    }

    @Override
    public String toString() {
        return "{(" + coordinate.x + "," + coordinate.y + ")[" + quadrant.getId() + "], " + relativeAngularCoeff + "}";
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public int getX() {
        return coordinate.x;
    }

    public int getY() {
        return coordinate.y;
    }

    public SpaceItem getSpaceItem() {
        return spaceItem;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        this.quadrant = quadrant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpacePoint that = (SpacePoint) o;
        return getCoordinate().equals(that.getCoordinate()) &&
                getSpaceItem() == that.getSpaceItem();
    }

    public double getRelativeAngularCoeff() {
        return relativeAngularCoeff;
    }

    public void setRelativeAngularCoeff(double relativeAngularCoeff) {
        this.relativeAngularCoeff = relativeAngularCoeff;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate(), getSpaceItem());
    }


    @Override
    public int compareTo(SpacePoint that) {
        if (this.relativeAngularCoeff < that.relativeAngularCoeff) {
            return -1;
        } else if (this.relativeAngularCoeff > that.relativeAngularCoeff) {
            return 1;
        }
        return 0;
    }
}


enum SpaceItem {
    ASTEROID('#'), VOID('.');

    private char id;
    private static Map<Character, SpaceItem> map = new HashMap<>();

    SpaceItem(char id) {
        this.id = id;
    }

    public char getId() {
        return this.id;
    }

    static {
        for (SpaceItem d : SpaceItem.values()) {
            map.put(d.getId(), d);
        }
    }

    static SpaceItem getSpaceItemFromId(char id) {
        return map.get(id);
    }
}

enum Quadrant {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    private int id;
    private static Map<Integer, Quadrant> map = new HashMap<>();

    Quadrant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    static {
        for (Quadrant q : Quadrant.values()) {
            map.put(q.getId(), q);
        }
    }

    static Quadrant getSpaceItemFromId(int id) {
        return map.get(id);
    }
}