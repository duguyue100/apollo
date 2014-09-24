package shoot;

/**
 * This class describes arrow information and calculates
 * all useful information needed for draw an arrow.
 * 
 * The unit in this class:
 * 
 * For speed       : m/s
 * For angle       : radius
 * For acceleration: m/s^2
 * For position    : m
 * 
 * @author dgyHome
 *
 */

public class ArrowInfo {
	private double speed;
	private double alpha;
	private double gravity;
	private double init_x;
	private double init_y;
	
	/**
	 * Default constructor of an arrow
	 * 
	 * @param s absolute speed of an arrow.
	 * @param a shooting angle of an arrow.
	 * @param x arrow position at x-axis.
	 * @param y arrow position at y-axis.
	 */
	public ArrowInfo(double s, double a, double x, double y)
	{
		this.setSpeed(s);
		this.setAlpha(a);
		this.setInitX(x);
		this.setInitY(y);
		this.gravity=-9.8;
	}
	
	/**
	 * Set initial arrow x position, 
	 * @param x initial position in x-axis
	 */
	public void setInitX(double x)
	{
		this.init_x=x;
	}
	
	/**
	 * Get initial arrow x position
	 * @return initial position in x-axis
	 */
	public double getInitX()
	{
		return this.init_x;
	}
	
	/**
	 * Set initial arrow y position.
	 * In principle, y>=0
	 * 
	 * @param y initial position in y-axis
	 */
	public void setInitY(double y)
	{
		if (y>=0)
			this.init_y=y;
		else this.init_y=0;
	}
	
	/**
	 * Get initial arrow y position.
	 * @return initial position in y-axis
	 */
	public double getInitY()
	{
		return this.init_y;
	}
	
	/**
	 * The function set the speed for an arrow.
	 * Noted that the speed of arrow has to greater or 
	 * equal to 0.
	 * 
	 * @param s absolute speed
	 */
	public void setSpeed(double s)
	{
		if (s>=0)
			this.speed=s;
		else this.speed=0;
	}
	
	/**
	 * This function returns absolute speed of an arrow.
	 * 
	 * @return absolute speed.
	 */
	public double getSpeed()
	{
		return this.speed;
	}
	
	/**
	 * This function set shooting angle of an arrow,
	 * The angle has to greater or equal to 0.
	 * 
	 * In principle, 0<=alpha<=pi
	 * 
	 * @param a shooting angle
	 */
	public void setAlpha(double a)
	{
		if (a>=0)
			this.alpha=a;
		else this.alpha=0;
		
		while (this.alpha>Math.PI)
		{
			this.alpha-=Math.PI;
		}
	}
	
	/**
	 * This function returns shooting angle of an arrow.
	 * 
	 * @return shooting angle
	 */
	public double getAlpha()
	{
		return this.alpha;
	}
	
	/**
	 * This function calculates total flying period for arrow.
	 * 
	 * @return total flying period
	 */
	public double getFlyingPeriod()
	{
		double v=this.getVerticalSpeed();
		
		return (2*v)/(-1*this.gravity)+(-2*v+Math.sqrt(4*v*v-8*this.gravity*this.init_y))/(-2*this.gravity);
	}
	
	/**
	 * This function calculates horizontal speed of arrow.
	 * 
	 * @return horizontal speed of arrow
	 */
	public double getHorizontalSpeed()
	{
		if (this.alpha<=Math.PI/2)
			return this.speed*Math.cos(this.alpha);
		else return -1*this.speed*Math.cos(Math.PI-this.alpha);
	}
	
	/**
	 * This function calculates vertical speed of arrow.
	 * 
	 * @return vertical speed of arrow
	 */
	public double getVerticalSpeed()
	{
		if (this.alpha<=Math.PI/2)
			return this.speed*Math.sin(this.alpha);
		else return this.speed*Math.sin(Math.PI-this.alpha);
	}
	
	/**
	 * This function calculates horizontal speed at
	 * specific time.
	 * 
	 * @param t a specific time, assume start time is 0
	 * @return horizontal speed at that time
	 */
	public double getHorizontalSpeedAtTime(double t)
	{
		if (t<this.getFlyingPeriod())
			return this.getHorizontalSpeed();
		else return 0;
	}
	
	/**
	 * This function calculates vertical speed at
	 * specific time.
	 * 
	 * @param t a specific time, assume start time is 0
	 * @return vertical speed at that time
	 */
	public double getVerticalSpeedAtTime(double t)
	{
		if (t<this.getFlyingPeriod())
			return this.getVerticalSpeed()-this.gravity*t;
		else return 0;
	}
	
	/**
	 * Calculates arrow's x position at specific time.
	 * 
	 * @param t a specific time, assume start time is 0
	 * @return arrow's position in x-axis at that time
	 */
	public double getPositionXAtTime(double t)
	{
		if (t<this.getFlyingPeriod())
			return this.getInitX()+this.getHorizontalSpeed()*t;
		else return this.getInitX()+this.getHorizontalSpeed()*this.getFlyingPeriod();
	}
	
	/**
	 * Calculates arrow's y position at specific time.
	 * 
	 * @param t a specific time, assume start time is 0
	 * @return arrow's position in y-axis at that time.
	 */
	public double getPositionYAtTime(double t)
	{
		if (t<this.getFlyingPeriod())
			return this.init_y+0.5*this.gravity*t*t+this.getVerticalSpeed()*t;
		else return 0;
	}
	
	/**
	 * Calculates arrow's angle at a specific time.
	 *  
	 * @param t a specific time, assume start time is 0
	 * @return arrow's angle at that time.
	 */
	public double getThetaAtTime(double t)
	{
		if (t<this.getFlyingPeriod())
			if (this.alpha<=Math.PI/2)
				return Math.atan(this.getVerticalSpeedAtTime(t)/this.getVerticalSpeedAtTime(t));
			else return Math.atan(this.getVerticalSpeedAtTime(t)/(-1*this.getVerticalSpeedAtTime(t)));
		else return 0;
	}
}
