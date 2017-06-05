package process;

import java.util.ArrayList;

public class Process implements Comparable<Process> {

	private boolean prioLock = false;
	private int pid;
	private int arrivalTime;
	private int priority;
	private int burstTime;
	private ArrayList<Resource> allocation;
	private ArrayList<Resource> need;
	private ArrayList<Resource> max;
	
	public Process(int pid, int arrivalTime, int priority, int burstTime, ArrayList<Resource> allocation, ArrayList<Resource> max) {
		this.pid = pid;
		this.priority = priority;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.allocation = allocation;
		this.max = max; 
		
		need = new ArrayList<Resource>();
	}
	
	public int getPid() {
		return pid;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int burstTime() {
		return burstTime;
	}
	
	public ArrayList<Resource> getAllocation() {
		return allocation;
	}
	
	public ArrayList<Resource> getMax() {
		return max;
	}
	
	public ArrayList<Resource> getNeed() {
		return need;
	}
	
	public void setNeed(ArrayList<Resource> need) {
		this.need = need;
	}
	
	public void addNeed(int index, Resource resource) {
		need.add(index, resource);
	}
	
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	
	public int getPrio() {
		return priority;
	}
	
	public int compareTo(Process p) {
		if(!prioLock) {
			int compBurstTime = p.burstTime();
			return this.burstTime - compBurstTime;
		}
		else {
			int compPrio = p.getPrio();
			return this.priority - compPrio;
		}
	}
}
