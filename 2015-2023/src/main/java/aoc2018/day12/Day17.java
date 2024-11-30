package aoc2018.day12;

public class Day17 {

	public static void main(String[] args) {
		int[] cannes = new int[] { 33, 14, 18, 20, 45, 35, 16, 35, 1, 13, 18, 13, 50, 44, 48, 6, 24, 41, 30, 42 };
		int totalWay = 0, minContainer = cannes.length;
		for (int i = 1; i <= 1 << cannes.length; ++i) {
			int container = 0, total = 0;
			for (int j = 0; j < cannes.length; ++j) {
				if (((i >> j) & 1) > 0) {
					container++;
					total += cannes[j];
				}
			}

			if (total == 150) {
				if (minContainer > container) {
					minContainer = container;
					totalWay = 1;
				} else if (minContainer == container) {
					totalWay++;
				}
			}
		}

		System.out.println(totalWay + " " + minContainer);
	}
}