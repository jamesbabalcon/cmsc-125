package processScheduling;

import java.util.ArrayList;
import java.util.Iterator;

import deadlock.Bankers;
import process.Process;
import process.Resource;

public class FCFS implements Runnable {
	
	private boolean running;
	private Thread thread;
	private int runningTime;
	
	private ArrayList<Process> processes;
	private ArrayList<Process> jobQueue;
	private ArrayList<Process> readyQueue;
	private ArrayList<Resource> available;
	
	private Process current;
	
	private Bankers bank;
	
	public FCFS(ArrayList<Process> processes, ArrayList<Resource> available) {
		this.processes = processes;
		this.available = available;
		
		jobQueue = new ArrayList<Process>();
		readyQueue = new ArrayList<Process>();
		
		bank = new Bankers(available);
		
		getNeed();
	}
	
	public void getNeed() {
		for(Process p : processes) {
			for(int x = 0; x < available.size(); x++) {
				p.addNeed(x, new Resource(p.getMax().get(x).getValue() - p.getAllocation().get(x).getValue()));
			}
		}
	}
	
	public synchronized void start() {
		if( running )
			return;
		
		System.out.println("started");
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(running) {
//			System.out.println("size: " + readyQueue.size());
			
			Iterator<Process> iterx = processes.iterator();
			
			while(iterx.hasNext()) {
				Process p = iterx.next();
				
				if(p.getArrivalTime() == runningTime) {
					System.out.println(runningTime);
					jobQueue.add(p);
					iterx.remove();
				}
			}
			
			Iterator<Process> jobIiter = jobQueue.iterator();
			
			while(jobIiter.hasNext()) {
				Process p = jobIiter.next();
				
				if(bank.allocated(p)) {
					readyQueue.add(p);
					jobIiter.remove();
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			Iterator<Process> readyIter = readyQueue.iterator();
			
			while(readyIter.hasNext()) {
				Process p = readyIter.next();
				
				for( int i = 0; i <= p.burstTime(); i++) {
					System.out.println("p" + p.getPid());
					current = p;
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				readyIter.remove();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			runningTime++;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Process getCurrent() {
		return current;
	}
}
