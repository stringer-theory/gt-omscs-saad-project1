package common;
// PlateInterface.java
public interface PlateInterface{
	public void initializeMatrix();
	public void setTempThreshold(double threshold);
	public void setMaxIterations(int maxIterations);
	public void diffuse();
	public void setDiffusionListener(DiffusionListener dl);
	public double[][] getMatrix();
}
