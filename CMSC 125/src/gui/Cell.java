package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {

	private int x, y;
	private String pid;
	private Color color;
	
	public Cell(String pid, Color color, int x, int y) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.pid = pid;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(color);
        g2.fillRect(x, y, 30, 20);

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 30, 20);
        
        g2.drawString(pid, x + 10, y + 15);
	}
}
