// Plate.java
package Tpdahp;

import common.*;

/**
 * Tpdahp stands for Textual (no GUI) primitive (non-wrapped Java type) double
 * (to hold the point values) array (uses arrays instead of object graph references
 * to access neighboring points) hot plate. It uses a two-dimensional array of 
 * doubles to represent the hot plate. 
 * 
 * @author Team 11
 */

public class Plate implements PlateInterface {
	//todo: get this from properties
	private double tempThreshold = .0001;
	private int maxIterations = 10000;
	// parameters specified by user
	private int dimension;
	private double topTemp;
	private double bottomTemp;
	private double leftTemp;
	private double rightTemp;
	// two instances of the plate, as a two-dimensional array of doubles. The new temps 
	// are calculated on the new matrix using current temps on the old, and then the old
	// values are replaced with the new after each iteration.
    private double[][] oldmatrix;
    private double[][] newmatrix;
    private DiffusionListener dl;

	/**
	 * Creates a plate of specified dimension and sets the surrounding edge temperatures.
	 * 
	 * @param dimension
	 * @param topTemp
	 * @param bottomTemp
	 * @param leftTemp
	 * @param rightTemp
	 */
    // Plate constructor
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#initializeMatrix()
	 */
	@Override
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setTempThreshold(double)
	 */
	@Override
	public void setTempThreshold(double threshold){
		this.tempThreshold = threshold;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setMaxIterations(int)
	 */
	@Override
	public void setMaxIterations(int maxIterations){
		this.maxIterations = maxIterations;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setDiffusionListener(common.DiffusionListener)
	 */
	@Override
	public void setDiffusionListener(DiffusionListener dl){
		this.dl = dl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#diffuse()
	 */
	@Override
	//Use a difference equation to approximate the diffusion of heat through the plate
	public void diffuse() {
		boolean done = false;
		int iterations = 0;
		
		while (!done) {
			done = true;
			for (int row = 1; row < dimension + 1; row++) {
				for (int col = 1; col < dimension + 1; col++) {
					newmatrix[row][col] = (oldmatrix[row + 1][col] + oldmatrix[row - 1][col] + oldmatrix[row][col + 1] + oldmatrix[row][col - 1]) / 4.0;
					// Has there been a significant temperature change?
					if (Math.abs(newmatrix[row][col] - oldmatrix[row][col]) >= tempThreshold) {
						done = false;
					}
				}
			}
			dl.iterationDone(iterations);
			if (!done) {
				swapMatrix();
				iterations = iterations + 1;
				// Have we reached the max number of iterations (fail-safe)
				if (iterations >= maxIterations) {
					done = true;
				}
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
	public double[][] getMatrix(){
		return oldmatrix;
	}
}
