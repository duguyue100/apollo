package apollo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import shoot.Arrow;

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
	
	// Mouse motion
	
	public int mouse_x;
	public int mouse_y;
	public boolean mouse_release=false;
	public boolean mouse_press=false;

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
		
		final ApolloPanel apolloPanel=new ApolloPanel(this.getWidth(), this.getHeight());
		final ApolloMouseListener apolloMouseListener=new ApolloMouseListener();
		
		// Add Panel
		this.add(apolloPanel);
		
		this.addMouseListener(apolloMouseListener);
		this.addMouseMotionListener(apolloMouseListener);
		
		this.pack();
	}
	
	public class ApolloMouseListener implements MouseMotionListener, MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			mouse_press=true;
			mouse_release=false;
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			mouse_release=true;
			mouse_press=false;
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			mouse_x=arg0.getX();
			mouse_y=arg0.getY();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class ApolloPanel extends JPanel implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		private final int DELAY=60;
		private final int GROUND_HEIGHT=30;
		
		private int WIDTH;
		private int HEIGHT;
		private final int m2px=20;
		
		// display parameter
		
		private int arrow_strength_label_x;
		private int arrow_strength_label_y;
		private String arrow_strength_message="Arrow Strength: ";
		
		// timing
		private Timer timer;
		private double time_counter=0;
		
		// arrow information

		public ArrayList<Arrow> arrowList=new ArrayList<Arrow>();
		private int arrow_head_x;
		private int arrow_head_y;
		private int arrow_tail_x;
		private int arrow_tail_y;
		private int arrow_travelled;
		private double arrow_speed=0;
		private final double arrow_max_speed=40;
		
		// bow information
		
		private int bow_x;
		private int bow_y;
		
		private int bow_enda_x;
		private int bow_enda_y;
		private int bow_endb_x;
		private int bow_endb_y;
		
		// Man's information
		
		private final int ARM_LENG=40;
		private final int HEAD_SIZE=30;
		private final int BODY_LENGTH=40;
		private int MAN_POSITION=60;
		private int MAN_HEIGHT=100;
		private int ARM_START_X;
		private int ARM_START_Y;
		
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
			
			// init display information
			
			this.arrow_strength_label_x=10;
			this.arrow_strength_label_y=this.HEIGHT-this.GROUND_HEIGHT/3;
			
			
			// init bow information
			this.ARM_START_X=this.MAN_POSITION+this.HEAD_SIZE/2;
			this.ARM_START_Y=this.HEIGHT-this.GROUND_HEIGHT-this.MAN_HEIGHT+this.HEAD_SIZE+this.BODY_LENGTH/2;
			this.bow_x=this.ARM_START_X+this.ARM_LENG;
			this.bow_y=this.ARM_START_Y;
			this.bow_enda_x=this.bow_x-10;
			this.bow_enda_y=this.bow_y-20;
			this.bow_endb_x=this.bow_enda_x;
			this.bow_endb_y=this.bow_y+20;
			
			
			// init timing informtion
			timer=new Timer(DELAY, (ActionListener) this);
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
			Graphics2D g2d=(Graphics2D)g;
			
			//AffineTransform old=g2d.getTransform();
			
			/*** Man 1 ***/
			
			// head
			g.fillOval(this.MAN_POSITION, this.HEIGHT-this.GROUND_HEIGHT-this.MAN_HEIGHT, this.HEAD_SIZE, this.HEAD_SIZE);
			
			// body
			g2d.setStroke(new BasicStroke(5));
			g2d.draw(new Line2D.Float(this.MAN_POSITION+this.HEAD_SIZE/2,this.HEIGHT-this.GROUND_HEIGHT-this.MAN_HEIGHT+this.HEAD_SIZE,this.MAN_POSITION+this.HEAD_SIZE/2,this.HEIGHT-this.GROUND_HEIGHT-this.MAN_HEIGHT+this.HEAD_SIZE+this.BODY_LENGTH));
			
			// leg
			g2d.draw(new Line2D.Float(this.MAN_POSITION+this.HEAD_SIZE/2,this.HEIGHT-this.GROUND_HEIGHT-this.MAN_HEIGHT+this.HEAD_SIZE+this.BODY_LENGTH, this.MAN_POSITION+this.HEAD_SIZE/2-20, this.HEIGHT-this.GROUND_HEIGHT));
			g2d.draw(new Line2D.Float(this.MAN_POSITION+this.HEAD_SIZE/2,this.HEIGHT-this.GROUND_HEIGHT-this.MAN_HEIGHT+this.HEAD_SIZE+this.BODY_LENGTH, this.MAN_POSITION+this.HEAD_SIZE/2+20, this.HEIGHT-this.GROUND_HEIGHT));
			
			// arm
			g2d.draw(new Line2D.Float(this.ARM_START_X, this.ARM_START_Y, this.bow_x, this.bow_y));
			
			// bow
			g2d.draw(new QuadCurve2D.Float(this.bow_enda_x, this.bow_enda_y,
					this.bow_x, this.bow_y,
					this.bow_endb_x, this.bow_endb_y));
			
			g2d.setStroke(new BasicStroke(2));
		

		}
		
		public void drawMessage(Graphics g)
		{
			Graphics2D g2d=(Graphics2D)g;
			if (this.arrowList.size()==0)
			{
				g2d.drawString(this.arrow_strength_message+"0 m/s", this.arrow_strength_label_x, this.arrow_strength_label_y);
			}
			else g2d.drawString(this.arrow_strength_message+Double.toString(this.arrowList.get(this.arrowList.size()-1).getArrowSpeed())+" m/s", this.arrow_strength_label_x, this.arrow_strength_label_y);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			this.drawGround(g);
			this.drawMan(g);
			this.drawMessage(g);
			
			
//			if (this.arrow_travelled<=500)
//			{
//				this.arrow_travelled=arrow_x;
//				g.drawLine(this.WIDTH/2, this.HEIGHT/2, this.WIDTH/2+50, this.HEIGHT/2);
//			}
//			else
//			{
//				int travel=arrow_x-this.arrow_travelled;
//				g.drawLine(travel+this.WIDTH/2, this.HEIGHT/2, travel+this.WIDTH/2+50, this.HEIGHT/2);
//			}
			
//			int travel=arrow_x-this.arrow_travelled;
			
			// draw all arrow
			
			for (int i=0;i<this.arrowList.size();i++)
			{
				Arrow temp=this.arrowList.get(i);
				g.drawLine(temp.getArrowHeadX(), temp.getArrowHeadY(), temp.getArrowTailX(), temp.getArrowTailY());
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			if (mouse_release==true && mouse_press==false)
			{
				Arrow temp=new Arrow();

				double d=this.updateAngle(mouse_x, mouse_y);
				int diff_x=(int)(20*Math.cos(d));
				int diff_y=(int)(20*Math.sin(d));

				temp.setArrowHead(this.bow_x+diff_x,	this.bow_y-diff_y);
				temp.setArrowTail(this.bow_x-diff_x, this.bow_y+diff_y);
				temp.setArrowAngle(d);

				this.arrowList.add(temp);

				mouse_release=false;

			}
			else if (mouse_press==true && mouse_release==false)
			{
				this.updateLastArrowInfo(mouse_x, mouse_y);				
			}
			
			this.updateArrowInfo();
			
			// update bow info
			this.updateBowAxis(mouse_x, mouse_y);
			
			// update time
			// if a new arrow loaded, time count as zero			
			time_counter++;
			
			repaint();
		}
		
		/**
		 * Get angle based on mouse and arm start
		 * @param m_x mouse_x
		 * @param m_y mouse_y
		 * @return degree 0-2pi
		 */
		public double updateAngle(int m_x, int m_y)
		{
			double x=(double)(m_x-this.ARM_START_X);
			double y=(double)(this.ARM_START_Y-m_y);
			double z=Math.sqrt((double)x*(double)x+(double)y*(double)y);
			
			double v_sin=y/z;
			double v_cos=x/z;
			
			this.bow_y=this.ARM_START_Y-(int)(this.ARM_LENG*v_sin);
			this.bow_x=this.ARM_START_X+(int)(this.ARM_LENG*v_cos);
			
			double d_sin=Math.asin(v_sin);
			
			if (this.bow_x<this.ARM_START_X && this.bow_y<this.ARM_START_Y)
				d_sin=(Math.PI-d_sin);
			else if (this.bow_x<this.ARM_START_X && this.bow_y>this.ARM_START_Y)
				d_sin=(Math.PI-d_sin);
			else if (this.bow_x>this.ARM_START_X && this.bow_y>this.ARM_START_Y)
				d_sin+=(2*Math.PI);
			
			return d_sin;
		}
		
		/**
		 * find new Bow Axis
		 * @param m_x mouse_x
		 * @param m_y mouse_y
		 */
		public void updateBowAxis(int m_x, int m_y)
		{
			double d_sin=this.updateAngle(m_x, m_y);
			
			this.bow_enda_x=this.ARM_START_X+(int)(this.ARM_LENG*(Math.cos(d_sin+Math.PI/6)));
			this.bow_enda_y=this.ARM_START_Y-(int)(this.ARM_LENG*(Math.sin(d_sin+Math.PI/6)));
			
			this.bow_endb_x=this.ARM_START_X+(int)(this.ARM_LENG*(Math.cos(d_sin-Math.PI/6)));
			this.bow_endb_y=this.ARM_START_Y-(int)(this.ARM_LENG*(Math.sin(d_sin-Math.PI/6)));
			
			double x=(double)(m_x-this.ARM_START_X);
			double y=(double)(this.ARM_START_Y-m_y);
			double z=Math.sqrt((double)x*(double)x+(double)y*(double)y);
			
			double speed=z/(double)this.m2px;
			if (speed>40) speed=40.0;
			if (this.arrowList.size()!=0)
				this.arrowList.get(this.arrowList.size()-1).setArrowSpeed(speed);
		}
		
		public void updateLastArrowInfo(int m_x, int m_y)
		{
			if (this.arrowList.size()!=0)
				this.arrowList.get(this.arrowList.size()-1).setArrowAngle(this.updateAngle(m_x, m_y));
		}
		
		public void updateArrowInfo()
		{
			for (int i=0;i<this.arrowList.size();i++)
			{
				int diff_x=(int)(this.arrowList.get(i).getArrowSpeed()*Math.cos(this.arrowList.get(i).getArrowAngle()));
				int diff_y=(int)(this.arrowList.get(i).getArrowSpeed()*Math.sin(this.arrowList.get(i).getArrowAngle()));
				
				int temp_head_x=this.arrowList.get(i).getArrowHeadX()+diff_x;
				int temp_head_y=this.arrowList.get(i).getArrowHeadY()-diff_y;
				int temp_tail_x=this.arrowList.get(i).getArrowTailX()+diff_x;
				int temp_tail_y=this.arrowList.get(i).getArrowTailY()-diff_y;
				
				this.arrowList.get(i).setArrowHead(temp_head_x, temp_head_y);
				this.arrowList.get(i).setArrowTail(temp_tail_x, temp_tail_y);
			}
		}
	}
}
