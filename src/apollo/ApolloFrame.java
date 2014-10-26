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

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import shoot.Arrow;
import shoot.Player;

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
	public int mouse_release_count=0;
	public int mouse_press_count=0;

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
			mouse_press_count++;
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			mouse_release=true;
			mouse_press=false;
			mouse_release_count++;
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
		private final double arrow_max_speed=40;
		private final int arrow_length=20;
		
		// Players
		
		private Player p1=new Player();
		private Player p2=new Player();
		
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
			
			// init display information
			
			this.arrow_strength_label_x=10;
			this.arrow_strength_label_y=this.HEIGHT-this.GROUND_HEIGHT/3;
			
			// init P1 info
			
			p1.MAN_POSITION=60;
			p1.ARM_START_X=p1.MAN_POSITION+p1.HEAD_SIZE/2;
			p1.ARM_START_Y=this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH/2;
			p1.bow_x=p1.ARM_START_X+p1.ARM_LENG;
			p1.bow_y=p1.ARM_START_Y;
			p1.bow_enda_x=p1.bow_x;
			p1.bow_enda_y=p1.bow_y;
			p1.bow_endb_x=p1.bow_x;
			p1.bow_endb_y=p1.bow_y;
			p1.life_block_messag_x=40;
			p1.life_block_messag_y=35;
			p1.life_block_x=30;
			p1.life_block_y=20;
			p1.life_block_length=20;
			p1.life_block_width=200;
			p1.life_block_fill_width=200;
			
			// init p2 info
			p2.MAN_POSITION=1140;
			p2.ARM_START_X=p2.MAN_POSITION+p2.HEAD_SIZE/2;
			p2.ARM_START_Y=this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH/2;
			p2.bow_x=p2.ARM_START_X-p2.ARM_LENG;
			p2.bow_y=p2.ARM_START_Y;
			p2.bow_enda_x=p2.bow_x;
			p2.bow_enda_y=p2.bow_y;
			p2.bow_endb_x=p2.bow_x;
			p2.bow_endb_y=p2.bow_y;
			p2.life_block_messag_x=980;
			p2.life_block_messag_y=35;
			p2.life_block_x=970;
			p2.life_block_y=20;
			p2.life_block_length=20;
			p2.life_block_width=200;
			p2.life_block_fill_width=200;
			
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
			
			/*** player 1 ***/
			
			// head
			g.fillOval(p1.MAN_POSITION, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT, p1.HEAD_SIZE, p1.HEAD_SIZE);
			
			// body
			g2d.setStroke(new BasicStroke(5));
			g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE, p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH));
			
			// leg
			g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH, p1.MAN_POSITION+p1.HEAD_SIZE/2-20, this.HEIGHT-this.GROUND_HEIGHT));
			g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH, p1.MAN_POSITION+p1.HEAD_SIZE/2+20, this.HEIGHT-this.GROUND_HEIGHT));
			
			// arm
			g2d.draw(new Line2D.Float(p1.ARM_START_X, p1.ARM_START_Y, p1.bow_x, p1.bow_y));
			
			// bow
			g2d.draw(new QuadCurve2D.Float(p1.bow_enda_x, p1.bow_enda_y, p1.bow_x, p1.bow_y, p1.bow_endb_x, p1.bow_endb_y));
			
			/*** player 2 ***/
			
			// head
			g.fillOval(p2.MAN_POSITION, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT, p2.HEAD_SIZE, p2.HEAD_SIZE);

			// body
			g2d.setStroke(new BasicStroke(5));
			g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE, p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH));

			// leg
			g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH, p2.MAN_POSITION+p2.HEAD_SIZE/2-20, this.HEIGHT-this.GROUND_HEIGHT));
			g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2, this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH, p2.MAN_POSITION+p2.HEAD_SIZE/2+20, this.HEIGHT-this.GROUND_HEIGHT));

			// arm
			g2d.draw(new Line2D.Float(p2.ARM_START_X, p2.ARM_START_Y, p2.bow_x, p2.bow_y));

			// bow
			g2d.draw(new QuadCurve2D.Float(p2.bow_enda_x, p2.bow_enda_y, p2.bow_x, p2.bow_y, p2.bow_endb_x, p2.bow_endb_y));

			g2d.setStroke(new BasicStroke(2));
		}
		
		public void drawMessage(Graphics g)
		{
			
			Graphics2D g2d=(Graphics2D)g;
			
			// draw arrow strength
			if (mouse_press_count%2==1)
			{
				if (p1.arrowList.size()==0)
				{
					g2d.drawString(this.arrow_strength_message+"0 m/s", this.arrow_strength_label_x, this.arrow_strength_label_y);
				}
				else g2d.drawString(this.arrow_strength_message+Double.toString(p1.arrowList.get(p1.arrowList.size()-1).getArrowSpeed())+" m/s", this.arrow_strength_label_x, this.arrow_strength_label_y);
			}
			else
			{
				if (p2.arrowList.size()==0)
				{
					g2d.drawString(this.arrow_strength_message+"0 m/s", this.arrow_strength_label_x, this.arrow_strength_label_y);
				}
				else g2d.drawString(this.arrow_strength_message+Double.toString(p2.arrowList.get(p2.arrowList.size()-1).getArrowSpeed())+" m/s", this.arrow_strength_label_x, this.arrow_strength_label_y);
			}
			
			// draw player 1 life
			g.setColor(Color.RED);
			g2d.drawRect(p1.life_block_x, p1.life_block_y, p1.life_block_width, p1.life_block_length);
			g2d.fillRect(p1.life_block_x, p1.life_block_y, p1.life_block_fill_width, p1.life_block_length);
			g.setColor(Color.BLACK);
			g2d.drawString("P1 Life: "+Integer.toString(p1.life_block_fill_width/2), p1.life_block_messag_x, p1.life_block_messag_y);
			
			// draw player 2 life
			g.setColor(Color.RED);
			g2d.drawRect(p2.life_block_x, p2.life_block_y, p2.life_block_width, p2.life_block_length);
			g2d.fillRect(p2.life_block_x, p2.life_block_y, p2.life_block_fill_width, p2.life_block_length);
			g.setColor(Color.BLACK);
			g2d.drawString("P2 Life: "+Integer.toString(p2.life_block_fill_width/2), p2.life_block_messag_x, p2.life_block_messag_y);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			this.drawGround(g);
			this.drawMan(g);
			this.drawMessage(g);
			
			g.setColor(Color.BLACK);
			// draw all arrow
			
			for (int i=0;i<p1.arrowList.size();i++)
			{
				Arrow temp=p1.arrowList.get(i);
				g.drawLine(temp.getArrowHeadX(), temp.getArrowHeadY(), temp.getArrowTailX(), temp.getArrowTailY());
			}
			
			for (int i=0;i<p2.arrowList.size();i++)
			{
				Arrow temp=p2.arrowList.get(i);
				g.drawLine(temp.getArrowHeadX(), temp.getArrowHeadY(), temp.getArrowTailX(), temp.getArrowTailY());
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			if (mouse_release==true && mouse_press==false)
			{
				if (mouse_press_count%2==1)
					this.addNewArrow(p1, mouse_x, mouse_y);
				else this.addNewArrow(p2, mouse_x, mouse_y);

				mouse_release=false;
			}
			
			// update all arrows
			this.updateArrowInfo(p1);
			this.updateArrowInfo(p2);
			
			// update bow info
			if (mouse_press_count%2==1)
				this.updateBowAxis(p1, mouse_x, mouse_y);
			else this.updateBowAxis(p2, mouse_x, mouse_y);
			
			// update time
			// if a new arrow loaded, time count as zero			
			time_counter++;
			
			
			repaint();
		}
		
		public void addNewArrow(Player p, int m_x, int m_y)
		{
			Arrow temp=new Arrow();

			double d=this.updateAngle(p, mouse_x, mouse_y);
			int diff_x=(int)(this.arrow_length*Math.cos(d));
			int diff_y=(int)(this.arrow_length*Math.sin(d));

			temp.setArrowHead(p.bow_x+diff_x, p.bow_y-diff_y);
			temp.setArrowTail(p.bow_x-diff_x, p.bow_y+diff_y);
			temp.setArrowAngle(d);
			
			p.arrowList.add(temp);
		}
		
		/**
		 * Get angle based on mouse and arm start
		 * @param m_x mouse_x
		 * @param m_y mouse_y
		 * @return degree 0-2pi
		 */
		public double updateAngle(Player p, int m_x, int m_y)
		{
			double x=(double)(m_x-p.ARM_START_X);
			double y=(double)(p.ARM_START_Y-m_y);
			double z=Math.sqrt((double)x*(double)x+(double)y*(double)y);
			
			double v_sin=y/z;
			double v_cos=x/z;
			
			p.bow_y=p.ARM_START_Y-(int)(p.ARM_LENG*v_sin);
			p.bow_x=p.ARM_START_X+(int)(p.ARM_LENG*v_cos);
			
			double d_sin=Math.asin(v_sin);
			
			if (p.bow_x<=p.ARM_START_X && p.bow_y<=p.ARM_START_Y)
				d_sin=(Math.PI-d_sin);
			else if (p.bow_x<=p.ARM_START_X && p.bow_y>=p.ARM_START_Y)
				d_sin=(Math.PI-d_sin);
			else if (p.bow_x>=p.ARM_START_X && p.bow_y>=p.ARM_START_Y)
				d_sin+=(2*Math.PI);
			
			return d_sin;
		}
		
		/**
		 * find new Bow Axis
		 * @param m_x mouse_x
		 * @param m_y mouse_y
		 */
		public void updateBowAxis(Player p, int m_x, int m_y)
		{
			double d_sin=this.updateAngle(p, m_x, m_y);
			
			p.bow_enda_x=p.ARM_START_X+(int)(p.ARM_LENG*(Math.cos(d_sin+Math.PI/6)));
			p.bow_enda_y=p.ARM_START_Y-(int)(p.ARM_LENG*(Math.sin(d_sin+Math.PI/6)));
			
			p.bow_endb_x=p.ARM_START_X+(int)(p.ARM_LENG*(Math.cos(d_sin-Math.PI/6)));
			p.bow_endb_y=p.ARM_START_Y-(int)(p.ARM_LENG*(Math.sin(d_sin-Math.PI/6)));
			
			double x=(double)(m_x-p.ARM_START_X);
			double y=(double)(p.ARM_START_Y-m_y);
			double z=Math.sqrt((double)x*(double)x+(double)y*(double)y);
			
			double speed=z/(double)this.m2px;
			if (speed>this.arrow_max_speed) speed=this.arrow_max_speed;
			if (p.arrowList.size()!=0)
				p.arrowList.get(p.arrowList.size()-1).setArrowSpeed(speed);
		}
		
		public void updateLastArrowInfo(Player p, int m_x, int m_y)
		{
			if (p.arrowList.size()!=0)
				p.arrowList.get(p.arrowList.size()-1).setArrowAngle(this.updateAngle(p, m_x, m_y));
		}
		
		public void updateArrowInfo(Player p)
		{
			for (int i=0;i<p.arrowList.size();i++)
			{
				Arrow t=p.arrowList.get(i);
				// touch the ground stop
				if (t.getArrowHeadY()>this.HEIGHT-this.GROUND_HEIGHT)
				{
					p.arrowList.get(i).setArrowSpeed(0);
					p.arrowList.get(i).arrow_active=false;
				}
				
				// shoot on guy also stop
				
				// shoot on p1.
				if ((t.getArrowHeadY()<this.HEIGHT-this.GROUND_HEIGHT && t.getArrowHeadY()>this.HEIGHT-p1.MAN_HEIGHT-p1.HEAD_SIZE) &&
					(t.getArrowHeadX()>p1.MAN_POSITION && t.getArrowHeadX()<p1.MAN_POSITION+p1.HEAD_SIZE))
				{
					p.arrowList.get(i).setArrowSpeed(0);
					
					if (p.arrowList.get(i).arrow_active==true)
						p1.life_block_fill_width-=p1.LIFE_FACTOR;
					
					p.arrowList.get(i).arrow_active=false;
				}
				
				// shoot on p2
				
				if ((t.getArrowHeadY()<this.HEIGHT-this.GROUND_HEIGHT && t.getArrowHeadY()>this.HEIGHT-p2.MAN_HEIGHT-p2.HEAD_SIZE) &&
					(t.getArrowHeadX()>p2.MAN_POSITION && t.getArrowHeadX()<p2.MAN_POSITION+p2.HEAD_SIZE))
				{
					p.arrowList.get(i).setArrowSpeed(0);

					if (p.arrowList.get(i).arrow_active==true)
						p2.life_block_fill_width-=p2.LIFE_FACTOR;

					p.arrowList.get(i).arrow_active=false;
				}
				
				int diff_x=(int)(t.getArrowSpeed()*Math.cos(t.getArrowAngle()));
				int diff_y=(int)(t.getArrowSpeed()*Math.sin(t.getArrowAngle()));
				
				int temp_head_x=t.getArrowHeadX()+diff_x;
				int temp_head_y=t.getArrowHeadY()-diff_y;
				int temp_tail_x=t.getArrowTailX()+diff_x;
				int temp_tail_y=t.getArrowTailY()-diff_y;
				
				p.arrowList.get(i).setArrowHead(temp_head_x, temp_head_y);
				p.arrowList.get(i).setArrowTail(temp_tail_x, temp_tail_y);
			}
		}
	}
}
