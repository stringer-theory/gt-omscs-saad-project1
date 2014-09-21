// Plate.java
package Tpfahp;

import common.*;

/**
 * Tpfahp stands for Textual (no GUI) primitive (non-wrapped Java type) float
 * (for node values and computations) array (uses arrays to access neighboring points)
 * hot plate.
 * 
 * @author Team 11
 */
public class Plate implements PlateInterface {

	private double tempThreshold = .0001;
	private int maxIterations = 10000;
	private int dimension;
	private float topTemp;
	private float bottomTemp;
	private float leftTemp;
	private float rightTemp;
	private float[][] oldMatrix;
	private float[][] newMatrix;
	private DiffusionListener dl;

	/**
	 * Creates a plate with a height of dim and width of dim. Also
	 * sets the surrounding edge temperatures.
	 * 
	 * @param dimension
	 * @param topTemperature
	 * @param bottomTemperature
	 * @param leftTemperature
	 * @param rightTemperature
	 */
	public Plate(int dim, float top, float bottom, float left, float right) {
		dimension = dim;
		topTemp = top;
		bottomTemp = bottom;
		leftTemp = left;
		rightTemp = right;
		oldMatrix = new float[dimension + 2][dimension + 2];
		newMatrix = new float[dimension + 2][dimension + 2];

		initializeMatrix();
	}

	/**
	 * Initializes the plate's edge node values with edge temperatures.
	 * Zeroes out interior nodes.
	 */
	public void initializeMatrix() {
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				if (row == 0) {
					newMatrix[row][col] = topTemp;
				} else if (col == 0) {
					newMatrix[row][col] = leftTemp;
				} else if (row == dimension + 1) {
					newMatrix[row][col] = bottomTemp;
				} else if (col == dimension + 1) {
					newMatrix[row][col] = rightTemp;
				} else {
					newMatrix[row][col] = 0;
				}
			}
		}
		// initialize the old matrix by copying the new
		swapMatrix();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setTempThreshold(double)
	 */
	@Override
	public void setTempThreshold(double threshold) {
		this.tempThreshold = threshold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setMaxIterations(int)
	 */
	@Override
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setDiffusionListener(common.DiffusionListener)
	 */
	@Override
	public void setDiffusionListener(DiffusionListener dl) {
		this.dl = dl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#diffuse()
	 */
	@Override
	public void diffuse() {
		boolean done = false;
		int iterations = 0;

		while (!done && iterations <= this.maxIterations) {
			done = true;
			for (int row = 1; row < dimension + 1; row++) {
				for (int col = 1; col < dimension + 1; col++) {
					newMatrix[row][col] = (oldMatrix[row + 1][col]
							+ oldMatrix[row - 1][col] + oldMatrix[row][col + 1] + oldMatrix[row][col - 1]) / 4.0f;
					if (Math.abs(newMatrix[row][col] - oldMatrix[row][col]) >= tempThreshold) {
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#getMatrix()
	 */
	@Override
	public double[][] getMatrix() {
		// Convert matrix (after computations) to adhere to interface
		return convertMatrix(newMatrix);
	}

	private void swapMatrix() {
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				oldMatrix[row][col] = newMatrix[row][col];
			}
		}
	}

	// Convert float matrix to double matrix
	private double[][] convertMatrix(float[][] matrix) {
		int l = matrix.length;
		double[][] convertedMatrix = new double[l][l];

		for (int row = 0; row < l; row++) {
			for (int col = 0; col < l; col++) {
				double converted = matrix[row][col];
				convertedMatrix[row][col] = converted;
			}
		}

		return convertedMatrix;
	}
}
