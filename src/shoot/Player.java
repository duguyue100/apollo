package shoot;

import java.util.ArrayList;

public class Player {
	
	// person info
	
	public final int ARM_LENG=40;
	public final int HEAD_SIZE=30;
	public final int BODY_LENGTH=40;
	public final int MAN_HEIGHT=100;
	public final int LIFE_FACTOR=20;
	public byte PLAYER_ID;
	public int MAN_POSITION;
	public int ARM_START_X;
	public int ARM_START_Y;
	
	// bow info
	
	public int bow_x;
	public int bow_y;
	
	public int bow_enda_x;
	public int bow_enda_y;
	public int bow_endb_x;
	public int bow_endb_y;
	
	// arrow list
	public ArrayList<Arrow> arrowList=new ArrayList<Arrow>();
	
	// life info
	public int life_block_messag_x;
	public int life_block_messag_y;
	public int life_block_x;
	public int life_block_y;
	public int life_block_length;
	public int life_block_width;
	public int life_block_fill_width;
}
