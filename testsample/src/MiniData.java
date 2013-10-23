import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MiniData extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int x =0;
	int y =0;
	public void moveBall(){
		x =x +1;
		y =y+1;
	}
/*
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
	super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.fillOval(0, 0, 30, 30);
		g2d.drawOval(0, 50, 30, 30);
		g2d.fillRect(50, 0, 30, 30);
		g2d.drawRect(50, 50, 30, 30);
		g2d.draw(new Ellipse2D.Double(0,100,30,30));
	}
	*/
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
	super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			//					RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillOval(x, y, 30, 30);
		//g2d.fillRect(50, 0, 30, 30);
	//	g2d.drawRect(50, 50, 30, 30);
	//	g2d.draw(new Ellipse2D.Double(0,100,30,30));
	}
	
	
	public static void main(String args[]){
		JFrame jsdat = new JFrame();
		MiniData mds = new MiniData();
		jsdat.add(mds);
		jsdat.setSize(300,300);
		jsdat.setVisible(true);
		jsdat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		while(true) {
			mds.moveBall();
			mds.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
}
