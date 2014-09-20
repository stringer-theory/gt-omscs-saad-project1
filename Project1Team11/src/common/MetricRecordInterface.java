package common;

public interface MetricRecordInterface {

	public void preRunSetup();

	public void postRunCleanup();

	public long getAmountOfMemoryUsed();

	public long getDurationOfDiffusion();

}
