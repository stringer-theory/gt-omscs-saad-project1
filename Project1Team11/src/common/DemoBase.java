package common;

public class DemoBase {

	protected int dimension = 0;
	protected double left = 0;
	protected double right = 0;
	protected double top = 0;
	protected double bottom = 0;
	protected PlateInterface hotplate;
	protected int numberOfIterations = 0;
	private MetricRecorderInterface metricRecorder;

	public DemoBase() {
	}

	public MetricRecorderInterface getMetricRecorder() {
		return metricRecorder;
	}

	public void setMetricRecorder(MetricRecorderInterface metricRecorder) {
		this.metricRecorder = metricRecorder;
	}

	public PlateInterface getHotplate() {
		return hotplate;
	}

	public void setHotplate(PlateInterface hotplate) {
		this.hotplate = hotplate;
	}

	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	protected void printMatrix(double[][] matrix) {
		System.out.println("");
		for (int row = 1; row < matrix.length - 1; row++) {
			for (int col = 1; col < matrix.length - 1; col++) {
				System.out.printf("%-6s ", String.valueOf(Math
						.round(matrix[row][col] * 100.0) / 100.0));
			}
			System.out.println("");
		}
		System.out.println("");
	}

	protected void processArgs(String[] args) {
		// ensure args are passed as pairs (flag and value)
		if (args.length > 0 && args.length % 2 == 0) {
			// parse each pair and assign value to appropriate variable
			for (int i = 0; i < args.length; i = i + 2) {
				String flag = args[i];

				if (flag.equals("-d")) {
					dimension = Integer.parseInt(args[i + 1]);
				} else if (flag.equals("-l")) {
					left = ensureInRange(args[i + 1]);
				} else if (flag.equals("-r")) {
					right = ensureInRange(args[i + 1]);
				} else if (flag.equals("-t")) {
					top = ensureInRange(args[i + 1]);
				} else if (flag.equals("-b")) {
					bottom = ensureInRange(args[i + 1]);
				} else {
					this.usage();
				}
			}
		} else {
			this.usage();
		}
	}

	protected void printMetrics() {
		System.out.println("Run Time "
				+ this.getMetricRecorder().getDurationOfDiffusion() + " ns");
		System.out.println("Used Memory "
				+ this.getMetricRecorder().getAmountOfMemoryUsed() + " bytes");
		System.out.println("Number of Iterations "
				+ this.getNumberOfIterations());
	}

	// Confine edge temperature to [0, 100] range
	protected int ensureInRange(String edgeTemp) {
		return Math.min(Math.max(Integer.parseInt(edgeTemp), 0), 100);
	}

	protected void usage() {
		System.out
				.println("Usage: Demo -d: <dimension> -t <top> -b: <bottom> -l <left> -r <right>");
		System.exit(-1);
	}

}
