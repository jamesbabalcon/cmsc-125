package deadlock;
import java.util.ArrayList;
import java.util.Iterator;

import process.Process;
import process.Resource;

public class BankersTest implements Runnable {

	private boolean running;
	private Thread thread;
	private int runningTime;
	
	private ArrayList<Process> processes;
	private ArrayList<Process> jobQueue;
	private ArrayList<Resource> available;
	private ArrayList<Process> readyQueue;
	private Bankers bank;
	
	public BankersTest() {
		running = false;
		thread = null;
		runningTime = 0;
		
		processes = new ArrayList<Process>();
		jobQueue = new ArrayList<Process>();
		available = new ArrayList<Resource>();
		readyQueue = new ArrayList<Process>();
		
		init();
	}
	
	public void init() {
		for(int x = 0; x < 5; x++) {
			ArrayList<Resource> alloc = new ArrayList<Resource>();
			ArrayList<Resource> max = new ArrayList<Resource>();
			int arr = (int) (Math.random() * (10 - 1)) + x;
			for(int y = 0; y < 3; y++) {
				int a = (int) (Math.random() * (9 - 1));
				int b = (int) (Math.random() * (9 - 1)) + a;
				
				alloc.add(new Resource(a));
				max.add(new Resource(b));
			}
			
			processes.add(new Process(x, arr, 0, 0, alloc, max));
		}
		
		getNeed();
		
		System.out.print("AVAILABLE: ");
		
		for(int x = 0; x < 3; x++) {
			available.add(new Resource((int) (1 + Math.random() * (19 - 1))));
			System.out.print(available.get(x).getValue() + " ");
		}
		
		System.out.println();
		
		for(Process p : processes) {
			System.out.print(p.getPid() + " | ");
			
			for(Resource r : p.getAllocation()) {
				System.out.print(r.getValue() + " ");
			}
			
			System.out.print(" | ");
			
			for(Resource r : p.getMax()) {
				System.out.print(r.getValue() + " ");
			}
			
			System.out.print(" | ");
			
			for(Resource r : p.getNeed()) {
				System.out.print(r.getValue() + " ");
			}
			
			System.out.print(" | ");
			
			System.out.println(p.getArrivalTime());
			
			System.out.println();
		}
		
		bank = new Bankers(available);
	}
	
	public void getNeed() {
		for(Process p : processes) {
			for(int x = 0; x < 3; x++) {
				p.addNeed(x, new Resource(p.getMax().get(x).getValue() - p.getAllocation().get(x).getValue()));
			}
		}
	}
	
	public synchronized void start() {
		if(running) 
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while(running) {
			
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
				
				System.out.print("AVAILABLE: ");
				for(Resource r : bank.getAvailable()) {
					System.out.print(r.getValue() + " ");
				}
				System.out.println();
				
				try {
					Thread.sleep(9);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
					
				if(bank.allocated(p)) {
					readyQueue.add(p);
					System.out.println("Added p" + p.getPid() + " to ready queue");
					iter.remove();
				}
				else {
					System.err.println("Stuck at p" + p.getPid());
				}
				
				try {
					Thread.sleep(9);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				System.out.println("Time: " + runningTime);
				runningTime++;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(9);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("Time: " + runningTime);
			runningTime++;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
