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
		// ensure args are passed as pairs (flag and value)
		if (args.length>0 && args.length % 2 == 0) {
			// parse each pair and assign value to appropriate variable
			for (int i = 0; i < args.length; i = i + 2) {
				String flag = args[i];
				
				if(flag.equals("-d")) {	
					dimension = Integer.parseInt(args[i + 1]);
				} else if(flag.equals("-l")) {
					left = ensureInRange(args[i + 1]);
				} else if(flag.equals("-r")) {
					right = ensureInRange(args[i + 1]);
				} else if(flag.equals("-t")) {
					top = ensureInRange(args[i + 1]);
				} else if(flag.equals("-b")) {
					bottom = ensureInRange(args[i + 1]);
				} else {
					usage();
				}
			}
		} else {
			usage();
		}
	}
	
	// Confine edge temperature to [0, 100] range
	protected int ensureInRange(String edgeTemp) {
		int tmpTemp = Integer.parseInt(edgeTemp);
		if (tmpTemp < 0) {
			tmpTemp = 0;
		} else if (tmpTemp > 100) {
			tmpTemp = 100;
		}
		return tmpTemp;
	}

	protected void usage(){
		System.out.println("Usage: Demo -d: <dimension> -t <top> -b: <bottom> -l <left> -r <right>");
		System.exit(-1);
	}
}
