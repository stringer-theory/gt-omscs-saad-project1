package Twfahp;

import common.DemoBase;
import common.DiffusionListener;
import common.MetricRecorder;

/**
 * This class extends DemoBase for the purpose of executing a test from the
 * command line using the Twfahp plate implementation. It uses 1000 for the
 * default number of maximum iterations and 0.001 as the default minimum
 * temperature threshold within which to continue iterating. It uses the
 * MetricRecorder class to capture and store duration and memory usage metrics.
 * It displays the resulting plate and metrics of number of iterations to
 * converge, duration to converge, and the amount of memory used to converge
 * when finished.
 * 
 * @author Team 11
 * 
 */
public class Demo extends DemoBase implements DiffusionListener {

	private int maxIterations = 1000;
	private double temperateThreshold = 0.001;

	/**
	 * Expect argument format is Usage: Demo -d: <dimension> -t <top> -b:
	 * <bottom> -l <left> -r <right>
	 * 
	 * Sets up the plate and metric recorder, runs the test based on the command
	 * line arguments, captures the final metrics from the metric recorder,
	 * outputs the plate and metrics.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Demo demo = new Demo(args);
		demo.setMetricRecorder(new MetricRecorder());
		demo.getMetricRecorder().preRunSetup();
		demo.run();
		demo.getMetricRecorder().postRunCleanup();
		demo.printMatrix(demo.getHotplate().getMatrix());
		demo.printMetrics();
	}

	/**
	 * Processes the command line arguments.
	 * 
	 * @param args
	 */
	public Demo(String[] args) {
		super.processArgs(args);
	}

	/**
	 * Creates the plate, sets this as the diffusion listener, sets the maximum
	 * number of iterations to converge on the plate, sets the minimum
	 * temperature threshold to converge on the plate, and starts the diffusion.
	 */
	public void run() {
		super.setHotplate(new Plate(dimension, (float) top, (float) bottom,
				(float) left, (float) right));
		super.getHotplate().setDiffusionListener(this);
		super.getHotplate().setMaxIterations(this.maxIterations);
		super.getHotplate().setTempThreshold(this.temperateThreshold);
		super.getHotplate().diffuse();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.DiffusionListener#iterationDone(int)
	 */
	public void iterationDone(int currIter) {
		super.setNumberOfIterations(currIter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.DiffusionListener#diffusionDone(int)
	 */
	public void diffusionDone(int finalIter) {
		super.setNumberOfIterations(finalIter);
	}
}
