package Tpdahp;

import Tpdahp.Plate;

public class Demo {

	public static void main(String[] args) {

		int dimension = 0; 
		double left = 0;
		double right = 0;
		double top = 0;
		double bottom = 0;
		long startTime = 0;
		long totalTime = 0;
		
		// insure args are passed as pairs (flag and value)
		if (args.length % 2 == 0) {
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
					System.out.println("Invalid parameter specified");
				}
			}

			System.out.println("Tpdahp -d: " + dimension + " -t: " + top + " -b: " + bottom + " -l: " + left + " -r: " + right);
			System.out.println("");
			
			// get start time
			startTime = System.currentTimeMillis();
			Plate hotplate = new Plate(dimension, top, bottom, left, right);
			hotplate.diffuse();
			// compute elapsed time
			totalTime = System.currentTimeMillis() - startTime;
			
			System.out.println("Run Time " + totalTime + "ms");
			System.out.println("Max Memory " + Runtime.getRuntime().maxMemory() + " bytes");
			
		} else {
			System.out.println("Incorrect number of parameters specified");
		}
	}
	
}