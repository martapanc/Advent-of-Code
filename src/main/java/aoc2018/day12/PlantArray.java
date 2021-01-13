package aoc2018.day12;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PlantArray {

    private char[] plantArray;

    private int shift = 0;

    /* Initialise array with two '.' at the beginning and at the end */
    public PlantArray(char[] array) {

        int emptyPotsAtTheBeginning = 0;
        int emptyPotsAtTheEnd = 0;
        if (array[0] == '#') {
            emptyPotsAtTheBeginning = 3;
        }
        if (array[0] == '.' && array[1] == '.' && array[2] == '#') {
            emptyPotsAtTheBeginning = 1;
        }
        if (array[array.length -1] == '#') {
            emptyPotsAtTheEnd = 3;
        }
        if (array[array.length -3] == '#' &&array[array.length -2] == '.' && array[array.length -1] == '.') {
            emptyPotsAtTheEnd = 1;
        }
        this.plantArray = new char[array.length + emptyPotsAtTheBeginning + emptyPotsAtTheEnd];

        for (int b = 0; b < emptyPotsAtTheBeginning; b ++) {
            this.plantArray[b] = '.';
        }
        for (int e = 1; e <= emptyPotsAtTheEnd; e++) {
            this.plantArray[plantArray.length - e] = '.';
        }
        this.shift = emptyPotsAtTheBeginning;
        IntStream.of(0, 1, this.plantArray.length - 2, this.plantArray.length - 1).forEach(i -> this.plantArray[i] = '.');
        System.arraycopy(array, 0, this.plantArray, emptyPotsAtTheBeginning, array.length);
    }

    public PlantArray(PlantArray oldGen){
        this.plantArray = new char[oldGen.size()];
        Arrays.fill(this.plantArray, '.');
    }

    @Override
    public String toString() {
        return Arrays.toString(plantArray);
    }

    public void printAsAString(){
        System.out.println(new String(this.plantArray));
    }

    public String getNeighborsOfPlant(int index) {
        String neighbors = "";
        for (int i = index - 2; i <= index + 2; i++) {
            neighbors += plantArray[i];
        }
        return neighbors;
    }

    public int size() {
        return this.plantArray.length;
    }

    public void setPlantAt(int index, char plant) {
        this.plantArray[index] = plant;
    }

    public char[] getArray(){
        return this.plantArray;
    }

    public char getChar(int index) {
        return this.plantArray[index];
    }

    public int getShift() {
        return shift;
    }
}
