package Tpdohp;

/**
 * This class represents a single point in the plate. It has references to its
 * top, bottom, left, and right neighboring points. If instead of a neighboring
 * point there is an edge, that reference is null. It also holds its temperature
 * in a primitive double.
 * 
 * @author Team 11
 * 
 */
public class Point {
	private double temperature = 0.0;
	private Point top = null;
	private Point right = null;
	private Point bottom = null;
	private Point left = null;

	/**
	 * @return
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return
	 */
	public Point getTop() {
		return top;
	}

	/**
	 * @param top
	 */
	public void setTop(Point top) {
		this.top = top;
	}

	/**
	 * @return
	 */
	public Point getRight() {
		return right;
	}

	/**
	 * @param right
	 */
	public void setRight(Point right) {
		this.right = right;
	}

	/**
	 * @return
	 */
	public Point getBottom() {
		return bottom;
	}

	/**
	 * @param bottom
	 */
	public void setBottom(Point bottom) {
		this.bottom = bottom;
	}

	/**
	 * @return
	 */
	public Point getLeft() {
		return left;
	}

	/**
	 * @param left
	 */
	public void setLeft(Point left) {
		this.left = left;
	}

}
