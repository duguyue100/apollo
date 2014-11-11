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
		private final int GROUND_START=0;
		private final int GROUND_END=3600;
		
		private int WIDTH;
		private int HEIGHT;
		private final int m2px=20;
		
		// display parameter
		
		private int arrow_strength_label_x;
		private int arrow_strength_label_y;
		private String arrow_strength_message="Arrow Strength: ";
		
		private int screen_x;
		private int screen_y;
		
		private int body_shift;
		
		// physical constant
		
		private final double gravity=9.8;
		
		// timing
		private Timer timer;
		private double time_counter=0;
		
		// arrow information

		public ArrayList<Arrow> arrowList=new ArrayList<Arrow>();
		private final double arrow_max_speed=40;
		private final int arrow_length=20;
		private double last_arrow_speed=0;
		
		// Players
		
		private Player p1=new Player();
		private Player p2=new Player();
		private final int P1_POS=60;
		private final int P2_POS=3500;
		private final int P1_ID=1;
		private final int P2_ID=2;
		
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
			
			p1.PLAYER_ID=1;
			p1.MAN_POSITION=this.P1_POS;
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
			p2.PLAYER_ID=2;
			p2.MAN_POSITION=this.P2_POS;
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
			
			// screen axis
			
			this.screen_x=p1.ARM_START_X;
			this.screen_y=p1.ARM_START_Y;
			this.body_shift=0;
			
			// init timing information
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
			g.fillOval(p1.MAN_POSITION-this.body_shift,
					this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT,
					p1.HEAD_SIZE,
					p1.HEAD_SIZE);
			
			// body
			g2d.setStroke(new BasicStroke(5));
			g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE,
									p1.MAN_POSITION+p1.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH));
			
			// leg
			g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH,
									p1.MAN_POSITION+p1.HEAD_SIZE/2-20-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT));
			
			g2d.draw(new Line2D.Float(p1.MAN_POSITION+p1.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p1.MAN_HEIGHT+p1.HEAD_SIZE+p1.BODY_LENGTH,
									p1.MAN_POSITION+p1.HEAD_SIZE/2+20-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT));
			
			// arm
			g2d.draw(new Line2D.Float(p1.ARM_START_X-this.body_shift,
							p1.ARM_START_Y,
							p1.bow_x-this.body_shift,
							p1.bow_y));
			
			// bow
			g2d.draw(new QuadCurve2D.Float(p1.bow_enda_x-this.body_shift,
						p1.bow_enda_y,
						p1.bow_x-this.body_shift,
						p1.bow_y,
						p1.bow_endb_x-this.body_shift,
						p1.bow_endb_y));
			
			/*** player 2 ***/
			
			// head
			g.fillOval(p2.MAN_POSITION-this.body_shift,
					this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT,
					p2.HEAD_SIZE,
					p2.HEAD_SIZE);

			// body
			g2d.setStroke(new BasicStroke(5));
			g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE,
									p2.MAN_POSITION+p2.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH));

			// leg
			g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH,
									p2.MAN_POSITION+p2.HEAD_SIZE/2-20-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT));
			g2d.draw(new Line2D.Float(p2.MAN_POSITION+p2.HEAD_SIZE/2-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT-p2.MAN_HEIGHT+p2.HEAD_SIZE+p2.BODY_LENGTH,
									p2.MAN_POSITION+p2.HEAD_SIZE/2+20-this.body_shift,
									this.HEIGHT-this.GROUND_HEIGHT));

			// arm
			g2d.draw(new Line2D.Float(p2.ARM_START_X-this.body_shift,
									p2.ARM_START_Y,
									p2.bow_x-this.body_shift,
									p2.bow_y));

			// bow
			g2d.draw(new QuadCurve2D.Float(p2.bow_enda_x-this.body_shift,
										p2.bow_enda_y,
										p2.bow_x-this.body_shift,
										p2.bow_y,
										p2.bow_endb_x-this.body_shift,
										p2.bow_endb_y));

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
			
			// game over call
			
			if (p1.life_block_fill_width==0 || p2.life_block_fill_width==0)
			{
				g.setColor(Color.BLUE);
				if (p1.life_block_fill_width==0 && p2.life_block_fill_width>0)
					g2d.drawString("Game Over, Player 2 Win!", this.WIDTH/2-50, this.HEIGHT/2);
				else if (p1.life_block_fill_width>0 && p2.life_block_fill_width==0)
					g2d.drawString("Game Over, Player 1 Win!", this.WIDTH/2-50, this.HEIGHT/2);
				else if (p1.life_block_fill_width==0 && p2.life_block_fill_width==0)
					g2d.drawString("Game Over, Tie!", this.WIDTH/2-50, this.HEIGHT/2);
			}
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
				g.drawLine(temp.getArrowHeadX()-this.screen_x,
						temp.getArrowHeadY()+this.screen_y,
						temp.getArrowTailX()-this.screen_x, 
						temp.getArrowTailY()+this.screen_y);
			}
			
			for (int i=0;i<p2.arrowList.size();i++)
			{
				Arrow temp=p2.arrowList.get(i);
				g.drawLine(temp.getArrowHeadX()-this.screen_x,
						temp.getArrowHeadY()+this.screen_y,
						temp.getArrowTailX()-this.screen_x,
						temp.getArrowTailY()+this.screen_y);

			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			if (mouse_release==true && mouse_press==false)
			{
				if (mouse_press_count%2==1)
				{
					this.addNewArrow(p1, mouse_x, mouse_y);
				}
				else
				{
					this.addNewArrow(p2, mouse_x+this.GROUND_END-this.WIDTH, mouse_y);
				}

				mouse_release=false;
			}
			
			if (mouse_release==false && mouse_press==true)
			{
				if (mouse_press_count%2==1)
				{
					this.screen_x=0;
					this.screen_y=0;
					this.body_shift=0;
				}
				else
				{
					this.screen_x=this.GROUND_END-this.WIDTH;
					this.screen_y=0;
					this.body_shift=this.GROUND_END-this.WIDTH;
				}
				
				this.time_counter=0;
			}
			
			// update all arrows
			this.updateArrowInfo(p1);
			this.updateArrowInfo(p2);
						
			// update bow info
			if (mouse_press_count%2==1)
			{
				this.updateBowAxis(p1, mouse_x, mouse_y);
				
				if (p1.arrowList.size()!=0)
				{
					Arrow currArrow=p1.arrowList.get(p1.arrowList.size()-1);
					
					if ((currArrow.getArrowHeadX()>=this.GROUND_START+this.WIDTH/2) &&
						(currArrow.getArrowHeadX()<=this.GROUND_END-this.WIDTH/2) && currArrow.arrow_active==true)
					{
						this.screen_x=currArrow.getArrowHeadX()-this.WIDTH/2;
						
						this.screen_y=0;
//						if (currArrow.getArrowHeadY()<=this.HEIGHT/2)
//							this.screen_y=this.HEIGHT/2-currArrow.getArrowHeadY();
//						else this.screen_y=0;
						this.body_shift=this.screen_x;
					}
					else if ((currArrow.getArrowHeadX()<this.GROUND_START+this.WIDTH/2) && currArrow.arrow_active==true)
					{
						this.screen_x=0;
						this.screen_y=0;
						this.body_shift=0;
					}
					else if (currArrow.getArrowHeadX()>this.GROUND_END-this.WIDTH/2 && currArrow.arrow_active==true)
					{
						this.screen_x=this.GROUND_END-this.WIDTH;
						this.screen_y=0;
						this.body_shift=this.GROUND_END-this.WIDTH;
					}
				}
			}
			else if (mouse_press_count%2==0 && mouse_press_count!=0)
			{
				this.updateBowAxis(p2, mouse_x+this.GROUND_END-this.WIDTH, mouse_y);
				
				if (p2.arrowList.size()!=0)
				{
					Arrow currArrow=p2.arrowList.get(p2.arrowList.size()-1);
					
					if ((currArrow.getArrowHeadX()>=this.GROUND_START+this.WIDTH/2) &&
						(currArrow.getArrowHeadX()<=this.GROUND_END-this.WIDTH/2) && currArrow.arrow_active==true)
					{
						this.screen_x=currArrow.getArrowHeadX()-this.WIDTH/2;
						
						this.screen_y=0;
//						if (currArrow.getArrowHeadY()<=this.HEIGHT/2)
//							this.screen_y=this.HEIGHT/2-currArrow.getArrowHeadY();
//						else this.screen_y=0;
						this.body_shift=this.screen_x;
					}
					else if (currArrow.getArrowHeadX()>this.GROUND_END-this.WIDTH/2 && currArrow.arrow_active==true)
					{
						this.screen_x=this.GROUND_END-this.WIDTH;
						this.screen_y=0;
						this.body_shift=this.GROUND_END-this.WIDTH;
					}
					else if ((currArrow.getArrowHeadX()<this.GROUND_START+this.WIDTH/2) && currArrow.arrow_active==true)
					{
						this.screen_x=0;
						this.screen_y=0;
						this.body_shift=0;
					}
				}
			}
			
			// updating p1 and p2's angle
			for (int i=0; i<p1.arrowList.size();i++)
			{
				// updating angle
				Arrow updatedArrow=this.updateArrowInfo(p1.arrowList.get(i), this.time_counter);
				p1.arrowList.set(i, updatedArrow);
			}

			for (int i=0; i<p2.arrowList.size();i++)
			{
				Arrow updatedArrow=this.updateArrowInfo(p2.arrowList.get(i), this.time_counter);
				p2.arrowList.set(i, updatedArrow);
			}

			
			// update time
			// if a new arrow loaded, time count as zero			
			time_counter++;
			
			// update game settings
			repaint();
			
			if (p1.life_block_fill_width==0 || p2.life_block_fill_width==0)
			{
				timer.stop();
			}
		}
		
		public void addNewArrow(Player p, int m_x, int m_y)
		{
			Arrow temp=new Arrow();

			double d=this.updateAngle(p, m_x, m_y);
			int diff_x=(int)(this.arrow_length*Math.cos(d));
			int diff_y=(int)(this.arrow_length*Math.sin(d));

			temp.setArrowHead(p.bow_x+diff_x, p.bow_y-diff_y);
			temp.setArrowTail(p.bow_x-diff_x, p.bow_y+diff_y);
			temp.setArrowAngle(d);
			temp.setArrowSpeed(this.last_arrow_speed);
			
			temp.arrow_owner=p.PLAYER_ID;
			
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
			this.last_arrow_speed=speed;
		}
		
		public void updateLastArrowInfo(Player p, int m_x, int m_y)
		{
			if (p.arrowList.size()!=0)
				p.arrowList.get(p.arrowList.size()-1).setArrowAngle(this.updateAngle(p, m_x, m_y));
		}
		
		public Arrow updateArrowInfo(Arrow arr, double t)
		{
			Arrow result=arr;
			
			if (result.arrow_active==true)
			{
				double v_hor=arr.getArrowSpeed()*Math.cos(arr.getArrowAngle());
				double v_ver=arr.getArrowSpeed()*Math.sin(arr.getArrowAngle());

				v_ver-=this.gravity*(double)(this.DELAY/1000.0);

				result.setArrowSpeed(Math.sqrt(v_hor*v_hor+v_ver*v_ver));

				if (v_ver>=0 && v_hor>=0)
				{
					double d_sin=v_ver/result.getArrowSpeed();
					result.setArrowAngle(Math.asin(d_sin));
				}
				else if (v_ver<0 && v_hor>=0)
				{
					double d_sin=v_ver/result.getArrowSpeed();
					result.setArrowAngle(Math.asin(d_sin)+2*Math.PI);
				}
				else
				{
					double d_sin=v_ver/result.getArrowSpeed();
					result.setArrowAngle(Math.PI-Math.asin(d_sin));
				}
			}
			else result.setArrowSpeed(0);
			
			return result;
		}
		
		public void updateArrowInfo(Player p)
		{
			for (int i=0;i<p.arrowList.size();i++)
			{
				Arrow t=p.arrowList.get(i);
				
				// shoot on guy also stop
				
				// shoot on p1.
				if ((((t.getArrowHeadY()<this.HEIGHT-this.GROUND_HEIGHT+10 && t.getArrowHeadY()>this.HEIGHT-p1.MAN_HEIGHT-p1.HEAD_SIZE-10) &&
					(t.getArrowHeadX()>p1.MAN_POSITION-10 && t.getArrowHeadX()<p1.MAN_POSITION+p1.HEAD_SIZE+10))) &&
					t.arrow_owner!=this.P1_ID)
				{
					p.arrowList.get(i).setArrowSpeed(0);
					
					if (p.arrowList.get(i).arrow_active==true)
						p1.life_block_fill_width-=p1.LIFE_FACTOR;
					
					p.arrowList.get(i).arrow_active=false;
				}
				
				// shoot on p2
				
				if ((((t.getArrowHeadY()<this.HEIGHT-this.GROUND_HEIGHT+10 && t.getArrowHeadY()>this.HEIGHT-p2.MAN_HEIGHT-p2.HEAD_SIZE-10) &&
					(t.getArrowHeadX()>p2.MAN_POSITION-10 && t.getArrowHeadX()<p2.MAN_POSITION+p2.HEAD_SIZE+10))) &&
					t.arrow_owner!=this.P2_ID)
				{
					p.arrowList.get(i).setArrowSpeed(0);

					if (p.arrowList.get(i).arrow_active==true)
						p2.life_block_fill_width-=p2.LIFE_FACTOR;

					p.arrowList.get(i).arrow_active=false;
				}
				
				// touch the ground stop
				if (t.getArrowHeadY()>this.HEIGHT-this.GROUND_HEIGHT)
				{
					p.arrowList.get(i).setArrowSpeed(0);
					p.arrowList.get(i).arrow_active=false;
				}
				
				int diff_x=(int)(t.getArrowSpeed()*Math.cos(t.getArrowAngle())*1.3);
				int diff_y=(int)(t.getArrowSpeed()*Math.sin(t.getArrowAngle())*1.3);
				
				int cen_x=(t.getArrowHeadX()+t.getArrowTailX())/2;
				int cen_y=(t.getArrowHeadY()+t.getArrowHeadY())/2;
				
				int temp_head_x=cen_x+(int)(this.arrow_length*Math.cos(t.getArrowAngle()));
				int temp_head_y=cen_y-(int)(this.arrow_length*Math.sin(t.getArrowAngle()));
				int temp_tail_x=cen_x-(int)(this.arrow_length*Math.cos(t.getArrowAngle()));
				int temp_tail_y=cen_y+(int)(this.arrow_length*Math.sin(t.getArrowAngle()));
				
				temp_head_x+=diff_x;
				temp_head_y-=diff_y;
				temp_tail_x+=diff_x;
				temp_tail_y-=diff_y;
				
				if (t.arrow_active==false)
				{
					temp_head_x=t.getArrowHeadX();
					temp_head_y=t.getArrowHeadY();
					temp_tail_x=t.getArrowTailX();
					temp_tail_y=t.getArrowTailY();
				}
				
				p.arrowList.get(i).setArrowHead(temp_head_x, temp_head_y);
				p.arrowList.get(i).setArrowTail(temp_tail_x, temp_tail_y);
			}
		}
	}
}
