package Twfahp;

import Twfahp.Plate;

public class Demo {
	
			public static void main(String[] args) {

			// Default arguments
			int dimension = 22;
			float leftTemp = 22.0f;
			Float floatObject1 = leftTemp;
			float rightTemp = 44.0f;
			Float floatObject2 = rightTemp;
			float topTemp = 77.0f;
			Float floatObject3 = topTemp;
			float bottomTemp = 33.0f;
			Float floatObject4 = bottomTemp;
			
			// Parse command line arguments
			for (int i = 0; i < args.length; i += 2) {
				switch (args[i]) { 
				case "-d":	
					dimension = Integer.parseInt(args[i + 1]);
					break;
				case "-l":
					floatObject1 = Float.parseFloat(args[i + 1]);
					break;
				case "-r":
					floatObject2 = Float.parseFloat(args[i + 1]);
					break;
				case "-t":
					floatObject3 = Float.parseFloat(args[i + 1]);
					break;
				case "-b":
					floatObject4 = Float.parseFloat(args[i + 1]);
					break;
				default:
					System.out.println("Inoperable parameter.");
				}
			}	
			
			Plate hotplate = new Plate(dimension, floatObject3, floatObject4, floatObject1, floatObject2);
			hotplate.diffuseHeat();
		}
	}