package aoc2019.day06;

public class OrbitSystem {

    private String center;
    private String satellite;

    public OrbitSystem(String center, String satellite) {
        this.center = center;
        this.satellite = satellite;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    @Override
    public String toString() {
        return center + ")" + satellite;
    }
}
