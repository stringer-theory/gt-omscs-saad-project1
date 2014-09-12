package Twfahp;

	public class Printer {
		public static void printMatrix(Float[][] matrix) {
			System.out.println("");
			for (int row = 1; row < matrix.length - 1; row++) {
				for (int col = 1; col < matrix.length - 1; col++) {
					System.out.printf("%-6s ", String.valueOf(Math.round(matrix[row][col] * 100.0f)/100.0f));
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}