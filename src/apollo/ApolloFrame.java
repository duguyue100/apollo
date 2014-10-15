package apollo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class draw the entire playground and actions
 * 
 * @author dgyHome
 *
 */

public class ApolloFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public int X;
	public int Y;
	public int WIDTH;
	public int HEIGHT;
	public String PROJECT_TITLE;

	/**
	 * This is a constructor of Apollo frame.
	 * 
	 */
	public ApolloFrame(String project_title, int x, int y, int width, int height)
	{
		super("Apollo");
		
		this.PROJECT_TITLE=project_title;
		this.X=x;
		this.Y=y;
		this.WIDTH=width;
		this.HEIGHT=height;
		
		this.setTitle(PROJECT_TITLE);
		this.setBounds(X, Y, WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		this.add(new ApolloPanel(this.getWidth(), this.getHeight()));
		this.pack();
	}
	
	public class ApolloPanel extends JPanel implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		private final int DELAY=60;
		private final int GROUND_HEIGHT=30;
		private final int HEAD_SIZE=20;
		
		private int WIDTH;
		private int HEIGHT;
		private Timer timer;
		
		private int arrow_x;
		private int arrow_y;
		private int arrow_travelled;
		
		/**
		 * Constructor of Apollo Panel
		 * 
		 * @param width the width of panel
		 * @param height the height of panel
		 */
		public ApolloPanel(int width, int height)
		{
			init(width, height);
		}
		
		public void init(int width, int height)
		{
			this.WIDTH=width;
			this.HEIGHT=height;
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
			this.setDoubleBuffered(true);
			
			this.arrow_travelled=0;
						
			timer=new Timer(DELAY, this);
			timer.start();
		}
		
		public void drawGround(Graphics g)
		{
			g.setColor(Color.GREEN);
			g.fillRect(0, this.HEIGHT-this.GROUND_HEIGHT, this.WIDTH, this.GROUND_HEIGHT);
		}
		
		public void drawMan(Graphics g)
		{
			g.setColor(Color.BLACK);
			//Graphics2D g2d=(Graphics2D)g;
			
			//AffineTransform old=g2d.getTransform();
			
			
			
			// head
			g.fillOval(1300-arrow_x,200, this.HEAD_SIZE, this.HEAD_SIZE);
			g.fillOval(1305-arrow_x,215, this.HEAD_SIZE/2, this.HEAD_SIZE*3);
			
			// body
			//g2d.rotate(Math.toRadians(30), 200, 150);
			//g.fillOval(130+arrow_x,210, this.HEAD_SIZE/2, this.HEAD_SIZE*3);
				
			//g2d.rotate(Math.toRadians(330), 200, 150);
			// leg
			
		

		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			this.drawGround(g);
			
			this.drawMan(g);
			
			
			
			if (this.arrow_travelled<=500)
			{
				this.arrow_travelled=arrow_x;
				g.drawLine(this.WIDTH/2, this.HEIGHT/2, this.WIDTH/2+50, this.HEIGHT/2);
			}
			else
			{
				int travel=arrow_x-this.arrow_travelled;
				g.drawLine(travel+this.WIDTH/2, this.HEIGHT/2, travel+this.WIDTH/2+50, this.HEIGHT/2);
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			arrow_x+=10;
			arrow_y=200;
			
			repaint();
		}
		
	}
}
