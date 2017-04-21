import javax.swing.JFrame;

import gui.GUI;

public class Main {

	public static void main(String[] args) {
//		new BankersTest().start();
		
		GUI gui = new GUI();
		
		JFrame frame = new JFrame("CPU Scheduling Simulation");
		frame.add(gui);
		frame.setSize(1200, 700);
 		frame.setLocationRelativeTo(null);
 		frame.setResizable(false);
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		frame.setVisible(true);
	}
}
