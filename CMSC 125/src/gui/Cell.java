package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {

	private int x, y;
	private String pid;
	
	public Cell(String pid, int x, int y) {
		this.x = x;
		this.y = y;
		this.pid = pid;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(pid.equals("0"))
			g2.setColor(Color.BLUE);
		else if(pid.equals("1"))
			g2.setColor(Color.RED);
		else if(pid.equals("2"))
			g2.setColor(Color.GREEN);
		else if(pid.equals("3"))
			g2.setColor(Color.YELLOW);
		else if(pid.equals("4"))
			g2.setColor(Color.CYAN);
		
        g2.fillRect(x, y, 30, 20);

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 30, 20);
        
        g2.drawString("p" + pid, x + 10, y + 15);
	}
}
