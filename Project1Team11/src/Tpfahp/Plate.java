package Tpfahp;

import Tpfahp.Printer;

public class Plate {
	private float tempChangeMin = .0001f;
	private int dimension;
	private int dimensionWithEdges;
	private float topTemp;
	private float bottomTemp;
	private float leftTemp;
	private float rightTemp;
    private float[][] oldMatrix;
    private float[][] newMatrix;

	public Plate(int dimension, float topTemp, float bottomTemp, float leftTemp, float rightTemp) {
		this.dimension = dimension;
		this.dimensionWithEdges = dimension + 2;
		this.topTemp = topTemp;
		this.bottomTemp = bottomTemp;
		this.leftTemp = leftTemp;
		this.rightTemp = rightTemp;
	 	oldMatrix = new float[dimensionWithEdges][dimensionWithEdges];
	 	newMatrix = new float[dimensionWithEdges][dimensionWithEdges];
		
		initializeMatrices();
	}
	
	public void diffuseHeat() {
		boolean done = false;
		
		while(!done) {
			done = true;
			
			// Compute new node temperatures
			for (int row = 1; row <= dimension; row++) {
				for (int col = 1; col <= dimension; col++) {
					newMatrix[row][col] = computeNodeTemperature(row, col);
					
					// If delta of any single node is greater than or equal to
					// temperature change minimum, carry on with diffusion
					if(newMatrix[row][col] - oldMatrix[row][col] >= tempChangeMin) {
						done = false;
					}
				}
			}
			
			this.oldMatrix = copyMatrix(newMatrix);
		}
		
		// Output completed heat diffusion
		Printer.printMatrix(newMatrix);
	}
	
	private void initializeMatrices() {
		// Initialize a first matrix w/ edge temperatures and 0's
		for (int row = 0; row < dimensionWithEdges; row++) {
			for (int col = 0; col < dimensionWithEdges; col++) {
				if (row == 0) {
					oldMatrix[row][col] = topTemp;
				} else if (col == 0) {
					oldMatrix[row][col] = leftTemp;
				} else if (row == dimension + 1) {
					oldMatrix[row][col] = bottomTemp;
				} else if (col == dimension + 1) {
					oldMatrix[row][col] = rightTemp;
				} else {
					oldMatrix[row][col] = 0.0f;
				}
			}
		}
		
		// Duplicate the value-populated matrix
		this.newMatrix = copyMatrix(oldMatrix);
	}
	
	private float[][] copyMatrix(float[][] matrix) {
		float[][] tmpMatrix = new float[dimensionWithEdges][dimensionWithEdges];
		for (int row = 0; row < dimensionWithEdges; row++) {
			for (int col = 0; col < dimensionWithEdges; col++) {
				tmpMatrix[row][col] = matrix[row][col];
			}
		}
		return tmpMatrix;
	}
	
	private float computeNodeTemperature(int row, int col) {
		return (oldMatrix[row + 1][col] + oldMatrix[row - 1][col] +
				oldMatrix[row][col + 1] + oldMatrix[row][col - 1]) / 4.0f;
	}
}