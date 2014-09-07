package Tpfahp;

public class Plate {
	private double tempThreshold = .0001;
	private int dimension;
	private double topTemp;
	private double bottomTemp;
	private double leftTemp;
	private double rightTemp;
    private double[][] oldmatrix;
    private double[][] newmatrix;

	public Plate(int dim, double top, double bottom, double left, double right) {
		dimension = dim;
		topTemp = top;
		bottomTemp = bottom;
		leftTemp = left;
		rightTemp = right;
	 	oldmatrix = new double[dimension + 2][dimension + 2];
	 	newmatrix = new double[dimension + 2][dimension + 2];
		
		initializeMatrix();
		
	}

	private void printMatrix(int start, int end) {
		for (int row = start; row < end; row++) {
			for (int col = start; col < end; col++) {
				String temp = String.valueOf(Math.round(oldmatrix[row][col]*100.0)/100.0);
				if (oldmatrix[row][col] < 10) {
					if (temp.length() < 4) {
						System.out.print("  " + temp + "0 ");
					} else {							
						System.out.print("  " + temp + " ");
					}	
				} else if (oldmatrix[row][col] >= 99.995) {
					System.out.print(temp + "0 ");
				} else {
					if (temp.length() < 5) {
						System.out.print(" " + temp + "0 ");
					} else {
						System.out.print(" " + temp + " ");
					}
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private void initializeMatrix() {
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				if (row == 0) {
					oldmatrix[row][col] = topTemp;
				} else if (col == 0) {
					oldmatrix[row][col] = leftTemp;
				} else if (row == dimension + 1) {
					oldmatrix[row][col] = bottomTemp;
				} else if (col == dimension + 1) {
					oldmatrix[row][col] = rightTemp;
				} else {
					oldmatrix[row][col] = 0;
				}
			}
		}
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				if (row == 0) {
					newmatrix[row][col] = topTemp;
				} else if (col == 0) {
					newmatrix[row][col] = leftTemp;
				} else if (row == dimension + 1) {
					newmatrix[row][col] = rightTemp;
				} else if (col == dimension + 1) {
					newmatrix[row][col] = bottomTemp;
				} else {
					newmatrix[row][col] = 0;
				}
			}
		}
	}

	private void swapMatrix() {
		for (int row = 1; row < dimension+1; row++) {
			for (int col = 1; col < dimension+1; col++) {
				oldmatrix[row][col] = newmatrix[row][col];
			}
		}
	}
	
	public void diffuse() {
		boolean done = false;
		int iterations = 0;
		
		while (!done) {
			done = true;
			for (int row = 1; row < dimension + 1; row++) {
				for (int col = 1; col < dimension + 1; col++) {
					newmatrix[row][col] = (oldmatrix[row + 1][col] + oldmatrix[row - 1][col] + oldmatrix[row][col + 1] + oldmatrix[row][col - 1]) / 4.0;
					if (Math.abs(newmatrix[row][col] - oldmatrix[row][col]) >= tempThreshold) {
						done = false;
					}
				}
			}
			if (!done) {
				swapMatrix();
				iterations = iterations + 1;
			}
		}
		printMatrix(1, dimension + 1);
		System.out.println("Number of iterations: " + iterations);
	}
	
}