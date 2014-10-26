package shoot;

public class Arrow {
	
	private int arrow_head_x;
	private int arrow_head_y;
	private int arrow_tail_x;
	private int arrow_tail_y;
	private double arrow_angle;
	private double arrow_speed;
	public boolean arrow_active;
	
	public Arrow()
	{
		this.setArrowHead(0, 0);
		this.setArrowTail(0, 0);
		this.setArrowAngle(0);
		this.setArrowSpeed(0);
		this.arrow_active=true;
	}
	
	public Arrow(int head_x, int head_y, int tail_x, int tail_y)
	{
		this.setArrowHead(head_x, head_y);
		this.setArrowTail(tail_x, tail_y);
	}
	
	/*** Setter and getter ***/
	
	public void setArrowAngle(double angle)
	{
		this.arrow_angle=angle;
	}
	
	public double getArrowAngle()
	{
		return this.arrow_angle;
	}
	
	public void setArrowSpeed(double speed)
	{
		this.arrow_speed=speed;
	}
	
	public double getArrowSpeed()
	{
		return this.arrow_speed;
	}
	
	public void setArrowHead(int head_x, int head_y)
	{
		this.arrow_head_x=head_x;
		this.arrow_head_y=head_y;
	}
	
	public void setArrowTail(int tail_x, int tail_y)
	{
		this.arrow_tail_x=tail_x;
		this.arrow_tail_y=tail_y;
	}
	
	public int getArrowHeadX()
	{
		return this.arrow_head_x;
	}
	
	public int getArrowHeadY()
	{
		return this.arrow_head_y;
	}
	
	public int getArrowTailX()
	{
		return this.arrow_tail_x;
	}
	
	public int getArrowTailY()
	{
		return this.arrow_tail_y;
	}
}
