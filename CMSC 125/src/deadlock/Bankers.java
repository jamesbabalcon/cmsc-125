package deadlock;

import java.util.ArrayList;

import process.Resource;
import process.Process;

public class Bankers {
	
	private ArrayList<Resource> available;
	
	public Bankers(ArrayList<Resource> available) {
		this.available = available;
	}
	
	public boolean allocated(Process p) {
	
		if(isAvailable(p.getNeed())) {
			for(int x = 0; x < p.getAllocation().size(); x++) {
				available.set(x, new Resource(p.getAllocation().get(x).getValue() + p.getNeed().get(x).getValue()));
			}
			return true;
		}
		
		return false;
	}
	
	public boolean isAvailable(ArrayList<Resource> need) {
		
		for(int x = 0; x < available.size(); x++) {
			if(available.get(x).getValue() > need.get(x).getValue()) {
				continue;
			}
			else
				return false;
		}
		
		return true;
	}
	
	public ArrayList<Resource> getAvailable() {
		return available;
	}
}
