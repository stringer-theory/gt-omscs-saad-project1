package Tpfahp;

import Tpfahp.Plate;

public class Demo {

	public static void main(String[] args) {

		// Default arguments
		int dimension = 22;
		float leftTemp = 22.0f;
		float rightTemp = 44.0f;
		float topTemp = 77.0f;
		float bottomTemp = 33.0f;
		
		// Parse command line arguments
		for (int i = 0; i < args.length; i += 2) {
			switch (args[i]) { 
			case "-d":	
				dimension = Integer.parseInt(args[i + 1]);
				break;
			case "-l":
				leftTemp = Float.parseFloat(args[i + 1]);
				break;
			case "-r":
				rightTemp = Float.parseFloat(args[i + 1]);
				break;
			case "-t":
				topTemp = Float.parseFloat(args[i + 1]);
				break;
			case "-b":
				bottomTemp = Float.parseFloat(args[i + 1]);
				break;
			default:
				System.out.println("Inoperable parameter.");
			}
		}	
		
		Plate hotplate = new Plate(dimension, topTemp, bottomTemp, leftTemp, rightTemp);
		hotplate.diffuseHeat();
	}
}
