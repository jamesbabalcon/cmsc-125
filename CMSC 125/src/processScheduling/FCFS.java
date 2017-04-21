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
	private Bankers bank;
	
	public FCFS(ArrayList<Process> processes, ArrayList<Resource> available) {
		this.processes = processes;
		
		jobQueue = new ArrayList<Process>();
		readyQueue = new ArrayList<Process>();
		
		bank = new Bankers(available);
		
		getNeed();
	}
	
	public void getNeed() {
		for(Process p : processes) {
			for(int x = 0; x < 3; x++) {
				p.addNeed(x, new Resource(p.getMax().get(x).getValue() - p.getAllocation().get(x).getValue()));
			}
		}
	}
	
	public synchronized void start() {
		if( running )
			return;
		
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
					System.out.println("Added p" + p.getPid() + " to job queue");
					jobQueue.add(p);
					iterx.remove();
				}
			}
			
			Iterator<Process> iter = jobQueue.iterator();
			
			while(iter.hasNext()) {
				Process p = iter.next();
				
//				System.out.print("AVAILABLE: ");
				for(Resource r : bank.getAvailable()) {
					System.out.print(r.getValue() + " ");
				}
//				System.out.println();
//				
//				try {
//					Thread.sleep(9);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
					
				if(bank.allocated(p)) {
					readyQueue.add(p);
					System.out.println("Added p" + p.getPid() + " to ready queue");
					iter.remove();
				}
				else {
					System.err.println("Stuck at p" + p.getPid());
				}
				
//				try {
//					Thread.sleep(9);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//				
//				System.out.println("Time: " + runningTime);
//				runningTime++;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
//			try {
//				Thread.sleep(9);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
			
//			System.out.println("Time: " + runningTime);
//			runningTime++;
			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			if( !readyQueue.isEmpty() )	{
				for( int i = 0; i <= readyQueue.get(0).burstTime(); i++) {
					System.out.println(readyQueue.get(0).getPid()+":"+readyQueue.get(0).burstTime());
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
//				System.out.println(readyQueue.get(0).getPid() + " is finished");
//				System.out.println("Arrival Time: " + readyQueue.get(0).getArrivalTime());
				readyQueue.remove(0);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addProcess(Process p) {
		readyQueue.add(p);
	}
}
