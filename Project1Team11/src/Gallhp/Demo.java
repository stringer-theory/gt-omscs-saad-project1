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
	}

	private void setupWindow(){
      // setup overall app ui
       setTitle("Heated Plate Diffusion Simulation");
       // setSize(300, 200);
       setExtendedState(JFrame.MAXIMIZED_BOTH);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private	void addEventHandlers(){}

	public void actionPerformed(ActionEvent e) {}
}
