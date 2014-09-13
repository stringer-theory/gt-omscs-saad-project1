package Twfahp;

import common.*;

public class Demo extends DemoBase implements DiffusionListener{

	public static void main(String[] args) {
		Demo demo = new Demo(args);
		demo.run();			
	}

	public Demo(String[] args){
		processArgs(args);		
	}

	public void run(){
		long startTime = 0;
		long totalTime = 0;
		hotplate = new Plate(dimension, (Float)top, (Float)bottom, (Float)left, (Float)right);
		hotplate.setDiffusionListener(this);
		
		startTime = System.currentTimeMillis();
		hotplate.diffuse();
		totalTime = System.currentTimeMillis() - startTime;
		
		System.out.println("Run Time " + totalTime + "ms");
		System.out.println("Max Memory " + Runtime.getRuntime().maxMemory() + " bytes");
	}

	public void iterationDone(int currIter) {
		// System.out.println("i:"+currIter);
	}
	
	public void diffusionDone(int finalIter){
		double[][] matrix = hotplate.getMatrix();
		printMatrix(matrix);
	}
}
