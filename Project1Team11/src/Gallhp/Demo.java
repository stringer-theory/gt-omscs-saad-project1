package Gallhp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import common.DiffusionListener;
import common.MetricRecordInterface;
import common.MetricRecorder;
import common.PlateInterface;

public class Demo extends JFrame implements ActionListener, DiffusionListener {

	private MetricRecordInterface metricRecorder;
	protected int numberOfIterations = 0;

	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public MetricRecordInterface getMetricRecorder() {
		return metricRecorder;
	}

	public void setMetricRecorder(MetricRecordInterface metricRecorder) {
		this.metricRecorder = metricRecorder;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Demo ui = new Demo();
				ui.setVisible(true);
			}
		});
	}

	public Demo() {
		createUI();
		addEventHandlers();
	}

	private void createUI() {
		setupWindow();
		add(contentsPanel());
		pack();
	}

	private void setupWindow() {
		// setup overall app ui
		setTitle("Heated Plate Diffusion Simulation");
		// setSize(300, 200);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private JPanel contents;

	private JPanel contentsPanel() {
		// setup primary window contents panel
		JPanel contents = new JPanel(new BorderLayout());
		contents.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		contents.setAlignmentY(Component.TOP_ALIGNMENT);

		contents.add(inputsPanel(), BorderLayout.WEST);
		// contents.add(visualization(),BorderLayout.CENTER);
		// contents.add(runControls(),BorderLayout.SOUTH);

		this.contents = contents;
		return contents;
	}

	private JPanel inputsPanel() {
		JPanel inputsPanel = new JPanel();
		inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.PAGE_AXIS));
		// inputsPanel.setLayout(new FlowLayout());
		inputsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		// inputsPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		// inputsPanel.setPreferredSize(new Dimension(300,200));

		inputsPanel.add(prompt("Choose a simulation..."));
		inputsPanel.add(simulationCombo());
		inputsPanel.add(settings());
		inputsPanel.add(Box.createVerticalGlue());

		return inputsPanel;
	}

	// private Grid visualization(){ return new Grid();}

	private JPanel runControls() {
		JPanel ctrlsPanel = new JPanel(new FlowLayout());
		ctrlsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		ctrlsPanel.add(button("Run"));
		ctrlsPanel.add(button("Pause"));
		ctrlsPanel.add(button("stop"));

		return ctrlsPanel;
	}

	private JComboBox<String> simulationCombo;

	private JComboBox<String> simulationCombo() {
		String[] options = { "Tpdahp", "Tpfahp", "Twfahp", "Tpdohp" };
		simulationCombo = new JComboBox<String>(options);
		simulationCombo.setActionCommand("simulation");
		simulationCombo.addActionListener(this);

		return simulationCombo;
	}

	private JPanel settings() {
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		settingsPanel.setLayout(new BoxLayout(settingsPanel,
				BoxLayout.PAGE_AXIS));
		settingsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		// settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// settingsPanel.add(prompt("Set initial conditions"));
		settingsPanel.add(inputField("Dimension"));
		settingsPanel.add(inputField("Top"));
		settingsPanel.add(inputField("Bot"));
		settingsPanel.add(inputField("Left"));
		settingsPanel.add(inputField("Right"));
		// settingsPanel.add(prompt("Prompt2 goes here"));
		settingsPanel.add(inputField("# Iterations"));
		settingsPanel.add(inputField("Min. Temp Threshold"));
		settingsPanel.add(Box.createVerticalGlue());
		settingsPanel.add(runControls());

		return settingsPanel;
	}

	private JLabel prompt(String prompt) {
		JLabel label = new JLabel(prompt);
		label.setAlignmentX(Component.RIGHT_ALIGNMENT);
		return label;
	}

	private HashMap<String, JTextField> inputs = new HashMap<>();

	private JPanel inputField(String name) {
		JPanel inputPanel = new JPanel();
		// inputPanel.setBorder(BorderFactory.createTitledBorder("blah"));
		// inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.LINE_AXIS));
		inputPanel.setLayout(new FlowLayout());
		inputPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel l = new JLabel(name);
		l.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputPanel.add(l);

		// inputPanel.add(Box.createHorizontalGlue());
		JTextField t = new JTextField("", 10);
		t.setAlignmentX(Component.RIGHT_ALIGNMENT);
		l.setLabelFor(t);
		inputPanel.add(t);

		inputs.put(name, t);
		return inputPanel;
	}

	private JButton button(String name) {
		JButton button = new JButton(name);
		button.setActionCommand(name);
		button.addActionListener(this);
		return button;
	}

	private void addEventHandlers() {
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("simulation".equals(cmd)) {
			return;
		}
		if ("Run".equals(cmd)) {
			try {
				run(simulationCombo.getSelectedIndex());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null,
						"Please correct input. All fields need numbers");
			}
			return;
		}
	}

	private int dimension = 0;
	private double left = 0;
	private double right = 0;
	private double top = 0;
	private double bottom = 0;
	private PlateInterface hotplate;

	private void run(int i) throws NumberFormatException {
		// long startTime = 0;
		// long totalTime = 0;

		// System.out.println(inputs.get("Dimension"));
		dimension = Integer.parseInt(inputs.get("Dimension").getText());
		top = Double.parseDouble(inputs.get("Top").getText());
		bottom = Double.parseDouble(inputs.get("Bot").getText());
		left = Double.parseDouble(inputs.get("Left").getText());
		right = Double.parseDouble(inputs.get("Right").getText());

		this.setMetricRecorder(new MetricRecorder());
		this.getMetricRecorder().preRunSetup();

		String plateImplementationName = null;
		switch (i) {
		case 0:
			plateImplementationName = "Tpdahp";
			hotplate = new Tpdahp.Plate(dimension, top, bottom, left, right);
			break;
		case 1:
			plateImplementationName = "Tpfahp";
			hotplate = new Tpfahp.Plate(dimension, (float) top, (float) bottom,
					(float) left, (float) right);
			break;
		case 2:
			plateImplementationName = "Twfahp";
			hotplate = new Twfahp.Plate(dimension, (float) top, (float) bottom,
					(float) left, (float) right);
			break;
		case 3:
			plateImplementationName = "Tpdohp";
			hotplate = new Tpdohp.Plate(dimension, top, bottom, left, right);
			break;
		}

		String maxIterations = inputs.get("# Iterations").getText();
		hotplate.setMaxIterations(Integer.parseInt(maxIterations));

		String minTempThreshold = inputs.get("Min. Temp Threshold").getText();
		hotplate.setTempThreshold(Double.parseDouble(minTempThreshold));

		this.logTestParameters(maxIterations, minTempThreshold);

		hotplate.setDiffusionListener(this);

		hotplate.diffuse();

		this.getMetricRecorder().postRunCleanup();

		this.logTestMetrics(plateImplementationName);
	}

	private void logTestParameters(String maxIterations, String minTempThreshold) {
		System.out
				.println("Dimension, Top Edge Temperature, Bottom Edge Temperature, Left Edge Temperature, Right Edge Temperature, Maximum Allowed Iterations, Mininmum Temperature Threshold");

		System.out.println(String.valueOf(dimension) + ", "
				+ String.valueOf(top) + ", " + String.valueOf(bottom) + ", "
				+ String.valueOf(left) + ", " + String.valueOf(right) + ", "
				+ maxIterations + ", " + minTempThreshold);
	}

	private void logTestMetrics(String plateImplementationName) {
		System.out
				.println("Plate Type, Run Duration in ns, Memory Used in Bytes, Number of Iterations");
		System.out.println(plateImplementationName + ", "
				+ this.getMetricRecorder().getDurationOfDiffusion() + ", "
				+ this.getMetricRecorder().getAmountOfMemoryUsed() + ", "
				+ this.getNumberOfIterations());
	}

	private Grid viz;

	public void iterationDone(int currIter) {
		this.setNumberOfIterations(currIter);
	}

	public void diffusionDone(int finalIter) {
		this.setNumberOfIterations(finalIter);

		// System.out.println("diffusionDone:start");
		double[][] matrix = hotplate.getMatrix();
		// System.out.println("in diffusionDone");
		// for(double[] arr: matrix){
		// System.out.println(java.util.Arrays.toString(arr));
		// }
		if (viz != null) {
			contents.remove(viz);
		}
		viz = new Grid(matrix);
		contents.add(viz);
		validate();
		repaint();
		// System.out.println("diffusionDone:finish");
	}

}
