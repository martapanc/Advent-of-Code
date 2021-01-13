package aoc2018.day15;

import java.awt.*;

public class Unit {

    private int attackPoints;
    private int hitPoints;
    Point position;
    private char idChar = 'U';
    String name;

    public Unit(Point position) {
        this.position = position;
    }

    public Unit(Point position, String name) {
        this.position = position;
        this.name = name;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public char getIdChar() {
        return idChar;
    }

    public void setIdChar(char idChar) {
        this.idChar = idChar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "[" + hitPoints + "](" + position.x + "," + position.y + ")";
    }
}
