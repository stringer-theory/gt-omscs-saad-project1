package Tpdohp;

import common.DiffusionListener;
import common.PlateInterface;

/**
 * Tpdohp stands for Textual (no GUI) primitive (non-wrapped Java type) double
 * (to hold the point values) object (uses object graph references instead of
 * arrays to access neighboring points) hot plate. It uses an object graph of
 * Point class objects to represent a hot plate. The Point class represents a
 * single point in the plate. A Point class instance has references to its top,
 * bottom, left, and right neighboring points. If instead of a neighboring point
 * there is an edge, that reference is null. Each Point class instance also
 * holds its temperature in a primitive double.
 * 
 * @author Team 11
 */
public class Plate implements PlateInterface {

	private int dimension;
	private double topTemperature;
	private double bottomTemperature;
	private double leftTemperature;
	private double rightTemperature;
	private double temperatureThreshold;
	private int maxIterations;
	private DiffusionListener diffusionListener;
	// a two dimensional array of points is used to connect all of the points
	// and to create and two return a matrix of temperatures for printing, it is
	// not used to update point temperatures
	private Point[][] plateOnePoints;
	private Point[][] plateTwoPoints;
	// after every iteration lastUpdatedPlatePoints will reference the last
	// updated plate, whether that is plateOne or plateTwo
	private Point[][] lastUpdatedPlatePoints;
	private int iterations;

