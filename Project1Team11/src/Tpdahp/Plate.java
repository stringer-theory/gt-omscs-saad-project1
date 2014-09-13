// Plate.java
package Tpdahp;

import common.*;

class Plate implements PlateInterface {
	//todo: get this from properties
	private double tempThreshold = .0001;
	private int maxIterations = 10000;
	private int dimension;
	private double topTemp;
	private double bottomTemp;
	private double leftTemp;
	private double rightTemp;
    private double[][] oldmatrix;
    private double[][] newmatrix;
    private DiffusionListener dl;

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

	// initialize matrix to edge values and zero for all interior nodes
	public void initializeMatrix() {
		for (int row = 0; row < dimension + 2; row++) {
			for (int col = 0; col < dimension + 2; col++) {
				if (row == 0) {
					newmatrix[row][col] = topTemp;
				} else if (col == 0) {
					newmatrix[row][col] = leftTemp;
				} else if (row == dimension + 1) {
					newmatrix[row][col] = bottomTemp;
				} else if (col == dimension + 1) {
					newmatrix[row][col] = rightTemp;
				} else {
					newmatrix[row][col] = 0;
				}
			}
		}
		// initialize the old matrix by copying the new
		swapMatrix();	
	}

	// overlay the values in the old matrix with the values in the new matrix
	private void swapMatrix() {
		for (int row = 0; row < dimension+2; row++) {
			for (int col = 0; col < dimension+2; col++) {
				oldmatrix[row][col] = newmatrix[row][col];
			}
		}
	}
	
	public void setTempThreshold(double threshold){
		this.tempThreshold = threshold;
	}
	
	public void setMaxIterations(int maxIterations){
		this.maxIterations = maxIterations;
	}
	
	public void setDiffusionListener(DiffusionListener dl){
		this.dl = dl;
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
			dl.iterationDone(iterations);
			if (!done) {
				swapMatrix();
				iterations = iterations + 1;
				if (iterations >= maxIterations) {
					done = true;
				}
			}
		}
		dl.diffusionDone(iterations);
	}
	public double[][] getMatrix(){
		return oldmatrix;
	}
}
