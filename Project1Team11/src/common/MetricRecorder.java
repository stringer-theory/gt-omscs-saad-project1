package common;

/**
 * This is a simple implementation of the MetricRecorderInterface. It returns
 * the amount of memory used in bytes and the duration of the test run in
 * nanoseconds.
 * 
 * @author Team 11
 * 
 */
public class MetricRecorder implements MetricRecorderInterface {
	private long amountOfMemoryUsed = 0;
	private long durationOfDiffusion = 0;
	private long startTime = 0;
	private long beginningUsedMemory = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.MetricRecordInterface#getAmountOfMemoryUsed()
	 */
	public long getAmountOfMemoryUsed() {
		return amountOfMemoryUsed;
	}

	/**
	 * @param amountOfMemoryUsed
	 */
	private void setAmountOfMemoryUsed(long amountOfMemoryUsed) {
		this.amountOfMemoryUsed = amountOfMemoryUsed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.MetricRecordInterface#getDurationOfDiffusion()
	 */
	public long getDurationOfDiffusion() {
		return durationOfDiffusion;
	}

	/**
	 * @param durationOfDiffusion
	 */
	private void setDurationOfDiffusion(long durationOfDiffusion) {
		this.durationOfDiffusion = durationOfDiffusion;
	}

	/**
	 * @return
	 */
	private long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 */
	private void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return
	 */
	private long getBeginningUsedMemory() {
		return beginningUsedMemory;
	}

	/**
	 * @param beginningUsedMemory
	 */
	public void setBeginningUsedMemory(long beginningUsedMemory) {
		this.beginningUsedMemory = beginningUsedMemory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.MetricRecordInterface#preRunSetup()
	 */
	public void preRunSetup() {

		// Request two garbage collections because the first pass does not
		// always retrieve all unreferenced objects.
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();

		this.setBeginningUsedMemory(Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory());

		this.setStartTime(System.nanoTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.MetricRecordInterface#postRunCleanup()
	 */
	public void postRunCleanup() {

		// get the ending values
		long endingUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long endTime = System.nanoTime();

		long duration = endTime - this.getStartTime();
		this.setDurationOfDiffusion(duration);

		long totalMemoryUsed = endingUsedMemory - this.getBeginningUsedMemory();
		this.setAmountOfMemoryUsed(totalMemoryUsed);
	}
}