	/**
	 * Returns the number of iterations.
	 * 
	 * @return
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Creates a plate with a height of dimension and width of dimension. Also
	 * sets the surrounding edge temperatures.
	 * 
	 * @param dimension
	 * @param topTemperature
	 * @param bottomTemperature
	 * @param leftTemperature
	 * @param rightTemperature
	 */
	public Plate(int dimension, double topTemperature,
			double bottomTemperature, double leftTemperature,
			double rightTemperature) {
		this.dimension = dimension;
		this.topTemperature = topTemperature;
		this.bottomTemperature = bottomTemperature;
		this.leftTemperature = leftTemperature;
		this.rightTemperature = rightTemperature;
		// initial old plate
		this.plateOnePoints = new Point[this.dimension][this.dimension];
		// initial new plate
		this.plateTwoPoints = new Point[this.dimension][this.dimension];
		this.initializeMatrix();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#initializeMatrix()
	 */
	@Override
	public void initializeMatrix() {
		// create all points in both plates
		for (int row = 0; row < this.dimension; row++) {
			for (int column = 0; column < this.dimension; column++) {
				this.plateOnePoints[row][column] = new Point();
				this.plateTwoPoints[row][column] = new Point();
			}
		}

		// connect each point to its surrounding points
		for (int row = 0; row < this.dimension; row++) {
			for (int column = 0; column < this.dimension; column++) {

				// connect the top references
				if (row == 0) {
					this.plateOnePoints[row][column].setTop(null);
					this.plateTwoPoints[row][column].setTop(null);
				} else {
					this.plateOnePoints[row][column]
							.setTop(this.plateOnePoints[row - 1][column]);
					this.plateTwoPoints[row][column]
							.setTop(this.plateTwoPoints[row - 1][column]);
				}

				// connect the bottom references
				if (row == this.dimension - 1) {
					this.plateOnePoints[row][column].setBottom(null);
					this.plateTwoPoints[row][column].setBottom(null);
				} else {
					this.plateOnePoints[row][column]
							.setBottom(this.plateOnePoints[row + 1][column]);
					this.plateTwoPoints[row][column]
							.setBottom(this.plateTwoPoints[row + 1][column]);
				}

				// connect the left references
				if (column == 0) {
					this.plateOnePoints[row][column].setLeft(null);
					this.plateTwoPoints[row][column].setLeft(null);
				} else {
					this.plateOnePoints[row][column]
							.setLeft(this.plateOnePoints[row][column - 1]);
					this.plateTwoPoints[row][column]
							.setLeft(this.plateTwoPoints[row][column - 1]);
				}

				// connect the right references
				if (column == this.dimension - 1) {
					this.plateOnePoints[row][column].setRight(null);
					this.plateTwoPoints[row][column].setRight(null);
				} else {
					this.plateOnePoints[row][column]
							.setRight(this.plateOnePoints[row][column + 1]);
					this.plateTwoPoints[row][column]
							.setRight(this.plateTwoPoints[row][column + 1]);
				}

				// initialize old plate's values to zero degrees
				this.plateOnePoints[row][column].setTemperature(0);
			}
		}

		// after every iteration lastUpdatedPlatePoints will reference the last
		// updated plate, whether that is plateOne or plateTwo
		this.lastUpdatedPlatePoints = this.plateOnePoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#setTempThreshold(double)
	 */
	@Override
	public void setTempThreshold(double temperatureThreshold) {
		this.temperatureThreshold = temperatureThreshold;

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
	public void setDiffusionListener(DiffusionListener diffusionListener) {
		this.diffusionListener = diffusionListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#getMatrix()
	 */
	@Override
	public double[][] getMatrix() {

		// adding two to account for the edges expected by the caller, all of
		// the edges are set to 0
		double[][] matrix = new double[this.dimension + 2][this.dimension + 2];

		for (int row = 0; row < this.dimension + 2; row++) {
			for (int column = 0; column < this.dimension + 2; column++) {

				// if we are on a point in an edge, then just set it to zero
				if (row == 0 || row == this.dimension + 1 || column == 0
						|| column == this.dimension + 1) {
					matrix[row][column] = 0;
				} else {
					matrix[row][column] = this.lastUpdatedPlatePoints[row - 1][column - 1]
							.getTemperature();
				}
			}
		}

		return matrix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.PlateInterface#diffuse()
	 */
	@Override
	public void diffuse() {
		this.iterations = 0;
		boolean finished = false;
		Point currentOldRowFirstPoint = this.plateOnePoints[0][0];
		Point currentNewRowFirstPoint = this.plateTwoPoints[0][0];

		while (!finished && this.iterations < this.maxIterations) {
			int numberOfPointsWithinThreshold = 0;

			// process each row from top to bottom and left to right
			for (int row = 1; row <= this.dimension; row++) {

				// get the first point in the row
				if (row == 1) {
					// currentOldRowFirstPoint = this.plateOnePoints[0][0];
					// currentNewRowFirstPoint = this.plateTwoPoints[0][0];
				} else {
					currentOldRowFirstPoint = currentOldRowFirstPoint
							.getBottom();
					currentNewRowFirstPoint = currentNewRowFirstPoint
							.getBottom();
				}

				// process a row starting at the left-most point
				Point currentOldPoint = currentOldRowFirstPoint;
				Point currentNewPoint = currentNewRowFirstPoint;

				// when currentPoint is null, that means we reached the end of
				// the current row
				while (currentOldPoint != null) {
					double topTemperature = 0.0;
					double rightTemperature = 0.0;
					double bottomTemperature = 0.0;
					double leftTemperature = 0.0;

					// if there is no point above the current point, then use
					// the top edge temperature
					if (currentOldPoint.getTop() == null) {
						topTemperature = this.topTemperature;
					} else {
						topTemperature = currentOldPoint.getTop()
								.getTemperature();
					}

					// if there is no point to the right of the current point,
					// then use the right edge temperature
					if (currentOldPoint.getRight() == null) {
						rightTemperature = this.rightTemperature;
					} else {
						rightTemperature = currentOldPoint.getRight()
								.getTemperature();
					}

					// if there is no point below the current point, then use
					// the below edge temperature
					if (currentOldPoint.getBottom() == null) {
						bottomTemperature = this.bottomTemperature;
					} else {
						bottomTemperature = currentOldPoint.getBottom()
								.getTemperature();
					}

					// if there is no point to the left of the current point,
					// then use the left edge temperature
					if (currentOldPoint.getLeft() == null) {
						leftTemperature = this.leftTemperature;
					} else {
						leftTemperature = currentOldPoint.getLeft()
								.getTemperature();
					}

					double oldPointTemperature = currentOldPoint
							.getTemperature();
					double newPointTemperature = (topTemperature
							+ rightTemperature + bottomTemperature + leftTemperature) / 4.0;
					currentNewPoint.setTemperature(newPointTemperature);

					// check this point to see if it is within the threshold of
					// minimum change
					if (Math.abs(oldPointTemperature - newPointTemperature) < this.temperatureThreshold) {
						// if this point is within the threshold of minimum
						// change, then increment the counter for the number of
						// points that are within the minimum temperature
						// threshold
						numberOfPointsWithinThreshold++;

						// if all of the points within the matrix are within the
						// threshold of minimum change, then we are finished
						if (numberOfPointsWithinThreshold == this.dimension
								* this.dimension) {
							finished = true;
						}
					}

					// set the next currentPoint to the point to the right
					currentOldPoint = currentOldPoint.getRight();
					currentNewPoint = currentNewPoint.getRight();
				}

			}

			this.iterations++;
			this.diffusionListener.iterationDone(this.iterations);

			// swap our old and new matrices
			if (this.iterations % 2 == 0) {
				currentOldRowFirstPoint = this.plateOnePoints[0][0];
				currentNewRowFirstPoint = this.plateTwoPoints[0][0];
				this.lastUpdatedPlatePoints = this.plateOnePoints;
			} else {
				currentOldRowFirstPoint = this.plateTwoPoints[0][0];
				currentNewRowFirstPoint = this.plateOnePoints[0][0];
				this.lastUpdatedPlatePoints = this.plateTwoPoints;
			}
		}

		this.diffusionListener.diffusionDone(this.iterations);
	}
}
