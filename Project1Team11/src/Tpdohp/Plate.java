package Tpdohp;

import common.DiffusionListener;
import common.PlateInterface;

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
	private Point[][] points;
	private Point topLeftCorner;

	public Plate(int dimension, double topTemperature,
			double bottomTemperature, double leftTemperature,
			double rightTemperature) {
		this.dimension = dimension;
		this.topTemperature = topTemperature;
		this.bottomTemperature = bottomTemperature;
		this.leftTemperature = leftTemperature;
		this.rightTemperature = rightTemperature;
		this.points = new Point[this.dimension][this.dimension];
		this.initializeMatrix();
	}

	@Override
	public void initializeMatrix() {
		// create all points
		for (int row = 0; row < this.dimension; row++) {
			for (int column = 0; column < this.dimension; column++) {
				this.points[row][column] = new Point();
			}
		}

		// connect each point to its surrounding points
		for (int row = 0; row < this.dimension; row++) {
			for (int column = 0; column < this.dimension; column++) {
				if (row == 0) {
					this.points[row][column].setTop(null);
				} else {
					this.points[row][column]
							.setTop(this.points[row - 1][column]);
				}

				if (row == this.dimension - 1) {
					this.points[row][column].setBottom(null);
				} else {
					this.points[row][column]
							.setBottom(this.points[row + 1][column]);
				}

				if (column == 0) {
					this.points[row][column].setLeft(null);
				} else {
					this.points[row][column]
							.setLeft(this.points[row][column - 1]);
				}

				if (column == this.dimension - 1) {
					this.points[row][column].setRight(null);
				} else {
					this.points[row][column]
							.setRight(this.points[row][column + 1]);
				}
			}
		}

		this.topLeftCorner = points[0][0];
	}

	@Override
	public void setTempThreshold(double temperatureThreshold) {
		this.temperatureThreshold = temperatureThreshold;

	}

	@Override
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	@Override
	public void setDiffusionListener(DiffusionListener diffusionListener) {
		this.diffusionListener = diffusionListener;
	}

	@Override
	public double[][] getMatrix() {
		
		// adding two to account for the edges expected by the caller
		double[][] matrix = new double[this.dimension + 2][this.dimension + 2];

		for (int row = 0; row < this.dimension + 2; row++) {
			for (int column = 0; column < this.dimension + 2; column++) {

				// if we are on a point in an edge, then just set it to zero
				if (row == 0 || row == this.dimension + 1 || column == 0
						|| column == this.dimension + 1) {
					matrix[row][column] = 0;
				} else {
					matrix[row][column] = this.points[row - 1][column - 1]
							.getTemperature();
				}
			}
		}

		return matrix;
	}

	@Override
	public void diffuse() {
		int iterations = 0;
		boolean finished = false;
		Point currentRowFirstPoint = null;

		while (!finished && iterations < this.maxIterations) {
			
			// process each row from top to bottom and left to right
			for (int row = 1; row <= this.dimension; row++) {

				// get the first point in the row
				if (row == 1) {
					currentRowFirstPoint = this.topLeftCorner;
				} else {
					currentRowFirstPoint = currentRowFirstPoint.getBottom();
				}

				// process a row starting at the left-most point
				Point currentPoint = currentRowFirstPoint;

				// when currentPoint is null, that means we reached the end of
				// the current row
				while (currentPoint != null) {
					double topTemperature = 0;
					double rightTemperature = 0;
					double bottomTemperature = 0;
					double leftTemperature = 0;

					// if there is no point above the current point, then use
					// the top edge temperature
					if (currentPoint.getTop() == null) {
						topTemperature = this.topTemperature;
					} else {
						topTemperature = currentPoint.getTop().getTemperature();
					}

					// if there is no point to the right of the current point,
					// then use the right edge temperature
					if (currentPoint.getRight() == null) {
						rightTemperature = this.rightTemperature;
					} else {
						rightTemperature = currentPoint.getRight()
								.getTemperature();
					}

					// if there is no point below the current point, then use
					// the below edge temperature
					if (currentPoint.getBottom() == null) {
						bottomTemperature = this.bottomTemperature;
					} else {
						bottomTemperature = currentPoint.getBottom()
								.getTemperature();
					}

					// if there is no point to the left of the current point,
					// then use the left edge temperature
					if (currentPoint.getLeft() == null) {
						leftTemperature = this.leftTemperature;
					} else {
						leftTemperature = currentPoint.getLeft()
								.getTemperature();
					}

					double oldPointTemperature = currentPoint.getTemperature();
					double newPointTemperature = (topTemperature
							+ rightTemperature + bottomTemperature + leftTemperature) / 4;
					currentPoint.setTemperature(newPointTemperature);

					if (Math.abs(oldPointTemperature - newPointTemperature) < this.temperatureThreshold) {
						finished = true;
					}

					// set the next currentPoint to the point to the right
					currentPoint = currentPoint.getRight();
				}

			}

			iterations++;
			this.diffusionListener.iterationDone(iterations);
		}

		this.diffusionListener.diffusionDone(iterations);
	}
}
