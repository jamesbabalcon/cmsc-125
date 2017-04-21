package processScheduling;

import java.util.ArrayList;
import java.util.Collections;

import process.Process;

public class SJF implements Runnable
{
	private ArrayList <Process> readyQueue = new ArrayList<Process>();
	private boolean running = false;
	
	public synchronized void start()
	{
		if( running )
			return;
		
		running = true;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		while(true)
		{
			if( readyQueue.size() > 1 )
			{
				Collections.sort(readyQueue);
				
				System.out.println(readyQueue.get(0).getPid() + " is finished.");
				System.out.println("Burst Time: " +readyQueue.get(0).burstTime());
				readyQueue.remove(0);
			}
			else if( readyQueue.size() == 1 )
			{
				System.out.println(readyQueue.get(0).getPid() + " is finished.");
				System.out.println("Burst Time: " +readyQueue.get(0).burstTime());
				readyQueue.remove(0);
			}
		}
	}
	
	public void addProcess(Process p)
	{
		readyQueue.add(p);
	}
}
