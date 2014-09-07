package Tpfahp;

import Tpdahp.Plate;

public class Demo {

	public static void main(String[] args) {

		int dimension = 3; 
		double left = 75;
		double right = 50;
		double top = 100;
		double bottom = 0;

		for (int loop = 0; loop < args.length; loop = loop + 2) {
			switch (args[loop]) { 
			case "-d":	
				dimension = Integer.parseInt(args[loop + 1]);
				break;
			case "-l":
				left = Integer.parseInt(args[loop + 1]);
				break;
			case "-r":
				right = Integer.parseInt(args[loop + 1]);
				break;
			case "-t":
				top = Integer.parseInt(args[loop + 1]);
				break;
			case "-b":
				bottom = Integer.parseInt(args[loop + 1]);
				break;
			default:
				System.out.println("Bad param");
			}
		}	
		
		System.out.println("-d: " + dimension + " -t: " + top + " -b: " + bottom + " -l: " + left + " -r: " + right);
		System.out.println("");
		
		Plate hotplate = new Plate(dimension, top, bottom, left, right);
		hotplate.diffuse();

	}
}
