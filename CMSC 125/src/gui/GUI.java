package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import process.Process;
import process.Resource;
import processScheduling.FCFS;

@SuppressWarnings("serial")
public class GUI extends JPanel implements ActionListener {
	
	private JScrollPane scrollPane;
	
	private JPanel lower;
	private JPanel inner;
	private JPanel center;
	private JPanel left;
	private SimulationPanel bottom;
	private JPanel[] setProc = new JPanel[6];
	
	private JCheckBox[] algos = new JCheckBox[7];
	private String[] labels = {
			"FCFS", "SJF", "SRTF", "PRIO", "NP-PRIO", "RR-10", "RR-20"
			};
	
//	private int numProc;
//	private int numRes;
	
	private JTextField procField;
	private JTextField resField;
	private JLabel[] processes;
	private JTextField[] arrivalTime;
	private JTextField[] priority;
	private JTextField[] burstTime;
	private JTextField[][] alloc;
	private JTextField[][] max;
	private JTextField[] available;
	
	private JButton add;
	private JButton start;
	
	public GUI() {
		setLayout(new BorderLayout());
		
		left = new JPanel();
		left.setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(200, 200));;
		
		JPanel p1 = new JPanel(); 
		p1.setLayout(new GridLayout(5, 0));
		
		p1.add(new JLabel("Number of processes: "));
		p1.add(procField = new JTextField());
		
		p1.add(new JLabel("Resources per process: "));
		p1.add(resField = new JTextField());
		
		p1.add(new JLabel("Scheduling Algorithm:"));
		
		left.add(p1, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(4, 2));
		
		for(int x = 0; x < algos.length; x++) {
			p2.add(algos[x] = new JCheckBox(labels[x]));
		}
		
		left.add(p2, BorderLayout.CENTER);
		
		left.add(add = new JButton("Set"), BorderLayout.SOUTH);
		add.addActionListener(this);
		
		add(left, BorderLayout.WEST);
		
		////////////////////////////////////////
		
		inner = new JPanel();
		inner.setLayout(new BorderLayout());
		inner.setPreferredSize(new Dimension(500, 1200));
		
		center = new JPanel(new FlowLayout());
		
		for(int x = 0; x < setProc.length; x++) {
			setProc[x] = new JPanel(new BorderLayout());
			center.add(setProc[x]);
		}
		
		setProc[0].add(new JLabel("Process ID", JLabel.CENTER), BorderLayout.NORTH);
		setProc[0].setPreferredSize(new Dimension(70, 200));
		setProc[1].add(new JLabel("Arrival Time", JLabel.CENTER), BorderLayout.NORTH);
		setProc[1].setPreferredSize(new Dimension(70, 200));
		setProc[2].add(new JLabel("Priority", JLabel.CENTER), BorderLayout.NORTH);
		setProc[2].setPreferredSize(new Dimension(70, 200));
		setProc[3].add(new JLabel("Burst Time", JLabel.CENTER), BorderLayout.NORTH);
		setProc[3].setPreferredSize(new Dimension(70, 200));
		setProc[4].add(new JLabel("Allocated", JLabel.CENTER), BorderLayout.NORTH);
		setProc[4].setPreferredSize(new Dimension(200, 200));
		setProc[5].add(new JLabel("Maximum", JLabel.CENTER), BorderLayout.NORTH);
		setProc[5].setPreferredSize(new Dimension(200, 200));
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(center);
		inner.add(scrollPane, BorderLayout.CENTER);
		
		lower = new JPanel(new BorderLayout());
		lower.add(start = new JButton("Start"), BorderLayout.SOUTH);
		start.addActionListener(this);
		
		inner.add(lower, BorderLayout.SOUTH);
		start.setEnabled(false);
		
		add(inner, BorderLayout.CENTER);
		
		///////////////////////////////////////
		
		bottom = new SimulationPanel();
		
		add(bottom, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			int numProc = Integer.parseInt(procField.getText());
			int numRes = Integer.parseInt(resField.getText());
			
			processes = new JLabel[numProc];
			JPanel p0 = new JPanel(new GridLayout(numProc, 0));
			
			for(int x = 0; x < numProc; x++) {
				processes[x] = new JLabel("p" + x, JLabel.CENTER);
				p0.add(processes[x]);
			}
			
			setProc[0].add(p0);
			
			arrivalTime = new JTextField[numProc];
			JPanel p1 = new JPanel(new GridLayout(numProc, 0));
			
			for(int x = 0; x < numProc; x++) {
				arrivalTime[x] = new JTextField();
				p1.add(arrivalTime[x]);
			}
			
			setProc[1].add(p1);
			
			priority = new JTextField[numProc];
			JPanel p2 = new JPanel(new GridLayout(numProc, 0));
			
			for(int x = 0; x < numProc; x++) {
				priority[x] = new JTextField();
				p2.add(priority[x]);
			}
			
			setProc[2].add(p2);
			
			burstTime = new JTextField[numProc];
			JPanel p3 = new JPanel(new GridLayout(numProc, 0));
			
			for(int x = 0; x < numProc; x++) {
				burstTime[x] = new JTextField();
				p3.add(burstTime[x]);
			}
			
			setProc[3].add(p3);
			
			alloc = new JTextField[numProc][numRes];
			JPanel p4 = new JPanel(new GridLayout(numProc, numRes));
			
			for(int x = 0; x < numProc; x++) {
				for(int y = 0; y < numRes; y++) {
					alloc[x][y] = new JTextField();
					p4.add(alloc[x][y]);
				}
			}
			
			setProc[4].add(p4);
			
			max = new JTextField[numProc][numRes];
			JPanel p5 = new JPanel(new GridLayout(numProc, numRes));
			
			for(int x = 0; x < numProc; x++) {
				for(int y = 0; y < numRes; y++) {
					max[x][y] = new JTextField();
					p5.add(max[x][y]);
				}
			}
			
			setProc[5].add(p5);
			
			available = new JTextField[numRes];
			JPanel p6 = new JPanel();
			JPanel p6a = new JPanel();
			
			p6.add(new JLabel("Available: "), BorderLayout.WEST);
			for(int x = 0; x < numRes; x++) {
				available[x] = new JTextField();
				available[x].setPreferredSize(new Dimension(30, 20));
				p6a.add(available[x]);
			}
			p6.add(p6a);
			lower.add(p6);
			
			start.setEnabled(true);
			
			revalidate();
		}
		else if(e.getSource() == start) {
			ArrayList<Process> processes = new ArrayList<Process>();
			ArrayList<Resource> allocation = new ArrayList<Resource>();
			ArrayList<Resource> maxx = new ArrayList<Resource>();
			ArrayList<Resource> avail = new ArrayList<Resource>();
			
			for(int x = 0; x < Integer.parseInt(procField.getText()); x++) {
				for(int y = 0; y < Integer.parseInt(resField.getText()); y++) {
					allocation.add(new Resource(Integer.parseInt(alloc[x][y].getText())));
					maxx.add(new Resource(Integer.parseInt(max[x][y].getText())));
				}
				processes.add(new Process(x, Integer.parseInt(arrivalTime[x].getText()),0 , Integer.parseInt(burstTime[x].getText()), allocation, maxx));
			}
			
			for(int x = 0; x < Integer.parseInt(resField.getText()); x++) {
				avail.add(new Resource(Integer.parseInt(available[x].getText())));
			}
			
			FCFS fcfs =  new FCFS(processes, avail);
			bottom.setFcfs(fcfs);
			fcfs.start();
			bottom.start();
		}
	}
}
