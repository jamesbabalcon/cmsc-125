package processScheduling;

import java.util.ArrayList;
import java.util.Collections;

import process.Process;

public class Priority implements Runnable
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
				for( int i = 0; i < readyQueue.size(); i++ )
					System.out.print(readyQueue.get(i).getPid() +":" +readyQueue.get(i).getPrio() +", ");
				
				Collections.sort(readyQueue);
				
				System.out.println(readyQueue.get(0).getPid() +":" +readyQueue.get(0).burstTime());
				readyQueue.get(0).setBurstTime(readyQueue.get(0).burstTime()-1);
				
				if(readyQueue.get(0).burstTime() == 0)
				{
					System.out.println(readyQueue.get(0).getPid() + " is finished.");
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
