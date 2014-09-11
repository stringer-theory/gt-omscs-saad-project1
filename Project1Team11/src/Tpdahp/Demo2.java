package Tpdahp;

import common.*;

public class Demo2 implements DiffusionListener{

	private int dimension = 0; 
	private double left = 0;
	private double right = 0;
	private double top = 0;
	private double bottom = 0;
	private Plate2 hotplate;

	public static void main(String[] args) {

		Demo2 demo = new Demo2(args);
		demo.run();			
	}

	public Demo2(String[] args){
		processArgs(args);		
	}

	public void run(){
		long startTime = 0;
		long totalTime = 0;
		hotplate = new Plate2(dimension, top, bottom, left, right);
		hotplate.setDiffusionListener(this);
		
		startTime = System.currentTimeMillis();
		hotplate.diffuse();
		totalTime = System.currentTimeMillis() - startTime;
		
		System.out.println("Run Time " + totalTime + "ms");
		System.out.println("Max Memory " + Runtime.getRuntime().maxMemory() + " bytes");

	}

	public void iterationDone() {
		// not implemented
	}
	
	public void diffusionDone(){
		double[][] matrix = hotplate.getMatrix();
		printMatrix(matrix);
	}

	public void printMatrix(double[][] matrix) {
		System.out.println("");
		for (int row = 1; row < matrix.length - 1; row++) {
			for (int col = 1; col < matrix.length - 1; col++) {
				System.out.printf("%-6s ", String.valueOf(Math.round(matrix[row][col] * 100.0f)/100.0f));
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public void processArgs(String[] args){
		// insure args are passed as pairs (flag and value)
		if (args.length>0 && args.length % 2 == 0) {
			// parse each pair and assign value to appropriate variable
			for (int loop = 0; loop < args.length; loop = loop + 2) {
				switch (args[loop]) { 
				// dimension
				case "-d":	
					dimension = Integer.parseInt(args[loop + 1]);
					break;
				// left edge (range 0-100)	
				case "-l":
					left = Integer.parseInt(args[loop + 1]);
					if (left < 0) {
						left = 0;
					} else if (left > 100) {
						left = 100;
					}
					break;
				// right edge (range 0-100)
				case "-r":
					right = Integer.parseInt(args[loop + 1]);
					if (right < 0) {
						right = 0;
					} else if (right > 100) {
						right = 100;
					}
					break;
				// top edge (range 0-100)
				case "-t":
					top = Integer.parseInt(args[loop + 1]);
					if (top < 0) {
						top = 0;
					} else if (top > 100) {
						top = 100;
					}
					break;
				// bottom edge (range 0-100)
				case "-b":
					bottom = Integer.parseInt(args[loop + 1]);
					if (bottom < 0) {
						bottom = 0;
					} else if (bottom > 100) {
						bottom = 100;
					}
					break;
				// unknown flag	
				default:
					usage();
				}
			}
		} else {
			usage();
		}
	}

	private void usage(){
		System.out.println("Usage: Tpdahp -d: <dimension> -t <top> -b: <bottom> -l <left> -r <right>");
		System.exit(-1);
	}
}
