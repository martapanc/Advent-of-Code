package aoc2018.day15;//package AoC2018.fifteen;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Flooding {
//
//    public static final int Y = 13;
//    public static final int X = 13;
//
//    public static void main(String[] args) {
//        char testArray[][] = new char[Y][X];
//        for (int i = 0; i < X; i++) {
//            testArray[i][i] = '#';
//            testArray[X-1-i][i] = '#';
//        }
//        testArray[5][8] = '#';
//        testArray[6][9] = '#';
//        testArray[7][8] = '#';
//
//        for (int y = 0; y < Y; y++) {
//            for (int x = 0; x < X; x++) {
//                System.out.print(testArray[x][y] + " ");
//            }
//            System.out.println();
//        }
//
//        List<Point> list = fillArea(new Point(7,11),'\u0000','.', testArray);
//
//        System.out.println();
//        for (int y = 0; y < Y; y++) {
//            for (int x = 0; x < X; x++) {
//                System.out.print(testArray[x][y] + " ");
//            }
//            System.out.println();
//        }
//    }
//
//    public static List<Point> fillArea(Point pointInAreaToFill, char originalSymbol, char fillSymbol, char[][] matrix) {
//        int maxX = matrix.length - 1;
//        int maxY = matrix[0].length - 1;
//        int[][] stack = new int[(maxX+1)*(maxY+1)][2];
//        int index = 0;
//        int x = pointInAreaToFill.x;
//        int y = pointInAreaToFill.y;
//
//        List<Point> filledPoints = new ArrayList<>();
//
//        stack[0][0] = x;
//        stack[0][1] = y;
//        matrix[x][y] = fillSymbol;
//        filledPoints.add(new Point(x, y));
//
//        while (index >= 0){
//            x = stack[index][0];
//            y = stack[index][1];
//            index--;
//
//            if ((x > 0) && (matrix[x-1][y] == originalSymbol)){
//                matrix[x-1][y] = fillSymbol;
//                filledPoints.add(new Point(x-1, y));
//                index++;
//                stack[index][0] = x-1;
//                stack[index][1] = y;
//            }
//
//            if ((x < maxX) && (matrix[x+1][y] == originalSymbol)){
//                matrix[x+1][y] = fillSymbol;
//                filledPoints.add(new Point(x+1, y));
//                index++;
//                stack[index][0] = x+1;
//                stack[index][1] = y;
//            }
//
//            if ((y > 0) && (matrix[x][y-1] == originalSymbol)){
//                matrix[x][y-1] = fillSymbol;
//                filledPoints.add(new Point(x, y-1));
//                index++;
//                stack[index][0] = x;
//                stack[index][1] = y-1;
//            }
//
//            if ((y < maxY) && (matrix[x][y+1] == originalSymbol)){
//                matrix[x][y+1] = fillSymbol;
//                filledPoints.add(new Point(x, y+1));
//                index++;
//                stack[index][0] = x;
//                stack[index][1] = y+1;
//            }
//        }
//
//        return filledPoints;
//    }
//}
