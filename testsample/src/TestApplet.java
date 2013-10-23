import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;

public class TestApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init()
	{
		setSize(256,256);
		add(new TestApplet());
		getContentPane().setBackground(Color.red);
		setBackground(Color.red);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		setBackground(Color.red);
		//setForeground(Color.blue);
	
	}
}
