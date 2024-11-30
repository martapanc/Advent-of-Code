package aoc2019.day14;

import java.util.Objects;

public class Chemical {

    private int quantity;
    private String name;

    public Chemical(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + quantity + ")";
    }

    public boolean equalsName(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chemical chemical = (Chemical) o;
        return Objects.equals(getName(), chemical.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chemical chemical = (Chemical) o;
        return getQuantity() == chemical.getQuantity() &&
                getName().equals(chemical.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
