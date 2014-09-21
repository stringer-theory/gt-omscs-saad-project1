package common;

/**
 * Defines the interface for a class that captures and holds the metrics of
 * duration and memory used for a test.
 * 
 * @author Team 11
 */
public interface MetricRecorderInterface {

	/**
	 * Captures the starting time and amount of free memory.
	 */
	public void preRunSetup();

	/**
	 * Capture the different between the starting time and the ending time and
	 * the starting and ending free memory.
	 */
	public void postRunCleanup();

	/**
	 * 
	 * @return the different between the amount of starting free memory and
	 *         ending fee memory
	 */
	public long getAmountOfMemoryUsed();

	/**
	 * 
	 * @return the duration between the start time and end time
	 */
	public long getDurationOfDiffusion();

}
