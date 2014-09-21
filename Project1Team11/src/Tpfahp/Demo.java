package Tpfahp;

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
		demo.printMatrix(demo.getHotplate().getMatrix());
		demo.printMetrics();
	}

	public Demo(String[] args) {
		processArgs(args);
	}

	public void run() {
		hotplate = new Plate(dimension, (float) top,
				(float) bottom, (float) left, (float) right);
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
	}
}
