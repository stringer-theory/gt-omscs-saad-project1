package common;

public class MetricRecorder implements MetricRecordInterface {
	private long amountOfMemoryUsed = 0;
	private long durationOfDiffusion = 0;
	private long startTime = 0;
	private long beginningUsedMemory = 0;

	public long getAmountOfMemoryUsed() {
		return amountOfMemoryUsed;
	}

	public void setAmountOfMemoryUsed(long amountOfMemoryUsed) {
		this.amountOfMemoryUsed = amountOfMemoryUsed;
	}

	public long getDurationOfDiffusion() {
		return durationOfDiffusion;
	}

	public void setDurationOfDiffusion(long durationOfDiffusion) {
		this.durationOfDiffusion = durationOfDiffusion;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getBeginningUsedMemory() {
		return beginningUsedMemory;
	}

	public void setBeginningUsedMemory(long beginningUsedMemory) {
		this.beginningUsedMemory = beginningUsedMemory;
	}

	public void preRunSetup() {
		Runtime.getRuntime().gc();
		Runtime.getRuntime().gc();

		this.setStartTime(System.nanoTime());

		this.setBeginningUsedMemory(Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory());
	}

	public void postRunCleanup() {
		long endingUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long endTime = System.nanoTime();

		long duration = endTime - startTime;
		this.setDurationOfDiffusion(duration);
		long totalMemoryUsed = endingUsedMemory - beginningUsedMemory;
		this.setAmountOfMemoryUsed(totalMemoryUsed);
	}

}
