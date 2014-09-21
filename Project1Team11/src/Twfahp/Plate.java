// Plate.java for Float java program 
package Twfahp;

import common.*;

public class Plate implements PlateInterface {

	private double tempThreshold = .0001;
	private int maxIterations = 10000;
	private int dimension;
	private Float topTemp;
	private Float bottomTemp;
	private Float leftTemp;
	private Float rightTemp;
	private Float[][] oldmatrix;
	private Float[][] newmatrix;
	private DiffusionListener dl;

	public Plate(int dim, float top, float bottom, float left, float right) {
		dimension = dim;
		topTemp = top;
		bottomTemp = bottom;
		leftTemp = left;
		rightTemp = right;
		oldmatrix = new Float[dimension + 2][dimension + 2];
		newmatrix = new Float[dimension + 2][dimension + 2];

		initializeMatrix();
	}

	// initialize matrix to edge values and zero for all interior nodes
	public void initializeMatrix() {
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				if (row == 0) {
					newmatrix[row][col] = new Float(topTemp);
				} else if (col == 0) {
					newmatrix[row][col] = new Float(leftTemp);
				} else if (row == dimension + 1) {
					newmatrix[row][col] = new Float(bottomTemp);
				} else if (col == dimension + 1) {
					newmatrix[row][col] = new Float(rightTemp);
				} else {
					newmatrix[row][col] = new Float(0);
				}
			}
		}
		// initialize the old matrix by copying the new
		swapMatrix();
	}

	// overlay the values in the old matrix with the values in the new matrix
	private void swapMatrix() {
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				oldmatrix[row][col] = newmatrix[row][col];
			}
		}
	}
	//sets the given double value
	public void setTempThreshold(double threshold) {
		this.tempThreshold = threshold;
	}
	//sets the given int value
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}
	//sets the given value
	public void setDiffusionListener(DiffusionListener dl) {
		this.dl = dl;
	}

	public void diffuse() {
		boolean done = false;
		int iterations = 0;

		while (!done && iterations <= this.maxIterations) {
			done = true;
			for (int row = 1; row < dimension + 1; row++) {
				for (int col = 1; col < dimension + 1; col++) {
					newmatrix[row][col] = new Float(
							(oldmatrix[row + 1][col] + oldmatrix[row - 1][col]
									+ oldmatrix[row][col + 1] + oldmatrix[row][col - 1]) / 4.0);
					if (Math.abs(newmatrix[row][col] - oldmatrix[row][col]) >= tempThreshold) {
						done = false;
					}
				}
			}
			dl.iterationDone(iterations);
			if (!done) {
				swapMatrix();
				iterations = iterations + 1;
			}
		}
		dl.diffusionDone(iterations);
	}
		//Code to return matrix values
	public double[][] getMatrix() {
		// Convert matrix (after computations) to adhere to interface
		return convertMatrix(newmatrix);
	}

	// Convert float matrix to double matrix
	private double[][] convertMatrix(Float[][] newmatrix2) {
		int l = newmatrix2.length;
		double[][] convertedMatrix = new double[l][l];

		for (int row = 0; row < l; row++) {
			for (int col = 0; col < l; col++) {
				double converted = newmatrix2[row][col];
				convertedMatrix[row][col] = converted;
			}
		}

		return convertedMatrix;
	}

}
