package Tpdohp;

import common.DemoBase;
import common.DiffusionListener;

public class Demo extends DemoBase implements DiffusionListener {

	public static void main(String[] args) {
		Demo demo = new Demo(args);
		demo.run();
	}

	public Demo(String[] args) {
		super.processArgs(args);
	}

	public void run() {
		Runtime.getRuntime().gc();
		long beginningUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long startTime = 0;
		long totalTime = 0;
		super.hotplate = new Plate(super.dimension, super.top, super.bottom,
				super.left, super.right);
		super.hotplate.setDiffusionListener(this);
		super.hotplate.setMaxIterations(100);
		super.hotplate.setTempThreshold(0.001);

		startTime = System.currentTimeMillis();

		super.hotplate.diffuse();

		totalTime = System.currentTimeMillis() - startTime;
		long endingUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long deltaUsedMemory = endingUsedMemory - beginningUsedMemory;

		super.printMatrix(super.hotplate.getMatrix());

		System.out.println("Run Time " + totalTime + "ms");
		System.out.println("Beginning Memory " + beginningUsedMemory/1024 + " kilobytes");
		System.out.println("Ending Memory " + endingUsedMemory/1024 + " kilobytes");
		System.out.println("Used Memory " + deltaUsedMemory/1024 + " kilobytes");
	}

	public void iterationDone(int currIter) {

	}

	public void diffusionDone(int finalIter) {

	}
}
