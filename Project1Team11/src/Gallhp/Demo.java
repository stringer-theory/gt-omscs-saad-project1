// Demo.java
package Gallhp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.util.*;
import java.io.*;

import common.*;
import Tpdahp.*;
// import Tpfahp.*;
// import Twfahp.*;

public class Demo extends JFrame implements ActionListener {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Demo ui = new Demo();
                ui.setVisible(true);
            }
        });
    }
	
	public Demo(){
		createUI();
		addEventHandlers();
	}

	private void createUI(){
		setupWindow();
		add(contentsPanel());
		pack();
	}

	private void setupWindow(){
		// setup overall app ui
		setTitle("Heated Plate Diffusion Simulation");
		// setSize(300, 200);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private JPanel contentsPanel(){
       // setup primary window contents panel
		JPanel contents = new JPanel(new BorderLayout());
		contents.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		contents.setAlignmentY(Component.TOP_ALIGNMENT);
	
		contents.add(inputsPanel(),BorderLayout.WEST);
		contents.add(visualization(),BorderLayout.CENTER);
		// contents.add(runControls(),BorderLayout.SOUTH);

		return contents;
	}

	private JPanel inputsPanel(){
		JPanel inputsPanel = new JPanel();
		inputsPanel.setLayout(new BoxLayout(inputsPanel,BoxLayout.PAGE_AXIS));
		// inputsPanel.setLayout(new FlowLayout());
		inputsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		inputsPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		// inputsPanel.setPreferredSize(new Dimension(300,200));

		inputsPanel.add(prompt("Choose a simulation..."));
		inputsPanel.add(simulationCombo());
		inputsPanel.add(settings());
		inputsPanel.add(Box.createVerticalGlue());

		return inputsPanel;
	}

	private Grid visualization(){ return new Grid();}

	private JPanel runControls(){
		JPanel ctrlsPanel = new JPanel(new FlowLayout());
		ctrlsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		ctrlsPanel.add(button("Run"));
		ctrlsPanel.add(button("Pause"));
		ctrlsPanel.add(button("stop"));

		return ctrlsPanel;
	}

	private JComboBox<String> simulationCombo(){
		String[] options = {"Tpdahp","Tpfahp","Twfahp","4"};
		JComboBox<String> simulationCombo = new JComboBox<String>(options);
		simulationCombo.addActionListener(this);

		return simulationCombo;
	}

	private JPanel settings(){ 
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		settingsPanel.setLayout(new BoxLayout(settingsPanel,BoxLayout.PAGE_AXIS));
		settingsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		// settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// settingsPanel.add(prompt("Set initial conditions"));
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

	private JPanel inputField(String name){
		JPanel inputPanel = new JPanel();
		// inputPanel.setBorder(BorderFactory.createTitledBorder("blah"));
		// inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.LINE_AXIS));
		inputPanel.setLayout(new FlowLayout());
		inputPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel l = new JLabel(name);
		l.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputPanel.add(l);

		// inputPanel.add(Box.createHorizontalGlue());
		JTextField t = new JTextField("",10);
		t.setAlignmentX(Component.RIGHT_ALIGNMENT);
		l.setLabelFor(t);
		inputPanel.add(t);

		return inputPanel;
	}

	private JButton button(String name){
		JButton button = new JButton(name);
		button.setActionCommand(name);
		button.addActionListener(this);
		return button;
	}

	private	void addEventHandlers(){}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
}
