package processScheduling;

import java.util.ArrayList;

import process.Process;

public class RRTen implements Runnable
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
			if( !readyQueue.isEmpty() )
			{
				Process holder;
				
				if( readyQueue.get(0).burstTime() > 0 )
				{
					System.out.println(readyQueue.get(0).getPid() + ":" + readyQueue.get(0).burstTime());
					readyQueue.get(0).setBurstTime(readyQueue.get(0).burstTime()-10);
					holder = readyQueue.get(0);
					readyQueue.remove(0);
					readyQueue.add(holder);
				}
				else
				{
					System.out.println(readyQueue.get(0).getPid() + " is finished");
					readyQueue.remove(0);
				}
			}
		}
	}
	
	public void addProcess(Process p)
	{
		readyQueue.add(p);
	}
}
