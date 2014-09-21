package Tpdahp;

import common.DemoBase;
import common.DiffusionListener;
import common.MetricRecorder;

public class Demo extends DemoBase implements DiffusionListener {

	private int maxIterations = 1000;
	private double temperateThreshold = 0.001;

	public static void main(String[] args) {
		Demo demo = new Demo(args);
		demo.setMetricRecorder(new MetricRecorder());
		demo.getMetricRecorder().preRunSetup();
		demo.run();
		demo.getMetricRecorder().postRunCleanup();
		demo.printMetrics();
	}

	public Demo(String[] args) {
		processArgs(args);
	}

	public void run() {
		hotplate = new Plate(dimension, top, bottom,
				left, right);
		hotplate.setDiffusionListener(this);
		hotplate.setMaxIterations(this.maxIterations);
		hotplate.setTempThreshold(this.temperateThreshold);

		hotplate.diffuse();
	}

	public void iterationDone(int currIter) {
		setNumberOfIterations(currIter);
	}

	public void diffusionDone(int finalIter) {
		setNumberOfIterations(finalIter);
		printMatrix(hotplate.getMatrix());
	}
}
