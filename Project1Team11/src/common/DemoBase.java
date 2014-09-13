package common;

public class DemoBase{

	protected int dimension = 0; 
	protected double left = 0;
	protected double right = 0;
	protected double top = 0;
	protected double bottom = 0;
	protected PlateInterface hotplate;

	public DemoBase(){}

	protected void printMatrix(double[][] matrix) {
		System.out.println("");
		for (int row = 1; row < matrix.length - 1; row++) {
			for (int col = 1; col < matrix.length - 1; col++) {
				System.out.printf("%-6s ", String.valueOf(Math.round(matrix[row][col] * 100.0)/100.0));
			}
			System.out.println("");
		}
		System.out.println("");
	}

	protected void processArgs(String[] args){
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

	protected void usage(){
		System.out.println("Usage: Demo -d: <dimension> -t <top> -b: <bottom> -l <left> -r <right>");
		System.exit(-1);
	}
}
