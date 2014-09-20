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
		super.processArgs(args);
	}

	public void run() {
		super.hotplate = new Plate(super.dimension, (float) super.top,
				(float) super.bottom, (float) super.left, (float) super.right);
		super.hotplate.setDiffusionListener(this);
		super.hotplate.setMaxIterations(this.maxIterations);
		super.hotplate.setTempThreshold(this.temperateThreshold);

		super.hotplate.diffuse();
	}

	public void iterationDone(int currIter) {
		super.setNumberOfIterations(currIter);
	}

	public void diffusionDone(int finalIter) {
		super.setNumberOfIterations(finalIter);
	}
}
