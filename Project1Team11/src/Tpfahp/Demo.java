package Tpfahp;

import Tpdahp.Plate;

public class Demo {

	public static void main(String[] args) {

		// Default arguments
		int dimension = 3;
		float leftEdgeTemp = 75.0f;
		float rightEdgeTemp = 50.0f;
		float topEdgeTemp = 100.0f;
		float bottomEdgeTemp = 0.0f;
		
		// Parse command line arguments
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) { 
			case "-d":	
				dimension = Integer.parseInt(args[i + 1]);
				break;
			case "-l":
				leftEdgeTemp = Float.parseFloat(args[i + 1]);
				break;
			case "-r":
				rightEdgeTemp = Float.parseFloat(args[i + 1]);
				break;
			case "-t":
				topEdgeTemp = Float.parseFloat(args[i + 1]);
				break;
			case "-b":
				bottomEdgeTemp = Float.parseFloat(args[i + 1]);
				break;
			default:
				System.out.println("Inoperable parameter.");
			}
		}	
		
		Plate hotplate = new Plate(dimension, topEdgeTemp, bottomEdgeTemp, leftEdgeTemp, rightEdgeTemp);
		hotplate.diffuse();
	}
}
