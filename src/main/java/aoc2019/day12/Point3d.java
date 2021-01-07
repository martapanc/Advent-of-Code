package aoc2019.day12;

import java.util.Objects;

public class Point3d {

    private int x;
    private int y;
    private int z;
    private int xSpeed;
    private int ySpeed;
    private int zSpeed;

    public Point3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.zSpeed = 0;
    }

    public Point3d(String x, String y, String z) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.zSpeed = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setSpeed(int xSpeed, int ySpeed, int zSpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public int getzSpeed() {
        return zSpeed;
    }

    public int getPotentialEnergy() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    public int getKineticEnergy() {
        return Math.abs(xSpeed) + Math.abs(ySpeed) + Math.abs(zSpeed);
    }

    public boolean equalsPos(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3d)) return false;
        Point3d point3d = (Point3d) o;
        return getX() == point3d.getX() &&
                getY() == point3d.getY() &&
                getZ() == point3d.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        return "pos=<x= " + x + ", y= " + y + ", z= " + z + ">, " +
                "vel=<x= " + xSpeed + ", y= " + ySpeed + ", z= " + zSpeed + ">";
    }
}
