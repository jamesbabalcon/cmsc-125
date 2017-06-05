package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import process.Process;
import process.Resource;
import processScheduling.FCFS;

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel implements Runnable {
	
	private boolean running = false;;
	private Thread thread;
	private int count = 0;
	private int cellWidth = 30;
	
	private FCFS fcfs;
	
	private ArrayList<Cell> cells;

	public SimulationPanel() {
		setPreferredSize(new Dimension(1200, 300));
		
		cells = new ArrayList<Cell>();
	}
	
	public void init(ArrayList<Process> processes, ArrayList<Resource> available) {
		fcfs = new FCFS(processes, available);
	}
	
	public synchronized void start() {
		if( running )
			return;
		
		running = true;
		thread = new Thread(this);
		fcfs.start();
		thread.start();
	}

	public void run() {
		while(running) {
			
//			Integer.toString(fcfs.getCurrent().getPid());
			cells.add(new Cell(Integer.toString(fcfs.getCurrent().getPid()), 50 + count * cellWidth, 150));
			count++;
			
			repaint();
			
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.darkGray);
		g2.fillRect(0, 0, 1200, 300);
		
		for(Cell c : cells) {
			c.paint(g2);
		}
	}
}
